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

@Controller
@RequestMapping("/userIndex")
public class IndexPageController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping("/{id}")
    public String getUserIndexPage(
            @PathVariable long id,
            Model model
    ){
        UserModel userModel = userRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
        String name = "Здравствуйте " +  detailUserModel.getFirstName() + " " + detailUserModel.getLastName();
        model.addAttribute("id",detailUserModel.getId());
        model.addAttribute("Name",name);
        return "indexUser";
    }
}
