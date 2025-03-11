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
@RequestMapping("/replyAppeal")
public class AppealReplyController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    MessageRepo messageRepo;

    @GetMapping("/{id}")
    public String getReplyPage(
      @PathVariable long id,
      Model model
    ){
        return "appealReplyMessagePage";
    }
    @PostMapping("/{id}")
    public RedirectView sendReply(
            @PathVariable long id,
            Model model,
            @RequestParam String replyText
    ){
        UserModel userModel = userRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
        MessageModel messageModel = new MessageModel();
        messageModel.setMessageType("messageFromAdmin");
        messageModel.setMessageText(replyText);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:YY");
        String messageDate = simpleDateFormat.format(date);
        messageModel.setMessageStatus(messageDate);
        messageModel.setMessageStatus("unread");
        messageModel.setReceiverID(userModel.getId());
        messageRepo.save(messageModel);
        return new RedirectView("/adminUserMessages");
    }
}
