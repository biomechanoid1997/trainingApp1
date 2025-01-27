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
            @PathVariable long id,
            Model model
    ){
        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(id);
        model.addAttribute("Name",detailUserModel.getFirstName());
        model.addAttribute("id",id);
        return "testPage";
    }
    @PostMapping("/{id}")
    public RedirectView setProperty(
            @PathVariable long id,
            Model model
    //        @RequestParam MultipartFile file,
                              //      @RequestParam String City,
                               //     @RequestParam String Address,
                               //     @RequestParam Long humanCapacity,
                               //     @RequestParam String PropertyType,
                                //    @RequestParam String allowChildren,
                                //    @RequestParam Long rentalPrice,
                                  //  @RequestParam String rentalStatus
    ) throws Exception {

        return new RedirectView("/user/" +model.getAttribute("id")  ) ;
    }

}
