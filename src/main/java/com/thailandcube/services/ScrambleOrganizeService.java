package com.thailandcube.services;

import com.thailandcube.models.PublicWcif;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class ScrambleOrganizeService {
    private static final ScrambleOrganizeService instance = new ScrambleOrganizeService();

    private ScrambleOrganizeService() {

    }

    public static ScrambleOrganizeService getInstance() {
        return instance;
    }

    public void organizeScrambles(File scrambleZip, PublicWcif wcif) {
        if (wcif == null)
            throw new NullPointerException("WCIF Object is null");

        try (ZipFile zipFile = new ZipFile(scrambleZip)) {

        }
        catch (IOException e) {
            System.err.println("Failed to open or read the ZIP file.");
            e.printStackTrace();
        }
    }
}
