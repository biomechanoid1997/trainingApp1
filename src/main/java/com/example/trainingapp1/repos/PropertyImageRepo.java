package com.example.trainingapp1.repos;

import com.example.trainingapp1.models.PropertyImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImageRepo extends JpaRepository<PropertyImageModel,Long> {
}
