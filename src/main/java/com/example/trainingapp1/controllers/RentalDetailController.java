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

import java.util.List;

@Controller
@RequestMapping("/viewRental")
public class RentalDetailController {
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
    public String getRentalDetailPage(
            @PathVariable long id,
            Model model
    ){
        PropertyModel propertyModel = propertyRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findById((long) propertyModel.getOwnerID());
        List<PropertyImageModel> propertyImages = propertyImageRepo.findPropertyImageModelsByPropertyTableId(propertyModel.getId());
        for (int i = 0; i < propertyImages.size(); i++) {
            propertyImages.get(i).setImageUrl(firebaseService.getUrl(propertyImages.get(i).getImageUrl()));
        }
        if (propertyModel.getAllowChildren().equals("true")){
            model.addAttribute("allowChildren","Можно проживать детям");
        }else{
            model.addAttribute("allowChildren","Не рекомендуется брать детей");
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////
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
        //////////////////////////////////////////////////////////////////////////////////////////////////

        String address = "Город: " + propertyModel.getCity() + ", Адрес: " + propertyModel.getAddress();
        String owner = "Владелец: " + detailUserModel.getLastName() + " " + detailUserModel.getFirstName();
        String price = "Цена аренды: " +  propertyModel.getRentalPrice() +"рублей в сутки.";
        String propertyType = "Тип собственности: " + propertyModel.getRentalType();
      //  model.addAttribute("city",propertyModel.getCity());
        model.addAttribute("owner",owner);
        model.addAttribute("address",address);
        model.addAttribute("type",propertyType);
        model.addAttribute("price",price);
        model.addAttribute("description",propertyModel.getRentalDescription());
        model.addAttribute("id",id);
        model.addAttribute("propertyImages",propertyImages);
        return "detailRentalPage";
    }
}
