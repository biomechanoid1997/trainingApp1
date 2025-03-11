package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.MessageRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adminUserMessages")
public class AdminUserMessageController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    MessageRepo messageRepo;

    @GetMapping()
    public String getMessagePage(
            Model model
    ){
        List<MessageModel>messageModelList = messageRepo.findAll();
        List<MessageModel>messages = new ArrayList<MessageModel>();
        for (int i = 0; i < messageModelList.size(); i++) {
            if (messageModelList.get(i).getMessageType().equals("banAppeal")){
                messages.add(messageModelList.get(i));
            }
        }
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getMessageType().equals("banAppeal")){
                messages.get(i).setMessageType("Апелляция на блокировку");
            }
        }

        model.addAttribute("messages",messages);
      return "adminUserMessagePage";
    }
}
