package com.example.ITSec_Password_Cracker_Individual_Assigment_VG.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Controller
public class CrackerController {

    @GetMapping("/cracker")
    public String cracker(Model model) {
        return "cracker";
    }


    @PostMapping("/crackText")
    public String crackText(@RequestParam("inputText") String textToMatch, Model model) {

        long startTime = System.nanoTime(); // Start time for benchmarking

        String crackedPassword = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/hashes.txt"))) {
            Stream<String> lines = reader.lines();

            System.out.println("Text to crack: " + textToMatch + "\n" + "hold on this will take a while..");

            crackedPassword = lines.filter(line -> {
                        String[] parts = line.split(":");
                        if (parts.length == 3) {
                            String md5Hash = parts[1];
                            String sha256Hash = parts[2];

                            return md5Hash.equals(textToMatch) || sha256Hash.equals(textToMatch);
                        }
                        return false;
                    }).map(line -> line.split(":")[0])
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (crackedPassword != null) {
            model.addAttribute("crackedValues", crackedPassword);
            System.out.println("Match found: " + crackedPassword);

            long endTime = System.nanoTime(); // End time for benchmarking
            long durationInMillis = (endTime - startTime) / 1_000_000; // Convert to milliseconds

            System.out.println("Time taken: " + durationInMillis + " ms");
        } else {
            model.addAttribute("noMatch", "No match found.");
            System.out.println("No match found.");
        }

        return "cracker";
    }


    }


