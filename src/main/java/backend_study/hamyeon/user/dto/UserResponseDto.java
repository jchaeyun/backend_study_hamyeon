//서버가 클라이언트로 반환하는 데이터 규격
package backend_study.hamyeon.user.dto;

import backend_study.hamyeon.user.domain.User;

public record UserResponseDto(
        Long id,
        String nickname
) {
    //Entity를 DTO로 변환하는 정적 팩토리 메서드
    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getNickname());
    }
}
