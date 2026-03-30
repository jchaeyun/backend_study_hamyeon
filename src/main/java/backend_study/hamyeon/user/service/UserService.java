//트랜잭션을 관리하고 실제 비즈니스 흐름 제어
package backend_study.hamyeon.user.service;

import backend_study.hamyeon.user.domain.User;
import backend_study.hamyeon.user.dto.UserRequestDto;
import backend_study.hamyeon.user.dto.UserResponseDto;
import backend_study.hamyeon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //(클라이언트->서버)dto->entity
    //db에 entity 저장
    //entity->dto(서버->클라이언트) 리턴
    @Transactional //이 메서드 내의 작업이 하나라도 실패하면 모두 롤백하여 db 상태를 안전히 유지. 데이터 조작하는 서비스 메서드에는 반드시 붙여야함
    public UserResponseDto createUser(UserRequestDto request) {
        //1.DTO(클라이언트->서버)의 데이터를 꺼내어 User 엔티티 생성
        User user=new User(request.nickname());

        //2.db에 저장(저장 시점에 id값이 자동 생성되어 부여됨)
        User savedUser=userRepository.save(user);

        //3.저장된 엔티티를 dto(서버->클라이언트)로 변환하여 controller로 반환
        return UserResponseDto.from(savedUser);
    }
}
