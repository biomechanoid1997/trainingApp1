package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.models.PropertyModel;
import com.example.trainingapp1.repos.*;
import com.example.trainingapp1.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/leaveRequest")
public class RequestController {
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MessageRepo messageRepo;
    @Autowired
    PropertyRepo propertyRepo;

    @GetMapping("/{id}")
    public String getRequestPage(
            @PathVariable long id,
           Model model
    ) {
       // model.addAttribute("id",id);
        return "requestPage";
    }

    @PostMapping("/{id}")
    public RedirectView sendRequestMessage(
            @RequestParam String tennantName,
            @RequestParam String phoneNumber,
            @RequestParam String telegramAccount,
            @RequestParam String message,
            @PathVariable long id,
            Model model
    ){
        MessageModel messageModel = new MessageModel();
        PropertyModel propertyModel = propertyRepo.findById(id);
        long ownerId = propertyModel.getOwnerID();
        DetailUserModel detailUserModel = detailUserRepo.findById(ownerId);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:YY");
        String messageDate = simpleDateFormat.format(date);
        String contactInfo = "Имя отправителя: " + tennantName + " телефонный номер: " + phoneNumber + " Телеграмм: " + telegramAccount;
        messageModel.setMessageText(message);
        messageModel.setMessageType("rental_request");
        messageModel.setContactInfo(contactInfo);
        messageModel.setRequestPropertyId(propertyModel.getId());
        messageModel.setReceiverID(propertyModel.getOwnerID());
        messageModel.setMessageDate(messageDate);
        messageModel.setMessageStatus("unread");
        messageRepo.save(messageModel);
        return new RedirectView("/viewRental/{id}");
    }

}
