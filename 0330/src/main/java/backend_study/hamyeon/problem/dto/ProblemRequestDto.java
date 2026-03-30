//클라이언트와 주고받을 데이터 규격. UI 기획안의 제약 사항을 여기서 검증
package backend_study.hamyeon.problem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//고민 등록 요청(클라->서버)
public record ProblemRequestDto (
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max=25,message = "제목은 최대 25자까지 가능합니다." )
        String title,

        @NotBlank(message = "첫번째 선택지를 입력해주세요.")
        @Size(max=25,message="선택지는 최대 25자까지 가능합니다.")
        String choice1,

        @NotBlank(message = "두번째 선택지를 입력해주세요.")
        @Size(max=25,message="선택지는 최대 25자까지 가능합니다.")
        String choice2
){
}
