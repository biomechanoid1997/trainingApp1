package com.example.trainingapp1.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "RentalSiteUserDetail")
@Data
public class DetailUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "UserTableId")
    private long userTableId;

    @Column(name = "UserName")
    String userName;

    @Column(name ="E-mail")
    String Email;

    @Column(name = "FirstName")
    String firstName;

    @Column(name = "LastName")
    String lastName;

    @Column(name ="DateOfBirth")
    String dateOfBirth;

    @Column(name = "Sex")
    String sex;

    @Column(name = "MaritalStatus")
    String maritalStatus;


}
