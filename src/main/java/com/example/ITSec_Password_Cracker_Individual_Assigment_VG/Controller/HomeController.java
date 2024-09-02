package com.example.ITSec_Password_Cracker_Individual_Assigment_VG.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

}



