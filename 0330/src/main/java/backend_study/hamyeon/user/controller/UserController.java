//클라이언트의 HTTP 요청을 받아 service로 넘기는 역할

package backend_study.hamyeon.user.controller;

import backend_study.hamyeon.user.dto.UserRequestDto;
import backend_study.hamyeon.user.dto.UserResponseDto;
import backend_study.hamyeon.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users") //공통 주소
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //ResponseEntity:백엔->프엔 택배상자(HTTP 응답 전체. 상태코드/헤더(추가정보. 예)JSON 형식/body(실제 프엔에 전달할 데이터세트))
    //<UserResponseDto>:응답 상자의 body의 데이터 타입
    //@RequestBody;클라이언트가 보낸 JSON 데이터를 UserRequestDto 자바 객체로 변환
    //Vaild:DTO에 설정한 @NotBlack,@Size 등의 제약조건 검사. 조건 위반 시 메서드 실행 안하고 400 Bad Request 에러를 즉시 반환
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto response =userService.createUser(request); //dto(클라->서버)를 entity로 변환해 db 저장하고 다시 dto(서버->클라) 변환
        return ResponseEntity.ok(response); //dto(서버->클라).  ok(response):HTTP 상태코드200(정상처리됨)
        //->빈 상자에 200 성공 딱지를 붙이고 방금 service에서 받아온 response(UserResponse 객체)를 담아서 프엔으로 발송
        //프엔에서 이 200 응답과 JSON 데이터를 받아 파싱해서 앱 내부에 id 저장하고 다음 화면으로 넘어감
    }
}
