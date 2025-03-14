package com.example.trainingapp1.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "RentalSiteUsers")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "login")
  private   String login;

    @Column(name="Email")
    private String Email;

    @Column(name = "password")
  private   String password;

    @Column(name = "userType")
   private String userType;

    @Column(name = "userStatus")
    private String userStatus;

    @Column(name = "userIp")
    private String userIp;
}
