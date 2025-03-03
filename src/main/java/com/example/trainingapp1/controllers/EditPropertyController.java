package com.example.trainingapp1.controllers;

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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/editProperty")
public class EditPropertyController {
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
        List<PropertyImageModel> propertyImages = propertyImageRepo.findPropertyImageModelsByPropertyTableId(propertyModel.getId());
        for (int i = 0; i < propertyImages.size(); i++) {
            propertyImages.get(i).setImageUrl(firebaseService.getUrl(propertyImages.get(i).getImageUrl()));
        }
        String ownerId = String.valueOf(propertyModel.getOwnerID());
        model.addAttribute("property",propertyModel);
        model.addAttribute("propertyImages",propertyImages);
      return "propertyEditPage";
    }
    @PostMapping("/{id}")
    public RedirectView updateUserProperty(
            @PathVariable long id,
            Model model,
            @RequestParam MultipartFile file,
            @RequestParam String City,
            @RequestParam String Description,
            @RequestParam String Address,
            @RequestParam Long humanCapacity,
            @RequestParam String PropertyType,
            @RequestParam String allowChildren,
            @RequestParam Long rentalPrice,
            @RequestParam String rentalStatus
    ) throws Exception{
        String childAllow = allowChildren;
        PropertyModel propertyModel = propertyRepo.findById(id);
        PropertyImageModel propertyImageModel = new PropertyImageModel();
        propertyModel.setCity(City);
        propertyModel.setRentalDescription(Description);
        propertyModel.setAddress(Address);
        propertyModel.setCapacity(humanCapacity);
        propertyModel.setRentalType(PropertyType);
        if (childAllow.equals("false,true")) {
            propertyModel.setAllowChildren("true");
        }else {propertyModel.setAllowChildren("false");}
        propertyModel.setRentalPrice(rentalPrice);
        propertyModel.setRentalStatus(rentalStatus);
        propertyImageModel.setPropertyTableId(propertyModel.getId());
        propertyImageModel.setImageUrl(this.firebaseService.save(file));
        propertyRepo.save(propertyModel);
        propertyImageRepo.save(propertyImageModel);
        return new RedirectView("/property/viewProperty/"+ propertyModel.getOwnerID());
    }
}
