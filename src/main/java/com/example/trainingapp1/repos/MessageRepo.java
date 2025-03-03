package com.example.trainingapp1.repos;

import com.example.trainingapp1.models.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<MessageModel, Long> {

    MessageModel findById(long id);
    List<MessageModel> findAllByReceiverID(long receiverID);
}
