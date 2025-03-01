package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.PropertyModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.trainingapp1.helpers.RentalSortHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/searchRentals")
public class RentalSearchController {
    @Autowired
    PropertyRepo propertyRepo;
    @Autowired
    DetailUserRepo detailUserRepo;

    @GetMapping
    public String getRentals(){
        return "rentalSearchPage";
    }
     @PostMapping
    public String getRentalList(@RequestParam String City,
                                @RequestParam String PropertyType,
                                @RequestParam Long humanCapacity,
                                @RequestParam String allowChildren,
                              //  @RequestParam Long rentalPriceMin,
                              //  @RequestParam Long rentalPriceMax,
                                Model model){
        ArrayList<PropertyModel> propertyList = new ArrayList<>(propertyRepo.findAll());
      //  ArrayList<PropertyModel> propertyListFinal = new ArrayList<PropertyModel>();
        //must fix
     //   if (!City.equals("")){
     //       for (int i = 0; i < propertyList.size(); i++) {
     //           if (!propertyList.get(i).getCity().toString().trim().toUpperCase().equals(City.toString().toUpperCase().trim())){
     //               propertyList.remove(i);
    //            }
     //       }
    //    }


 ////fixed and ready
         if (!City.equals("")){
           propertyList = new ArrayList<>(propertyRepo.findAllByCity(City));
         }

         if (!City.equals("")){
                  propertyList = new ArrayList<PropertyModel>(RentalSortHelper.sortCity(propertyList,City));
               }


         //must fix
   //     if (!PropertyType.equals("null")){
   //         for (int i = 0; i < propertyList.size(); i++) {
     //           if (!propertyList.get(i).getRentalType().equals(PropertyType)){
      //             propertyList.remove(i);
     //           }
      //      }
    //   }

               ////////
         //fixed and ready
               if (!PropertyType.equals("null")){

                for (int i = 0; i < propertyList.size(); i++) {
                    if (!propertyList.get(0).getRentalType().equals(PropertyType)){
                        propertyList.remove(0);
                      i = 0;
                    }
                   if (!propertyList.get(i).getRentalType().equals(PropertyType)){
                      propertyList.remove(i);
                      i =0;
                   }
               }
            }
         if (propertyList.isEmpty()){
             model.addAttribute("rentalError","Собственности не найдено");
             return "rentalPage";
         }
///////////////////////////////////



         //fixed and ready
        if (humanCapacity != 0){
            for (int i = 0; i < propertyList.size(); i++) {
                if (propertyList.get(0).getCapacity() < humanCapacity){
                    propertyList.remove(0);
                    i=0;
                }
                if (propertyList.get(i).getCapacity() < humanCapacity){
                    propertyList.remove(i);
                    i=0;
                }
            }
        }
         if (propertyList.isEmpty()){
             model.addAttribute("rentalError","Собственности не найдено");
             return "rentalPage";
         }

////////////////////////// fixed and ready
         if (allowChildren.equals("false,true")) {
             allowChildren="true";
         }else {allowChildren="false";}
         for (int i = 0; i < propertyList.size(); i++) {
             if (!propertyList.get(0).getAllowChildren().equals(allowChildren)){
                 propertyList.remove(0);
                 i = 0;
            }
            if (!propertyList.get(i).getAllowChildren().equals(allowChildren)){
                 propertyList.remove(i);
                 i=0;
             }
         }
         if (propertyList.isEmpty()){
             model.addAttribute("rentalError","Собственности не найдено");
             return "rentalPage";
         }
/////////////////////////////////////////////////////
  //  if (rentalPriceMin != 0 || rentalPriceMax !=0){
    //     for (int i = 0; i < propertyList.size(); i++) {
    //         if (propertyList.get(0).getRentalPrice()<rentalPriceMin && propertyList.get(0).getRentalPrice()>rentalPriceMax){
   //              propertyList.remove(0);
    //             i=0;
   //          }
  //           if ( propertyList.get(i).getRentalPrice()<rentalPriceMin && propertyList.get(i).getRentalPrice()>rentalPriceMax){
  //               propertyList.remove(i);
   //              i=0;
  //           }
 //        }
  //   }

         for (int i = 0; i < propertyList.size(); i++) {
            if (!propertyList.get(0).getRentalStatus().equals("vacant")){
                 propertyList.remove(0);
                 i=0;
             }
             if (!propertyList.get(i).getRentalStatus().equals("vacant")){
                 propertyList.remove(i);
                 i=0;
             }
         }

         if (propertyList.isEmpty()){
             model.addAttribute("rentalError","Собственности не найдено");
             return "rentalPage";
         }

         for (int i = 0; i < propertyList.size(); i++) {
             PropertyModel propertyModel = propertyList.get(i);
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


         if (propertyList.isEmpty()){
             model.addAttribute("rentalError","Собственности не найдено");
             return "rentalPage";
         }

        model.addAttribute("propertyList",propertyList);
        return "rentalPage";
     }
}
