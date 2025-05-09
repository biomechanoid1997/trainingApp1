package com.example.trainingapp1.controllers;

import com.example.trainingapp1.models.DetailUserModel;
import com.example.trainingapp1.models.UserModel;
import com.example.trainingapp1.repos.DetailUserRepo;
import com.example.trainingapp1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/authorise")
public class AuthorisationController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DetailUserRepo detailUserRepo;
    @GetMapping
    public String GetAuthorisationPage(){
        return"Authorisation";
    }
    @PostMapping
    public RedirectView Authorise(@RequestParam String login,
                                  @RequestParam String password,
                            Model model){
        ArrayList<UserModel> userList = new ArrayList<UserModel>(userRepo.findAll());
        Long userId;
        String userError = " ";
        if (login.equals("admin")&& password.equals("password")){
            return new RedirectView("/admin");
        }
   for (int i = 0; i < userList.size(); i++) {
               UserModel userModel = userList.get(i);
           if (userModel.getLogin().equals(login)&&userModel.getPassword().equals(password)){
               ////////////////////////////////////////////////////////////////////////////
               // Забаненный пользователь
               if (userModel.getUserType().equals("banned")){
                   userId = userModel.getId();
                   DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userId);
                   model.addAttribute("id",detailUserModel.getId());
                   return new RedirectView("/userBanned/" + model.getAttribute("id"));
               }

               ///////////////////////////////////////////////////////////////////////////////////////////
                userId = userModel.getId();
                DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userId);
                String name = "Здравствуйте " +  detailUserModel.getFirstName() + " " + detailUserModel.getLastName();
                model.addAttribute("id",detailUserModel.getId());
                model.addAttribute("Name",name);

             return new RedirectView("/userIndex/" +model.getAttribute("id")  ) ;
          //      return new RedirectView("/userIndex"  ) ;
               // return new RedirectView("/user/" +model.getAttribute("id")  ) ;

            }
            }

        //////////////////////////////////////////////////////////////////////////////////////////
        //old code
        //////////////////////////////////////////////////////////////////////////////////////////
     //   for (int i = 0; i < userList.size(); i++) {
     //       UserModel userModel = userList.get(i);
     //   if (userModel.getLogin().equals(login)&&userModel.getPassword().equals(password)){
    //        userId = userModel.getId();
    //        DetailUserModel detailUserModel = detailUserRepo.findDetailUserModelByUserTableId(userId);
    //        String name = "Здравствуйте " +  detailUserModel.getFirstName() + " " + detailUserModel.getLastName();
    //        model.addAttribute("id",detailUserModel.getId());
    //        model.addAttribute("Name",name);
            /////////////////////////////////////////////////
    //        return new RedirectView("/user/" +model.getAttribute("id")  ) ;
  //          /////////////////////////////////////////////////
    //    }
    //    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        model.addAttribute("error",userError);
        return new RedirectView( "/authorise");
    }
}
