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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/banAppeal")
public class BanAppealController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    MessageRepo messageRepo;
    @GetMapping("/{id}")
    public String getAppealPage(
            @PathVariable long id,
            Model model
    ){
        return "appealMessagePage";
    }

    @PostMapping("/{id}")
    public RedirectView sendAppeal(
            @RequestParam String appealText,
            @PathVariable long id,
            Model model
    ){
        UserModel userModel = userRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(id);
        MessageModel messageModel = new MessageModel();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:YY");
        String messageDate = simpleDateFormat.format(date);
        String contactInfo = "Логин пользователя: " + userModel.getLogin() + " Имя: " + detailUserModel.getFirstName()+" " + detailUserModel.getLastName();
        messageModel.setMessageType("banAppeal");
        messageModel.setMessageStatus("unread");
        messageModel.setMessageText(appealText);
        messageModel.setContactInfo(contactInfo);
        messageModel.setMessageDate(messageDate);
        messageModel.setSenderID(userModel.getId());
        messageRepo.save(messageModel);
        return new RedirectView("/userBanned/" + id);
    }
}
