package kr.kro.namohagae.global.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.namohagae.global.dto.NotificationDto;
import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.global.util.constants.ImageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class GlobalRestController {

    private final NotificationService notificationService;
    private final TownService townService;

    // [이미지 로딩]--------------------------------------------------------------------
    @GetMapping(value = {"/image/board", "/image/dog","/image/embeded", "/image/product", "/image/profile", "/image/temp", "/image/chat"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> viewImage(String name, HttpServletRequest req){
        String path = req.getRequestURI();
        String folder = null;
        if(path.startsWith(ImageConstants.IMAGE_BOARD_PATH)){folder = ImageConstants.IMAGE_BOARD_DIRECTORY;}
        else if (path.startsWith(ImageConstants.IMAGE_DOG_PATH)) {folder = ImageConstants.IMAGE_DOG_DIRECTORY;}
        else if (path.startsWith(ImageConstants.IMAGE_EMBEDED_PATH)) {folder = ImageConstants.IMAGE_EMBEDED_DIRECTORY;}
        else if (path.startsWith(ImageConstants.IMAGE_PRODUCT_PATH)) {folder = ImageConstants.IMAGE_PRODUCT_DIRECTORY;}
        else if (path.startsWith(ImageConstants.IMAGE_PROFILE_PATH)) {folder = ImageConstants.IMAGE_PROFILE_DIRECTORY;}
        else if (path.startsWith(ImageConstants.IMAGE_TEMP_PATH)) {folder = ImageConstants.IMAGE_TEMP_DIRECTORY;}
        else if (path.startsWith(ImageConstants.IMAGE_CHAT_PATH)) {folder = ImageConstants.IMAGE_CHAT_DIRECTORY;}
        else {folder = ImageConstants.IMAGE_EMBEDED_DIRECTORY;}
        File file = new File(folder, name);
        if (!file.exists()) {
            file = new File(ImageConstants.IMAGE_EMBEDED_DIRECTORY, "error.png");
        }
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


    @GetMapping(value="/notification/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        return ResponseEntity.ok().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(notificationService.findAll(pageno,myUserDetails.getMemberNo()));
    }

    @PutMapping("/notification/read")
    public ResponseEntity<String> updateNotificationRead(Integer notificationNo) {
        // 알람의 읽음 여부를 업데이트하는 로직 작성
        notificationService.read(notificationNo);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping(value = "/notification/aside/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationDto.FindAll>> printNotificationList(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        if (myUserDetails != null) {
            return ResponseEntity.ok().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(notificationService.printNotificationList(myUserDetails.getMemberNo()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    // [Town]--------------------------------------------------------------------
    @GetMapping(value = "/town/dongList",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>dongList(){
        return  ResponseEntity.ok(townService.findDong());
    }

    @GetMapping("/town/find")
    public ResponseEntity<List<TownDto.Read>> viewTownDong(String townGu){
        return ResponseEntity.ok(townService.findTownDongByGu(townGu));
    }

}
