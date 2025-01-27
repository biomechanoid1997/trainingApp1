package com.example.trainingapp1.repos;

import com.example.trainingapp1.models.PropertyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepo extends JpaRepository<PropertyModel,Long> {
}
