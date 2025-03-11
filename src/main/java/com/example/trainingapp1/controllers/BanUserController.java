package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/banUser")
public class BanUserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping("/{id}")
    public RedirectView banUser(
            Model model,
            @PathVariable long id
    ){
        UserModel userModel = userRepo.findById(id);
        if (userModel.getUserType().equals("banned")){
            userModel.setUserType("user");
            userRepo.save(userModel);
            return new RedirectView("/viewUsers");
        }
        userModel.setUserType("banned");
        userRepo.save(userModel);
        return new RedirectView("/viewUsers");
    }
}
