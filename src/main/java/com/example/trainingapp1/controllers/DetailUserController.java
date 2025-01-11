package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
@RequestMapping("/DetailUser")
public class DetailUserController {

    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping
    public String getRegisterPage(){
        return "detailRegister";
    }

    @PostMapping
    public RedirectView saveUserData(@RequestParam String firstName,
                                     @RequestParam String lastName,
                                     @RequestParam String birthDate,
                                     @RequestParam String sex,
                                     @RequestParam String maritalStatus
    ){
        ArrayList<DetailUserModel> arrayList = new ArrayList<DetailUserModel>(detailUserRepo.findAll());
        DetailUserModel detailUserModel = arrayList.get(arrayList.size()-1);
        detailUserModel.setFirstName(firstName);
        detailUserModel.setLastName(lastName);
        detailUserModel.setDateOfBirth(birthDate);
        detailUserModel.setSex(sex);
        detailUserModel.setMaritalStatus(maritalStatus);
        return new RedirectView("/");
    }
}
