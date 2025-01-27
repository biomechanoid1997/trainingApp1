package com.example.trainingapp1.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "RentalSiteUserProperty")
@Data
public class PropertyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "OwnerId")
    Long ownerID;

    @Column(name = "City")
    String city;

    @Column(name = "Address")
    String address;

    @Column(name = "RentalType")
    String rentalType;

    @Column(name = "RentalDescription")
    String rentalDescription;

    @Column(name = "HumanCapacity")
    Long capacity;

    @Column(name = "AllowChildren")
    String allowChildren;

    @Column(name = "RentalPrice")
    Long rentalPrice;

    @Column(name = "RentalStatus")
    String rentalStatus;
}
