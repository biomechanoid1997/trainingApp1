package com.example.trainingapp1.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messageModel")
@Data
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "senderID")
    long senderID;

    @Column(name = "receiverID")
    long receiverID;

    @Column(name = "messageText")
    String messageText;

    @Column(name = "contactInfo")
    String contactInfo;

    @Column(name = "messageType")
    String messageType;

    @Column(name = "requestedPropertyId")
   long requestPropertyId;

    @Column(name="messageDate")
    String messageDate;

    @Column(name = "messageStatus")
    String messageStatus;
}
