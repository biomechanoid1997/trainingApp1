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
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/addProperty")
public class AddPropertyController {
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
    public String getPropertyPage(
            @PathVariable Long id,
            Model model
    ){
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(id);
        model.addAttribute("Name",detailUserModel.getFirstName());
        model.addAttribute("id",id);
        return "propertyAdd";
    }
    @PostMapping("/{id}")
    public RedirectView setProperty(
            @PathVariable Long id,
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
    ) throws Exception {
        String childAllow = allowChildren;
        PropertyModel propertyModel = new PropertyModel();
        PropertyImageModel propertyImageModel = new PropertyImageModel();
        propertyModel.setOwnerID(id);
        propertyModel.setRentalDescription(Description);
        propertyModel.setCity(City);
        propertyModel.setAddress(Address);
        propertyModel.setCapacity(humanCapacity);
        propertyModel.setRentalType(PropertyType);
        if (childAllow.equals("false,true")) {
            propertyModel.setAllowChildren("true");
        }else {propertyModel.setAllowChildren("false");}
        propertyModel.setRentalPrice(rentalPrice);
        propertyModel.setRentalStatus(rentalStatus);
        propertyRepo.save(propertyModel);
        propertyImageModel.setPropertyTableId(propertyModel.getId());
        propertyImageModel.setImageUrl( this.firebaseService.save(file));
        propertyImageRepo.save(propertyImageModel);

     model.addAttribute("id",id);
        return new RedirectView("/user/" +model.getAttribute("id")  ) ;
    }

}
