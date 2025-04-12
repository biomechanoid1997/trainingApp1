package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.models.PropertyModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.MessageRepo;
import com.example.trainingapp1.repos.PropertyRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MessageRepo messageRepo;
    @Autowired
    PropertyRepo propertyRepo;
    @GetMapping("/{id}")
    public String readMessage(
            Model model,
            @PathVariable long id
    ) {

        MessageModel messageModel = messageRepo.findById(id);
        /////////////////////////////////////////////////////////////////////////////////////////////////
        //Заявка на аренду
        if (messageModel.getMessageType().equals("rental_request")) {
            PropertyModel propertyModel = propertyRepo.findById(messageModel.getRequestPropertyId());
            messageModel.setMessageStatus("read");
            messageRepo.save(messageModel);
            //////////////////////////////////////////////////////////////
            if (propertyModel.getRentalType().equals("room")) {
                propertyModel.setRentalType("комната");
            }
            if (propertyModel.getRentalType().equals("flat")) {
                propertyModel.setRentalType("квартира");
            }
            if (propertyModel.getRentalType().equals("hostel")) {
                propertyModel.setRentalType("Хостел/общежитие");
            }
            if (propertyModel.getRentalType().equals("hotel")) {
                propertyModel.setRentalType("Отель/гостиница");
            }
            if (propertyModel.getRentalType().equals("house")) {
                propertyModel.setRentalType("Дом/коттедж");
            }
            //////////////////////////////////////////////////////////////////
            String address = "Адрес аренды: город " + propertyModel.getCity() + " " + propertyModel.getAddress();
            String rentalType = "Тип арендуемой собственности: " + propertyModel.getRentalType();
            model.addAttribute("address", address);
            model.addAttribute("rentalType", rentalType);
            model.addAttribute("contactInfo", messageModel.getContactInfo());
            model.addAttribute("messageText", messageModel.getMessageText());
            return "request";
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////
     ////////////////////апелляция на бан
        if (messageModel.getMessageType().equals("banAppeal")){
            UserModel userModel = userRepo.findById(messageModel.getSenderID());
            DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
            String appealInfo = "Пользователь: " + detailUserModel.getFirstName() + " " +detailUserModel.getLastName();
            String contactInfo = messageModel.getContactInfo();
            String messageText = messageModel.getMessageText();
            messageModel.setMessageStatus("read");
            messageRepo.save(messageModel);
            model.addAttribute("contactInfo",contactInfo);
            model.addAttribute("messageText",messageText);
            model.addAttribute("id",messageModel.getSenderID());
            return "messageFromUser";
        }
        ///////////////////////////////////////////////////////////////////////////
        ///////////////////////сообщение от администрации
        if (messageModel.getMessageType().equals("messageFromAdmin")){
            UserModel userModel = userRepo.findById(messageModel.getReceiverID());
            DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
            String appealInfo = "Пользователь: " + detailUserModel.getFirstName() + " " +detailUserModel.getLastName();
            String messageText = messageModel.getMessageText();
            messageModel.setMessageStatus("read");
            messageRepo.save(messageModel);
            model.addAttribute("messageText",messageText);
            model.addAttribute("id",messageModel.getSenderID());
            return "messageFromAdmin";
        }
        //////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////Сообщение от модераторов
        if (messageModel.getMessageType().equals("messageFromModerator")){
            UserModel userModel = userRepo.findById(messageModel.getReceiverID());
            DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
            String appealInfo = "Пользователь: " + detailUserModel.getFirstName() + " " +detailUserModel.getLastName();
            String messageText = messageModel.getMessageText();
            messageModel.setMessageStatus("read");
            messageRepo.save(messageModel);
            model.addAttribute("messageText",messageText);
            model.addAttribute("id",messageModel.getSenderID());
            return "messageFromAdmin";
        }
        //////////////////////////////Сообщение от модераторов
        if (messageModel.getMessageType().equals("messageToAdmin")){
            UserModel userModel = userRepo.findById(messageModel.getSenderID());
            DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userModel.getId());
            String messageText = messageModel.getMessageText();
            String contactInfo = messageModel.getContactInfo();
            messageModel.setMessageStatus("read");
            messageRepo.save(messageModel);
            model.addAttribute("contactInfo",contactInfo);
            model.addAttribute("messageText",messageText);
            model.addAttribute("id",messageModel.getSenderID());
            return "messageFromUser";
        }
       ////////////////////////////////////////////////////////////////////
        return "request";
    }

}
