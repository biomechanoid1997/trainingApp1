package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
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
@RequestMapping("/editModerator")
public class GiveRemoveModerationController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @GetMapping("/{id}")
    public RedirectView editModerator(
            @PathVariable long id,
            Model model
    ){
        DetailUserModel detailUserModel = detailUserRepo.findById(id);
        UserModel userModel = userRepo.findById(detailUserModel.getUserTableId());
        if (userModel.getUserType().equals("moderator")){
            userModel.setUserType("user");
            detailUserRepo.save(detailUserModel);
            userRepo.save(userModel);
            return new RedirectView("/viewUsers");
        }
        userModel.setUserType("moderator");
        detailUserRepo.save(detailUserModel);
        userRepo.save(userModel);
        return new RedirectView("/viewUsers");
    }
}
