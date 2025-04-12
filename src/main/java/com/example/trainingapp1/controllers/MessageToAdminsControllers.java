package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.MessageRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/messageToAdmin")
public class MessageToAdminsControllers {
    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping("/{id}")
    public String getMessagPage(
            @PathVariable long id,
            Model model
    ){
   return "messageToAdmin";
    }

    @PostMapping("/{id}")
    public RedirectView sendAdminMessage(
            @PathVariable long id,
            Model model,
            @RequestParam String messageText
    ){
        UserModel userModel = userRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:YY");
        String messageDate = simpleDateFormat.format(date);
        String userContact = "Пользователь: " + detailUserModel.getFirstName() + " " + detailUserModel.getLastName()+ " Логин: " + userModel.getLogin() ;
        MessageModel messageModel = new MessageModel();
        messageModel.setContactInfo(userContact);
        messageModel.setMessageDate(messageDate);
        messageModel.setMessageText(messageText);
        messageModel.setMessageType("messageToAdmin");
        messageModel.setSenderID(id);
        messageModel.setMessageStatus("unread");
        messageRepo.save(messageModel);
        return new RedirectView("/user/"+id);
    }
}
