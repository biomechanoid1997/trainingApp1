package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.PropertyImageModel;
import com.example.trainingapp1.models.PropertyModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.PropertyImageRepo;
import com.example.trainingapp1.repos.PropertyRepo;
import com.example.trainingapp1.repos.UserRepo;
import com.example.trainingapp1.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/property")
public class PropertyController {
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

    @GetMapping("/viewProperty/{id}")
    public String viewUserProperty(
            @PathVariable Long id,
            Model model
    ){
        model.addAttribute("id",id);
        DetailUserModel detailUserModel =detailUserRepo.findDetailUserModelByUserTableId(id);
        ArrayList<PropertyModel> propertyModels = new ArrayList<>(propertyRepo.findAllByOwnerID(id)) ;
        ArrayList<PropertyImageModel> propertyImageModels = new ArrayList<>(propertyImageRepo.findAll());
        ArrayList<Long> propertyIDlist = new ArrayList<Long>();
        ArrayList<Long> ownerPropertyIdList = new ArrayList<Long>();
        propertyIDlist.add(propertyImageModels.get(0).getPropertyTableId());
        ArrayList<PropertyImageModel> userImages = new ArrayList<PropertyImageModel>();
        long ownerId = (long) model.getAttribute("id");
        for (int i = 0; i < propertyModels.size(); i++) {
            PropertyModel propertyModel = propertyModels.get(i);
            if (propertyModel.getAllowChildren().equals("true")){
                propertyModel.setAllowChildren("Да");
            }
            if (propertyModel.getAllowChildren().equals("false")){
                propertyModel.setAllowChildren("Нет");
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
                propertyModel.setRentalStatus("Закрыто");
            }
        }

        // here we find owners property we have(done)
        for (int i = 0; i < propertyModels.size(); i++) {
            if (propertyModels.get(i).getOwnerID().equals(id)){
                ownerPropertyIdList.add(propertyModels.get(i).getId());
            }
        }
        // here we check how many owner Images we have(must work here)
        String testString = "Property image ID's ";
        for (int i = 0; i < propertyImageModels.size(); i++) {
            for (int j = 0; j < ownerPropertyIdList.size(); j++) {
                if (propertyImageModels.get(i).getPropertyTableId()==ownerPropertyIdList.get(j)){
                    testString = testString + propertyImageModels.get(i).getId() + " ";
                  userImages.add(propertyImageModels.get(i));
                }
            }
        }
        ///////////////////test



///////////////////////////////////////////////////
        for (int i = 0; i < userImages.size(); i++) {
            userImages.get(i).setImageUrl(firebaseService.getUrl(propertyImageModels.get(i).getImageUrl()));
        }
        /////////////////////////////
        model.addAttribute("userImages",userImages);
        model.addAttribute("ownerId",ownerId);
        model.addAttribute("testString",testString);
        model.addAttribute("propertyList",propertyModels);
        return "propertyPage";
    }

}
