package com.example.trainingapp1.helpers;

import com.example.trainingapp1.models.PropertyModel;

import java.util.ArrayList;

public class RentalSortHelper {
    public static ArrayList<PropertyModel> sortCity(ArrayList<PropertyModel> propertyModels, String city){
        for (int i = 0; i < propertyModels.size(); i++) {
            if (!propertyModels.get(i).getCity().toString().trim().toUpperCase().equals(city.toString().toUpperCase().trim())){
                                 propertyModels.remove(i);
                              }
        }
        return propertyModels;
    }
}
