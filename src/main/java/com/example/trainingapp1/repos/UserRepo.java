package com.example.trainingapp1.repos;

import com.example.trainingapp1.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {
    UserModel findById(long id);

}
