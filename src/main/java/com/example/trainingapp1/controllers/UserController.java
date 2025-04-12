package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.MessageRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    MessageRepo messageRepo;

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable long id,
                              Model model){
        UserModel userModel = userRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
        List<MessageModel> messageModels = messageRepo.findAllByReceiverID(id);
        long unreadMessages = 0;
        if (messageModels.size()!=0){
            for (int i = 0; i < messageModels.size(); i++) {
                if (messageModels.get(i).getMessageType().equals("unread")){
                    unreadMessages = unreadMessages + 1;
                }
            }
        }
        String newMessages = "(Новых сообщений: " + unreadMessages + " )";
        ////////////////////////////////////////////////////////////////////////////////////////
        /////Если пользователь получил модераторство
        String name = "Здравствуйте " + detailUserModel.getFirstName();
        model.addAttribute("Name",name);
        model.addAttribute("ID",id);

        if (userModel.getUserType().equals("moderator")){
            return "moderatorPage";
        }
        if (userModel.getUserType().equals("user")){
            return "userPage";
        }

        model.addAttribute("newMessages",newMessages);
        return "userPage";
    }
}
