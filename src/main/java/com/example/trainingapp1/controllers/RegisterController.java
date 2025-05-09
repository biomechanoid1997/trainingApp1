package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String postUser(@RequestParam String login,
                                 @RequestParam String password,
                                 @RequestParam String email,
                                 @RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String birthDate,
                                 @RequestParam String sex,
                                 @RequestParam String maritalStatus,
                                 Model model) throws Exception{
        DetailUserModel detailUserModel = new DetailUserModel();
        boolean isUniqueUser = true;
        boolean isUniqueEmail = true;
        boolean isEmailValid = false;
       String userError = "Данный пользователь уже существует";
       String emailError= "Данный адрес электронной почты уже занят";
       String validationError="Введите адрес электронной почты";
        ArrayList<UserModel>userModels = new ArrayList<UserModel>(userRepo.findAll());
        model.addAttribute("text","Текст здесь");
        if (login.equals("admin")){isUniqueUser = false;}
        for (int i = 0; i < userModels.size(); i++) {
            UserModel userModel1 = userModels.get(i);
            if (login.equals(userModel1.getLogin())){
                model.addAttribute("userError",userError);
                isUniqueUser = false;
            }
            if (email.equals(userModel1.getEmail())){
                isUniqueEmail = false;
                model.addAttribute("emailError",emailError);
            }
        }

        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i)=='@'){
                for (int j = i; j < email.length(); j++) {
                    if (email.charAt(j)=='.'){
                        isEmailValid = true;
                        break;
                    }
                }
                break;
            }
        }

        if (!isEmailValid){
            model.addAttribute("validationError",validationError);
        }

        if (isUniqueUser==false || isUniqueEmail==false || isEmailValid==false){
            model.addAttribute("firstName",firstName);
            model.addAttribute("lastName",lastName);
            model.addAttribute("birthdate",birthDate);
            model.addAttribute("sex",sex);
            model.addAttribute("maritalStatus",maritalStatus);
            return "RegisterUser";
        }



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
        detailUserModel.setFirstName(firstName);
        detailUserModel.setLastName(lastName);
        detailUserModel.setDateOfBirth(birthDate);
        detailUserModel.setSex(sex);
        detailUserModel.setMaritalStatus(maritalStatus);
        detailUserRepo.save(detailUserModel);
     return "index";
    }
    //////////////////////////////////////////////////////////////

}
