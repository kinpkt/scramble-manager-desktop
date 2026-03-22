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
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class PasscodeEntry implements Comparable<PasscodeEntry> {
    String event;
    int round;
    char group;
    Integer attempt;
    LocalDateTime startTime;
    String passcode;

    public PasscodeEntry(String event, int round, char group, Integer attempt, String passcode) {
        this.event = event;
        this.round = round;
        this.group = group;
        this.attempt = attempt;
        this.startTime = null;
        this.passcode = passcode;
    }

    public String getEvent() {
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

    @Override
    public int compareTo(PasscodeEntry o) {
        return startTime.compareTo(o.startTime);
    }
}

class EventDetails implements Comparable<EventDetails> {
    String code;
    String name;
    String venue;
    String room;
    int round;
    Integer attempt;
    List<EventGroupDetails> eventGroupDetails;
    LocalDateTime startTime;

    public EventDetails(String code, String name, String venue, String room, int round, LocalDateTime startTime) {
        this.code = code;
        this.name = name;
        this.venue = venue;
        this.room = room;
        this.round = round;
        this.eventGroupDetails = new ArrayList<>();
        this.startTime = startTime;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getRoom() {
        return room;
    }

    public int getRound() {
        return round;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public List<EventGroupDetails> getEventGroupDetails() {
        return eventGroupDetails;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public boolean groupIsInGroupDetails(char group) {
        for (EventGroupDetails gd : eventGroupDetails) {
            if (gd.getGroup() == group)
                return true;
        }

        return false;
    }

    @Override
    public int compareTo(EventDetails o) {
        return startTime.compareTo(o.startTime);
    }
}

class EventGroupDetails {
    char group;
    int groupNumber;
    LocalDateTime startTime;

    public EventGroupDetails(char group, int groupNumber, LocalDateTime startTime) {
        this.group = group;
        this.groupNumber = groupNumber;
        this.startTime = startTime;
    }

    public char getGroup() {
        return group;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}

public class ScrambleOrganizeService {
    private static final ScrambleOrganizeService instance = new ScrambleOrganizeService();

    public static ScrambleOrganizeService getInstance() {
        return instance;
    }

    private void reorganizePasscode(Path directory, List<EventDetails> eventDetails, String name) {
        Pattern passwordRegEx = Pattern.compile("^(.+) Round ([1-4]) Scramble Set ([A-Z]+)(?: Attempt ([0-9]+))?: ([0-9a-z]+)$");
        String scramblePasscodeFileName = name + " - Computer Display PDF Passcodes - SECRET.txt";
        Path passcodeFilePath = directory.resolve(scramblePasscodeFileName);

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

                EventDetails foundDetails = null;

                for (EventDetails ed : eventDetails) {
                    if (ed.getName().equals(eventName) && ed.getRound() == round && ed.getEventGroupDetails().size() > 0 && ed.groupIsInGroupDetails(group)) {
                        foundDetails = ed;
                        break;
                    }
                }

                if (foundDetails != null) {
                    passcodeEntry.setStartTime(foundDetails.getStartTime());
                    passcodeEntries.add(passcodeEntry);
                }
                else
                    System.out.println("Warning: Passcode found but no matching schedule activity for " + eventName + " Group " + group);
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

            String output = header + entry.getEvent() + " Round " + entry.getRound() + " Scramble Set " + entry.getGroup() + attemptStr + ": " + entry.getPasscode();
            outputLines.add(output);

            System.out.println(output);
        }

        String outputFileContent = String.join("\n", outputLines);

        // Write the organized content into separated output file
        Path newOutputPath = directory.resolve("[ORGANIZED] - " + directory.getFileName().toString() + ".txt");

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

    private void reorganizePDF(Path directory, List<EventDetails> allEventDetails) {
        int scheduleOrder = 1;
        String currentDateString = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (EventDetails eventDetails : allEventDetails) {
            String eventDateString = eventDetails.getStartTime().format(dateFormatter);

            if (currentDateString == null || !currentDateString.equals(eventDateString))
                currentDateString = eventDateString;

            String safeVenueName = eventDetails.getVenue().replaceAll("[\\\\/:*?\"<>|]", "_");
            String safeRoomName = eventDetails.getRoom().replaceAll("[\\\\/:*?\"<>|]", "_");

            String eventFolderName = scheduleOrder + "-" + eventDetails.getName() + " Round " + eventDetails.getRound();

            if (eventDetails.getAttempt() != null)
                eventFolderName += " Attempt " + eventDetails.getAttempt();

            Path eventDirPath = directory.resolve(safeVenueName).resolve(safeRoomName).resolve(currentDateString).resolve(eventFolderName);

            if (eventDetails.getEventGroupDetails().isEmpty()) {
                String scrambleFileName = eventDetails.getName() + " Round " + eventDetails.getRound() + " Scramble Set A Attempt " + eventDetails.getAttempt() + ".pdf";
                Path srcPath = directory.resolve(scrambleFileName);
                Path destPath = eventDirPath.resolve(scrambleFileName);

                try {
                    if (destPath.getParent() != null)
                        Files.createDirectories(destPath.getParent());

                    if (Files.exists(srcPath))
                        Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
                    else
                        System.out.println("File not found: " + srcPath);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                for (EventGroupDetails group : eventDetails.getEventGroupDetails()) {
                    String scrambleFileName = eventDetails.getName() + " Round " + eventDetails.getRound() + " Scramble Set " + group.getGroup() + (eventDetails.getAttempt() != null ? " Attempt " + eventDetails.getAttempt() : "") + ".pdf";

                    Path srcPath = directory.resolve(scrambleFileName);
                    Path destPath = eventDirPath.resolve(scrambleFileName);

                    try {
                        if (destPath.getParent() != null)
                            Files.createDirectories(destPath.getParent());

                        if (Files.exists(srcPath))
                            Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
                        else
                            System.out.println("File not found: " + srcPath);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            scheduleOrder++;
        }

        System.out.println("PDF successfully reorganized.");
        System.out.println("==================================");
    }

    private void createFolderFromVenues(Path directory, List<Venue> venues, String name) {
        List<EventDetails> allEventDetails = new ArrayList<>();

        for (Venue venue : venues) {
            String safeVenueName = venue.getName().replaceAll("[\\\\/:*?\"<>|]", "_");
            Path venuePath = directory.resolve(safeVenueName);

            try {
                Files.createDirectories(venuePath);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (Room room : venue.getRooms()) {
                String safeRoomName = room.getName().replaceAll("[\\\\/:*?\"<>|]", "_");
                Path roomPath = venuePath.resolve(safeRoomName);

                try {
                    Files.createDirectories(roomPath);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }

                for (Activity activity : room.getActivities()) {
                    if (!activity.getActivityCode().startsWith("other-")) {
                        String[] splittedActivityCode = activity.getActivityCode().split("-");
                        String activityCode = splittedActivityCode[0];
                        String activityRound = splittedActivityCode[1];

                        int roundNumber = Character.getNumericValue(activityRound.charAt(1));

                        String fullEventName = EventId.fromString(activityCode).getFullName();
                        if (fullEventName.endsWith(" Cube"))
                            fullEventName = fullEventName.substring(0, fullEventName.length() - 5);

                        EventDetails eventDetails = new EventDetails(activityCode, fullEventName, venue.getName(), room.getName(), roundNumber, activity.getStartTime());

                        if (activityCode.equals("333fm") || activityCode.equals("333mbf")) {
                            int attemptNumber = Character.getNumericValue(splittedActivityCode[2].charAt(1));

                            eventDetails.setAttempt(attemptNumber);
                        }
                        else {
                            for (Activity child : activity.getChildActivities()) {
                                String[] splittedChildActivityCode = child.getActivityCode().split("-");

                                int groupNumber = Character.getNumericValue(splittedChildActivityCode[2].charAt(1));

                                EventGroupDetails eventGroupDetails = new EventGroupDetails((char)('A'+groupNumber-1), groupNumber, child.getStartTime());

                                eventDetails.getEventGroupDetails().add(eventGroupDetails);
                            }
                        }

                        allEventDetails.add(eventDetails);
                    }
                }
            }
        }

        Collections.sort(allEventDetails);

        reorganizePDF(directory, allEventDetails);
        reorganizePasscode(directory, allEventDetails, name);
    }

    private void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    }
                    catch (IOException e) {
                        System.err.println("Failed to delete: " + path + " - " + e.getMessage());
                    }
                });
        }
    }

    private void zipDirectory(Path sourceDirPath, Path zipFilePath) throws IOException {
        Path interchangeFolderPath = sourceDirPath.resolve("Interchange");
        Path printingFolderPath = sourceDirPath.resolve("Printing");

        deleteDirectory(interchangeFolderPath);
        deleteDirectory(printingFolderPath);

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
            Files.walk(sourceDirPath)
                .filter(path -> !sourceDirPath.equals(path))
                .forEach(path -> {
                        String entryName = sourceDirPath.relativize(path).toString().replaceAll("\\\\", "/");

                        if (Files.isDirectory(path) && !entryName.endsWith("/"))
                            entryName += "/";

                        try {
                            ZipEntry zipEntry = new ZipEntry(entryName);
                            zos.putNextEntry(zipEntry);

                            if (!Files.isDirectory(path))
                                Files.copy(path, zos);

                            zos.closeEntry();
                        } catch (IOException e) {
                            System.err.println("Error while zipping file: " + e.getMessage());
                        }
                    }
                );
        }
    }

    public File organizeScrambles(File scrambleZip, PublicWcif wcif) {
        if (wcif == null)
            throw new NullPointerException("WCIF Object is null");

        final String scrambleZipFileName = wcif.getName() + " - Computer Display PDFs.zip";
        final String scramblePasscodeFileName = wcif.getName() + " - Computer Display PDF Passcodes - SECRET.txt";

        try {
            Path tempDir = Files.createTempDirectory("scramble-manager-temp");

            // Unzipping original outer scramble file to temp folder
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

            try (ZipInputStream innerZis = new ZipInputStream(Files.newInputStream(scrambleZipFilePath))) {
                ZipEntry innerEntry = innerZis.getNextEntry();

                while (innerEntry != null) {
                    Path newPath = tempDir.resolve(innerEntry.getName());
                    if (innerEntry.isDirectory())
                        Files.createDirectories(newPath);
                    else {
                        if (newPath.getParent() != null)
                            Files.createDirectories(newPath.getParent());
                        Files.copy(innerZis, newPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    innerEntry = innerZis.getNextEntry();
                }
                innerZis.closeEntry();
            }
            catch (IOException e) {
                System.err.println("Error while extracting ZIP: " + e.getMessage());
            }

            Files.delete(scrambleZipFilePath);

            createFolderFromVenues(tempDir, wcif.getSchedule().getVenues(), wcif.getName());

            // Create a new temp file to hold the final zipped output
            Path outputZipPath = Files.createTempFile("[ORGANIZED] - " + wcif.getName() + "-", ".zip");

            // Compress the reorganized tempDir into the output zip
            zipDirectory(tempDir, outputZipPath);

            System.out.println("Successfully zipped to: " + outputZipPath.toString());

            return outputZipPath.toFile();
        }
        catch (IOException e) {
            System.err.println("Error occured while processing zip: " + e.getMessage());
            return null;
        }
    }
}
