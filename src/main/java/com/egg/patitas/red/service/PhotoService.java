package com.egg.patitas.red.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PhotoService {

    @Value("${my.property}")
    private String directory;

    @Autowired
    private StorageService storageService;

    public String copy(MultipartFile photo) throws Exception{

        try{
            return storageService.uploadFile(photo);
        }catch(Exception e){
            throw new Exception("Error al guardar la foto");
        }

    }


    /*public String copy(MultipartFile photo) throws Exception{

        try{
            String namePhoto = photo.getOriginalFilename();

            Path pathPhoto = Paths.get(directory, namePhoto).toAbsolutePath();

            Files.copy(photo.getInputStream(), pathPhoto, StandardCopyOption.REPLACE_EXISTING);

            return namePhoto;

        }catch(IOException e){
            throw new Exception("Error al guardar la foto");
        }

    }*/
}
