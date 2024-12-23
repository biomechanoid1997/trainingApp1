package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String getRegisterPage(){
        return "RegisterUser";
    }

///////////////////////////////////////////////////////
    @PostMapping
    public RedirectView postUser(@RequestParam String login,
                                 @RequestParam String password) throws Exception{
        UserModel userModel = new UserModel();
        userModel.setUserType("user");
        userModel.setLogin(login);
       userModel.setPassword(password);
        userRepo.save(userModel);

     return new RedirectView("/");
    }
    //////////////////////////////////////////////////////////////

}
