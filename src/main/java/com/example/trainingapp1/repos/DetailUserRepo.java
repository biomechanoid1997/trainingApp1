package com.example.trainingapp1.repos;

import com.example.trainingapp1.models.DetailUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailUserRepo extends JpaRepository<DetailUserModel,Long>{
    DetailUserModel findDetailUserModelByUserTableId(Long userTableId);

    DetailUserModel findById(long ownerID);
}
