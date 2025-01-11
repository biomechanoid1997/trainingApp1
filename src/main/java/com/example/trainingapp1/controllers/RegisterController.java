package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.UserModel;
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
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping
    public String getRegisterPage(){
        return "RegisterUser";
    }

///////////////////////////////////////////////////////
    @PostMapping
    public RedirectView postUser(@RequestParam String login,
                                 @RequestParam String password,
                                 @RequestParam String email) throws Exception{
        DetailUserModel detailUserModel = new DetailUserModel();
        UserModel userModel = new UserModel();
        userModel.setUserType("user");
        userModel.setLogin(login);
        userModel.setEmail(email);
        userModel.setUserStatus("offline");
       userModel.setPassword(password);
       detailUserModel.setEmail(email);
       detailUserModel.setUserName(login);
        userRepo.save(userModel);
        ArrayList<UserModel> userlist = (ArrayList<UserModel>) userRepo.findAll();
        UserModel userModel1 = userlist.get(userlist.size()-1);
        detailUserModel.setUserTableId(userModel1.getId());
        detailUserRepo.save(detailUserModel);
     return new RedirectView("/DetailUser");
    }
    //////////////////////////////////////////////////////////////

}
