package com.example.trainingapp1.controllers;

import com.example.trainingapp1.helpers.ViewUserHelper;
import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/viewUsers")
public class ViewUserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping()
    public String viewUsers(
            Model model
    ){
        List<UserModel> userModelList = userRepo.findAll();
        List<DetailUserModel> detailUserModelList = detailUserRepo.findAll();
        List<ViewUserHelper> viewUserHelperList = new ArrayList<>();
        for (int i = 0; i < userModelList.size(); i++) {
       userModelList.sort(Comparator.comparing(UserModel::getId));
       detailUserModelList.sort(Comparator.comparing(DetailUserModel::getId));
            String moderatorLine ="Сделать модератором";
            String banLine = "Заблокировать";
            if(userModelList.get(i).getUserType().equals("moderator")){
                moderatorLine = "Лишить модераторства";
            }
            if (userModelList.get(i).getUserType().equals("banned")){
                banLine = "разблокировать";
            }
            String userName = detailUserModelList.get(i).getFirstName() + " " + detailUserModelList.get(i).getLastName();
                 String userType = "пользователь";
             if (userModelList.get(i).getUserType().equals("moderator")){
                 userType = "модератор";
             }
            if (userModelList.get(i).getUserType().equals("banned")){
                userType = "заблокированный";
            }
            ViewUserHelper viewUserHelper = new ViewUserHelper(
                    userModelList.get(i).getId(),
                    userModelList.get(i).getLogin(),
                    userModelList.get(i).getEmail(),
                    userModelList.get(i).getUserStatus(),
                    userType,
                    userName,
                    moderatorLine,
                    banLine
                    );
            viewUserHelperList.add(viewUserHelper);
        }
      
        model.addAttribute("userList",viewUserHelperList);
        return "viewUsers";
    }
}
