package kr.kro.namohagae.global.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.namohagae.global.util.ImageConstants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping(value = "/api/v1")
public class GlobalRestController {

    @GetMapping(value = {"/image/board", "/image/dog","/image/embeded", "/image/product", "/image/profile", "/image/temp", "/image/chat"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> viewImage(String name, HttpServletRequest req){
        String path = req.getRequestURI();
        String folder = null;
        switch (path){
            case ImageConstants.IMAGE_BOARD_PATH -> folder = ImageConstants.IMAGE_BOARD_FOLDER;
            case ImageConstants.IMAGE_EMBEDED_PATH -> folder = ImageConstants.IMAGE_EMBEDED_FOLDER;
            case ImageConstants.IMAGE_PRODUCT_PATH -> folder = ImageConstants.IMAGE_PRODUCT_FOLDER;
            case ImageConstants.IMAGE_PROFILE_PATH -> folder = ImageConstants.IMAGE_PROFILE_FOLDER;
            case ImageConstants.IMAGE_TEMP_PATH -> folder = ImageConstants.IMAGE_TEMP_FOLDER;
            case ImageConstants.IMAGE_CHAT_PATH -> folder = ImageConstants.IMAGE_CHAT_FOLDER;
            case ImageConstants.IMAGE_DOG_PATH -> folder = ImageConstants.IMAGE_DOG_FOLDER;
            default -> throw new IllegalStateException("Unexpected value: " + path);
        }
        File file = new File(folder, name);
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());
            MediaType type = MediaType.parseMediaType(contentType);
            return ResponseEntity.ok().contentType(type).body(bytes);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }




}
