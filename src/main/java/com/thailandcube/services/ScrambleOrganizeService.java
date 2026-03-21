package com.thailandcube.services;

import com.thailandcube.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class PasscodeEntry implements Comparable<PasscodeEntry> {
    EventId event;
    int round;
    char group;
    Integer attempt;
    LocalDateTime startTime;
    String passcode;

    public PasscodeEntry(String event, int round, char group, Integer attempt, String passcode) {
        this.event = EventId.fromFullName(event);
        this.round = round;
        this.group = group;
        this.attempt = attempt;
        this.startTime = null;
        this.passcode = passcode;
    }

    public EventId getEvent() {
        return event;
    }

    public int getRound() {
        return round;
    }

    public char getGroup() {
        return group;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Activity findMatchingActivity(Schedule schedule) {
        for (Venue venue : schedule.getVenues()) {
            for (Room room : venue.getRooms()) {
                for (Activity activity : room.getActivities()) {
                    String activityCode = activity.getActivityCode();
                    String[] splittedActivityCode = activityCode.split("-");

                    if (splittedActivityCode.length < 3)
                        continue;

                    if (splittedActivityCode[1].length() < 2 || splittedActivityCode[2].length() < 2)
                        continue;

                    String eventId = splittedActivityCode[0];
                    EventId matchedEventId = EventId.fromString(eventId);
                    int activityRound = Character.getNumericValue(splittedActivityCode[1].charAt(1));
                    int activityGroupNumber = Character.getNumericValue(splittedActivityCode[2].charAt(1));

                    char expectedGroupChar = (char)('A' + activityGroupNumber - 1);

                    if (matchedEventId.equals(event) && round == activityRound && group == expectedGroupChar)
                        return activity;
                }
            }
        }

        return null;
    }

    @Override
    public int compareTo(PasscodeEntry o) {
        return startTime.compareTo(o.startTime);
    }
}

public class ScrambleOrganizeService {
    private static final ScrambleOrganizeService instance = new ScrambleOrganizeService();

    public static ScrambleOrganizeService getInstance() {
        return instance;
    }

    private char getAlphabetFromNumber(int number) {
        if (number < 1 || number > 26)
            return '\0';

        return (char)('A' + number - 1);
    }

    private void reorganizePasscode(Path passcodeFilePath, Schedule schedule) {
        Pattern passwordRegEx = Pattern.compile("^(.+) Round ([1-4]) Scramble Set ([A-Z]+)(?: Attempt ([0-9]+))?: ([0-9a-z]+)$");

        List<String> lines;
        try {
            lines = Files.readAllLines(passcodeFilePath, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<PasscodeEntry> passcodeEntries = new ArrayList<>();

        for (String line : lines) {
            if (line.isEmpty())
                continue;

            Matcher matcher = passwordRegEx.matcher(line);

            if (matcher.find()) {
                System.out.println("Passcode pattern matched.");

                String eventName = matcher.group(1);
                int round = Integer.parseInt(matcher.group(2));
                char group = matcher.group(3).charAt(0);

                Integer attempt = null;
                if (matcher.group(4) != null)
                    attempt = Integer.parseInt(matcher.group(4));

                String passcode = matcher.group(5);

                PasscodeEntry passcodeEntry = new PasscodeEntry(eventName, round, group, attempt, passcode);

                // Find the matching activity in schedule
                Activity foundActivity = passcodeEntry.findMatchingActivity(schedule);

                if (foundActivity != null) {
                    passcodeEntry.setStartTime(foundActivity.getStartTime());
                    passcodeEntries.add(passcodeEntry);
                }
            }
        }

        Collections.sort(passcodeEntries);

        // Preparing output for organized passcode text file's content
        String lastDate = null;
        List<String> outputLines = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (PasscodeEntry entry : passcodeEntries) {
            String dateStr = entry.getStartTime().format(formatter);
            String header = "";

            if (!dateStr.equals(lastDate)) {
                header = "=== " + dateStr + " ===\n";
                lastDate = dateStr;
            }

            String attemptStr = entry.getAttempt() != null ? " Attempt " + entry.getAttempt().toString() : "";

            String output = header + entry.getEvent().getFullName() + " Round " + entry.getRound() + " Scramble Set " + entry.getGroup() + attemptStr + ": " + entry.getPasscode();
            outputLines.add(output);

            System.out.println(output);
        }

        String outputFileContent = String.join("\n", outputLines);

        // Write the organized content into separated output file
        Path parentDirectory = passcodeFilePath.getParent();
        Path newOutputPath = parentDirectory.resolve("[ORGANIZED] - " + passcodeFilePath.getFileName().toString());

        try {
            Files.writeString(newOutputPath, outputFileContent, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Delete old passcode file
        try {
            Files.delete(passcodeFilePath);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Passcode successfully reorganized.");
        System.out.println("==================================");
    }

    public void organizeScrambles(File scrambleZip, PublicWcif wcif) {
        if (wcif == null)
            throw new NullPointerException("WCIF Object is null");

        final String scrambleZipFileName = wcif.getName() + " - Computer Display PDFs.zip";
        final String scramblePasscodeFileName = wcif.getName() + " - Computer Display PDF Passcodes - SECRET.txt";

        try {
            Path tempDir = Files.createTempDirectory("scramble-manager-temp");

            // Unzipping original scramble file to temp folder
            try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(scrambleZip.toPath()))) {
                ZipEntry entry = zis.getNextEntry();

                while (entry != null) {
                    Path newPath = tempDir.resolve(entry.getName());

                    if (entry.isDirectory())
                        Files.createDirectories(newPath);
                    else {
                        Files.createDirectories(newPath.getParent());
                        Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    entry = zis.getNextEntry();
                }
                zis.closeEntry();
            }
            catch (IOException e) {
                System.err.println("Error while extracting ZIP: " + e.getMessage());
            }

            Path scrambleZipFilePath = tempDir.resolve(scrambleZipFileName);
            Path scramblePasscodeFilePath = tempDir.resolve(scramblePasscodeFileName);

            if (!Files.exists(scrambleZipFilePath) || !Files.exists(scramblePasscodeFilePath))
                throw new IOException("Scramble file or passcode file not found");

            System.out.println("Scramble file and passcode file both exist in the temp directory.");

            reorganizePasscode(scramblePasscodeFilePath, wcif.getSchedule());
        }
        catch (IOException e) {
            System.err.println("Error occured while processing zip: " + e.getMessage());
        }
    }
}
