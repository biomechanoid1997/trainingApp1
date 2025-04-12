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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/moderateUsers")
public class moderateUsersController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping()
    public String viewUsers(
            Model model
    ) {
        List<UserModel> userModelList = userRepo.findAllByUserType("user");
        List<UserModel> moderatorist = userRepo.findAllByUserType("moderator");
        List<DetailUserModel> detailUserModelList = detailUserRepo.findAll();
        List<ViewUserHelper> viewUserHelperList = new ArrayList<>();
        List<ViewUserHelper> moderatorHelperList = new ArrayList<>();
        List<UserModel> bannedUserList = userRepo.findAllByUserType("banned");
        if (bannedUserList.size() > 0) {
        for (int i = 0; i < bannedUserList.size(); i++) {
            userModelList.add(bannedUserList.get(i));
        }
    }
        for (int i = 0; i < userModelList.size(); i++) {
            userModelList.sort(Comparator.comparing(UserModel::getId));
            String moderatorLine ="Сделать модератором";
            String banLine = "Заблокировать";
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
        for (int i = 0; i < moderatorist.size(); i++) {
            userModelList.sort(Comparator.comparing(UserModel::getId));
            String moderatorLine ="Сделать модератором";
            String banLine = "Заблокировать";
            if (userModelList.get(i).getUserType().equals("banned")){
                banLine = "разблокировать";
            }
            String userName = detailUserModelList.get(i).getFirstName() + " " + detailUserModelList.get(i).getLastName();
            String userType = "модератор";


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
            moderatorHelperList.add(viewUserHelper);

        }
        model.addAttribute("userList",viewUserHelperList);
        model.addAttribute("moderatorList",moderatorHelperList);
        return "moderateUsers";
    }
}
