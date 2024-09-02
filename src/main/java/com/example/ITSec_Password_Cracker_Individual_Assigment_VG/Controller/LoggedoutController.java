package com.example.ITSec_Password_Cracker_Individual_Assigment_VG.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoggedoutController {

    @GetMapping("/logged-out")
    public String loggedOut() {
        System.out.println("Logged out");
        return "logged-out";
    }
}
