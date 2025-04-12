package com.example.trainingapp1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aboutUs")
public class AboutUsController {

    @GetMapping
    public String getInfo(){
        return "aboutUs";
    }
}
