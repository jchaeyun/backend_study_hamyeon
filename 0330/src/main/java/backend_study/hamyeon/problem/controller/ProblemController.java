//Controller API 엔드포인트
//프론트엔드가 요청을 보낼때 HTTP 헤더(X-User-Id)에 이전에 발급받은 User ID를 담아서 보낸다고 가정하고 설계
package backend_study.hamyeon.problem.controller;

import backend_study.hamyeon.problem.dto.HomeResponseDto;
import backend_study.hamyeon.problem.dto.ProblemRequestDto;
import backend_study.hamyeon.problem.dto.ProblemResolveRequestDto;
import backend_study.hamyeon.problem.service.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    //고민 등록 API
    //ResponseEntity:스프링 프레임워크가 웹 통신을 위해 제공하는 클래스. 웹 통신 규격 HTTP 응답 상자
    @PostMapping
    public ResponseEntity<Long> createProblem(
            @RequestHeader("X-User-Id") Long userId,//누가 보냈는지 헤더에 담음
            @Valid @RequestBody ProblemRequestDto request){

        Long problemId = problemService.createProblem(userId, request);
        return ResponseEntity.ok(problemId); //HTTP 상태코드 200 붙여서 HTTP 응답 상자 만들고 problemId 넣어 프엔으로 전송
    }

    //홈 화면 상태 조회 API
    @GetMapping("/home")
    public ResponseEntity<HomeResponseDto> getHomeStatus(
            @RequestHeader("X-User-Id") Long userId
    ){
        HomeResponseDto response =problemService.getHomeStatus(userId);
        return ResponseEntity.ok(response);
    }

    //72시간 내에 선택지를 골라 고민 해결 API
    @PostMapping("/{problemId}/resolve")
    public ResponseEntity<Void> resolveProblem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long problemId,
            @Valid @RequestBody ProblemResolveRequestDto request
    ){

        problemService.resolveProblem(userId, problemId, request);
        return ResponseEntity.ok().build();//build():body 데이터가 없는 상태로 HTTP 응답 객체 생성 마무리하고 반환
    }

}
