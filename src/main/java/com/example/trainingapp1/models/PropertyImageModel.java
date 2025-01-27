package com.example.trainingapp1.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PropertyImageModel")
@Data
public class PropertyImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "PropertyTableId")
    long propertyTableId;

    @Column(name = "ImageUrl")
    String imageUrl;

}
