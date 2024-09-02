package com.example.ITSec_Password_Cracker_Individual_Assigment_VG;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class HashGenerator implements CommandLineRunner {

    private static final String INPUT_FILE = "src/input.txt";
    private static final String OUTPUT_FILE = "src/hashes.txt";


    @Override
    public void run(String... args) throws Exception {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable statusUpdateTask = () -> System.out.println("Processing... This is gonna take a while...");

        scheduler.scheduleAtFixedRate(statusUpdateTask, 1, 10, TimeUnit.SECONDS);

        File outputFile = new File(OUTPUT_FILE);

        if (outputFile.exists() && outputFile.length() > 0) {
            System.out.println("Output file already exists and is not empty. Aborting write operation.");
            scheduler.shutdown();
            return;
        }

        createOutputFileIfNotExists(OUTPUT_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {

            System.out.println("File read from: " + INPUT_FILE);
            System.out.println("File is being written to: " + OUTPUT_FILE);

            String line;
            while ((line = reader.readLine()) != null) {
                String md5Hash = hashText(line, "MD5");
                String sha256Hash = hashText(line, "SHA-256");
                writer.write(line + ":" + md5Hash + ":" + sha256Hash);
                writer.newLine();
            }
            scheduler.shutdown();
            System.out.println("File is completed and written to: " + OUTPUT_FILE);

        } catch(IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void createOutputFileIfNotExists(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("File already exists: " + fileName);
            }
        }
    }

    private String hashText(String text, String algorithm) throws NoSuchAlgorithmException {
        java.security.MessageDigest digest = java.security.MessageDigest.getInstance(algorithm);
        byte[] encodedHash = digest.digest(text.getBytes());

        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
