package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable Long id,
                              Model model){
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(id);
        String name = "Здравствуйте " + detailUserModel.getFirstName();
        model.addAttribute("Name",name);
        model.addAttribute("ID",id);
        return "userPage";
    }
}
