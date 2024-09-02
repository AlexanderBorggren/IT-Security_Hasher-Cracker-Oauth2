package com.example.ITSec_Password_Cracker_Individual_Assigment_VG.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HashingController {

    @GetMapping("/hasher")
    public String hasher(Model model) {
        return "hasher";
    }

    @PostMapping("/hashText")
    public String gatherText(@RequestParam("inputText") String textToHash, Model model) throws NoSuchAlgorithmException {

        Map<String, String> hashedValues = new HashMap<>();

        hashedValues.put("hashedMD5", hashText(textToHash, "MD5"));
        hashedValues.put("hashedSHA256", hashText(textToHash, "SHA-256"));

        model.addAttribute("hashedValues", hashedValues);

        return "hasher";
    }


    private String hashText(String text, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
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

