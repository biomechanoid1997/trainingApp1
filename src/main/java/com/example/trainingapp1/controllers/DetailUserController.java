package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.PropertyModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.PropertyRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/detailUserPage")
public class DetailUserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @Autowired
    PropertyRepo propertyRepo;

    @GetMapping("/{id}")
    public String getDetailUserPage(
            @PathVariable long id,
            Model model
    ){
       UserModel userModel = userRepo.findById(id);
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(id);
        List<PropertyModel> propertyModelList = propertyRepo.findAllByOwnerID(id);
        for (int i = 0; i < propertyModelList.size(); i++) {
            if (propertyModelList.get(i).getAllowChildren().equals("true")){
                model.addAttribute("allowChildren","Можно проживать детям");
            }else{
                model.addAttribute("allowChildren","Не рекомендуется брать детей");
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////
            if (propertyModelList.get(i).getRentalStatus().equals("vacant")){
                propertyModelList.get(i).setRentalStatus("Свободно");
            }
            if (propertyModelList.get(i).getRentalStatus().equals("occupied")){
                propertyModelList.get(i).setRentalStatus("Занято");
            }
            if (propertyModelList.get(i).getRentalStatus().equals("maintenance")){
                propertyModelList.get(i).setRentalStatus("В ремонте");
            }
            if (propertyModelList.get(i).getRentalStatus().equals("closed")){
                propertyModelList.get(i).setRentalStatus("закрыто");
            }
            if (propertyModelList.get(i).getRentalType().equals("room")){
                propertyModelList.get(i).setRentalType("комната");
            }
            if (propertyModelList.get(i).getRentalType().equals("flat")){
                propertyModelList.get(i).setRentalType("квартира");
            }
            if (propertyModelList.get(i).getRentalType().equals("hostel")){
                propertyModelList.get(i).setRentalType("Хостел/общежитие");
            }
            if (propertyModelList.get(i).getRentalType().equals("hotel")){
                propertyModelList.get(i).setRentalType("Отель/гостиница");
            }
            if (propertyModelList.get(i).getRentalType().equals("house")){
                propertyModelList.get(i).setRentalType("Дом/коттедж");
            }
        }
        if (userModel.getUserType().equals("user")){
            userModel.setUserType("пользователь");
        }else if (userModel.getUserType().equals("user")){
            userModel.setUserType("модератор");
        }else if(userModel.getUserType().equals("banned")){
            userModel.setUserType("Заблокированный");
        }
        if (detailUserModel.getSex().equals("Male")){
            detailUserModel.setSex("Мужской");
        } else if (detailUserModel.getSex().equals("Female")){
            detailUserModel.setSex("Женский");
        }
        if (detailUserModel.getMaritalStatus().equals("single")){
            detailUserModel.setMaritalStatus("холост/незамужем");
        }else if (detailUserModel.getMaritalStatus().equals("married")){
            detailUserModel.setMaritalStatus("женат/замужем");
        }else if(detailUserModel.getMaritalStatus().equals("hasChildren")){
            detailUserModel.setMaritalStatus("есть дети");
        }

        model.addAttribute("user",userModel);
        model.addAttribute("detailUser",detailUserModel);
        model.addAttribute("propertyList",propertyModelList);
             return "userInfo";
    }


}
