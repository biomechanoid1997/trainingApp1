package com.example.trainingapp1.services;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@Service
public class FirebaseService {
    private Storage store = StorageOptions.getDefaultInstance().getService();

    public String save(MultipartFile multipartFile) throws Exception{
        String imageName = String.valueOf(System.currentTimeMillis());
        BlobId blobId = BlobId.of("itoveroneshop-dcad8.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(multipartFile.getContentType())
                .build();
        store = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("itoveroneshop-dcad8-firebase-adminsdk-f95f2-27c7648989.json")))
                .build()
                .getService();
        store.create(blobInfo, multipartFile.getInputStream());
        return imageName;
    }
    public String getUrl(String filename){
        return "https://firebasestorage.googleapis.com/v0/b/itoveroneshop-dcad8.appspot.com/o/"+filename+"?alt=media&token=35a5c81e-60a6-4239-ad1a-ef88e50c879b";
    }
}
