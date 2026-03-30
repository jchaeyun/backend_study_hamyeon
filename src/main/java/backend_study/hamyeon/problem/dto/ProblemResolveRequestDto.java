//사용자가 72시간 이내에 직접 선택지를 골랐을 때 호출되는 API
package backend_study.hamyeon.problem.dto;

import jakarta.validation.constraints.NotNull;

public record ProblemResolveRequestDto(
        @NotNull(message = "선택지 id는 필수입니다.")
        Long choiceId
) {
}
