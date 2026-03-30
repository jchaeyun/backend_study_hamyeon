//data transfer object:클라이언트와 서버가 데이터를 주고받을 규격
//record 클래스를 사용하면 불변 객체를 위한 Getter와 생성자를 자동으로 만들어줘 코드가 매우 짧아짐
package backend_study.hamyeon.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//클라이언트가 서버로 보내는 데이터. UI 명세서의 제약조건을 백엔드에서도 검증
public record UserRequestDto(
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max=12,message = "닉네임은 최대 12자까지 가능합니다.")
        String nickname
) {
}
