package com.example.trainingapp1.repos;

import com.example.trainingapp1.models.PropertyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepo extends JpaRepository<PropertyModel,Long> {
    PropertyModel findById(long id);
    List<PropertyModel> findAllByCity(String city);
    List<PropertyModel> findAllByOwnerID(Long ownerID);

}
