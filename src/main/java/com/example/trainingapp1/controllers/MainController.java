package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    UserRepo userRepo;
@GetMapping
    public String GetMainPage(){
    boolean anyAdmins = false;
    List<UserModel> userModelList = userRepo.findAll();
    for (int i = 0; i < userModelList.size() ; i++) {
        if (userModelList.get(i).getUserType().equals("admin")){
            anyAdmins = true;
        }
    }
    if ( !anyAdmins){
        UserModel userModel = new UserModel();
        userModel.setUserType("admin");
        userModel.setLogin("admin");
        userModel.setPassword("password");
        userRepo.save(userModel);
    }
    return "index";
}
}
