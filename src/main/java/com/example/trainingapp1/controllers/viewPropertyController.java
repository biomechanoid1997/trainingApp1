package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.PropertyImageModel;
import com.example.trainingapp1.models.PropertyModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.PropertyImageRepo;
import com.example.trainingapp1.repos.PropertyRepo;
import com.example.trainingapp1.repos.UserRepo;
import com.example.trainingapp1.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/adminProperty")
public class viewPropertyController {
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PropertyRepo propertyRepo;
    @Autowired
    PropertyImageRepo propertyImageRepo;
    @Autowired
    FirebaseService firebaseService;

    @GetMapping("/{id}")
    public String editUserProperty(
            @PathVariable long id,
            Model model
    ){
        PropertyModel propertyModel = propertyRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findById((long) propertyModel.getOwnerID());
        List<PropertyImageModel> propertyImages = propertyImageRepo.findPropertyImageModelsByPropertyTableId(propertyModel.getId());
        for (int i = 0; i < propertyImages.size(); i++) {
            propertyImages.get(i).setImageUrl(firebaseService.getUrl(propertyImages.get(i).getImageUrl()));
        }
        String ownerId = String.valueOf(propertyModel.getOwnerID());
        String owner = "Владелец: " + detailUserModel.getLastName() + " " + detailUserModel.getFirstName();
        model.addAttribute("owner",owner);
        model.addAttribute("property",propertyModel);
        if (propertyModel.getAllowChildren().equals("true")){
            model.addAttribute("allowChildren","Можно проживать детям");
        }else{
            model.addAttribute("allowChildren","Не рекомендуется брать детей");
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////
        if (propertyModel.getRentalStatus().equals("vacant")){
            propertyModel.setRentalStatus("Свободно");
        }
        if (propertyModel.getRentalStatus().equals("occupied")){
            propertyModel.setRentalStatus("Занято");
        }
        if (propertyModel.getRentalStatus().equals("maintenance")){
            propertyModel.setRentalStatus("В ремонте");
        }
        if (propertyModel.getRentalStatus().equals("closed")){
            propertyModel.setRentalStatus("закрыто");
        }
        if (propertyModel.getRentalType().equals("room")){
            propertyModel.setRentalType("комната");
        }
        if (propertyModel.getRentalType().equals("flat")){
            propertyModel.setRentalType("квартира");
        }
        if (propertyModel.getRentalType().equals("hostel")){
            propertyModel.setRentalType("Хостел/общежитие");
        }
        if (propertyModel.getRentalType().equals("hotel")){
            propertyModel.setRentalType("Отель/гостиница");
        }
        if (propertyModel.getRentalType().equals("house")){
            propertyModel.setRentalType("Дом/коттедж");
        }
        model.addAttribute("propertyImages",propertyImages);
        return "propertyAdminPage";
    }
    @PostMapping("/{id}")
    public RedirectView updateUserProperty(
            @PathVariable long id,
            Model model,

            @RequestParam String rentalStatus
    ) throws Exception{
        PropertyModel propertyModel = propertyRepo.findById(id);
        propertyModel.setRentalStatus(rentalStatus);
        propertyRepo.save(propertyModel);
        return new RedirectView("/detailUserPage/"+ propertyModel.getOwnerID());
    }
}
