package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.MessageModel;
import com.example.trainingapp1.models.PropertyModel;
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

        return "request";
    }

}
