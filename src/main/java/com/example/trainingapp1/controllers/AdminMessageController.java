package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.MessageRepo;
import com.example.trainingapp1.repos.UserRepo;
import io.netty.handler.codec.MessageAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/adminMessage")
public class AdminMessageController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    MessageRepo messageRepo;
    @GetMapping("/{id}")
    public String getMessagePage(
            @PathVariable long id,
            Model model
    ){
        return "adminMessagePage";
    }
    @PostMapping("/{id}")
    public RedirectView sendMessage(
            @PathVariable long id,
            Model model,
            @RequestParam String messageText
    ){
        UserModel userModel = userRepo.findById(id);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:YY");
        String messageDate = simpleDateFormat.format(date);
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
        MessageModel messageModel = new MessageModel();
        messageModel.setMessageStatus("unread");
        messageModel.setMessageType("messageFromAdmin");
        messageModel.setMessageDate(messageDate);
        messageModel.setMessageText(messageText);
        messageModel.setReceiverID(userModel.getId());
        messageRepo.save(messageModel);
     return new RedirectView("/viewUsers");
    }
}
