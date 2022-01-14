package com.egg.patitas.red.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class PhotoService {

    @Value("${my.property}")
    private String directory;

    @Autowired
    private StorageService storageService;

    public String copy(MultipartFile photo) throws Exception{
        if(photo.isEmpty()){
            throw new IllegalStateException("Debe ingresar una foto de su mascota");
        }

        String contentType = photo.getContentType();
        if(!isSupportedContentTypee(contentType)){
            throw new IllegalArgumentException("Debe ingresar un archivo de tipo IMG/PNG/JPEG");
        }

        try{
            return storageService.uploadFile(photo);
        }catch(Exception e){
            throw new Exception("Error al guardar la foto");
        }

    }

    private boolean isSupportedContentTypee(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
