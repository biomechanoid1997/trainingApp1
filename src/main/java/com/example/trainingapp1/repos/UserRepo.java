package com.example.trainingapp1.repos;

import com.example.trainingapp1.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {
   List<UserModel> findAllByUserType(String user_type);
    UserModel findById(long id);

}
