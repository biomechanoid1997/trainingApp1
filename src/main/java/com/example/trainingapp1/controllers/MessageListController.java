package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.MessageRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/messages")
public class MessageListController {
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MessageRepo messageRepo;

    @GetMapping("/{id}")
    public String getMessageList(
            @PathVariable long id,
            Model model
    ){
        DetailUserModel detailUserModel = detailUserRepo.findById(id);
        ArrayList<MessageModel> messageModelList = new ArrayList<>(messageRepo.findAllByReceiverID(detailUserModel.getId())) ;
        if (messageModelList.isEmpty()){
            model.addAttribute("noMessages","Сообщений пока нет, но скоро будут...");
        }
        for (int i = 0; i < messageModelList.size(); i++) {
            if (messageModelList.get(i).getMessageStatus().equals("unread")){
                messageModelList.get(i).setMessageStatus("Непрочитано");
            }
            if (messageModelList.get(i).getMessageStatus().equals("read")){
                messageModelList.get(i).setMessageStatus("Прочитано");
            }
            if (messageModelList.get(i).getMessageType().equals("rental_request")){
                messageModelList.get(i).setMessageType("Заявка на аренду");
            }
        }
        model.addAttribute("messages",messageModelList);
        return "messagePage";
    }

}
