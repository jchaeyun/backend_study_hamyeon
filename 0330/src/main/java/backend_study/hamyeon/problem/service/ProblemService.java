//트랜잭션 통제하고 72시간 계산 로직 처리
package backend_study.hamyeon.problem.service;

import backend_study.hamyeon.problem.domain.Choice;
import backend_study.hamyeon.problem.domain.Problem;
import backend_study.hamyeon.problem.domain.ProblemStatus;
import backend_study.hamyeon.problem.dto.HomeResponseDto;
import backend_study.hamyeon.problem.dto.ProblemRequestDto;
import backend_study.hamyeon.problem.dto.ProblemResolveRequestDto;
import backend_study.hamyeon.problem.repository.ProblemRepository;
import backend_study.hamyeon.user.domain.User;
import backend_study.hamyeon.user.repository.UserRepository;
import backend_study.hamyeon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;

    //problem과 choice 2개 등록
    @Transactional
    public Long createProblem(Long userId, ProblemRequestDto request) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        //1.이미 진행 중인 고민이 있는지 검증(1인 1고민 정책)
        Optional<Problem> activeProblem=problemRepository
                .findFirstByUserIdAndStatusOrderByCreatedAtDesc(userId, ProblemStatus.PROCEEDING);
        if(activeProblem.isPresent()){
            throw new IllegalArgumentException("이미 진행중인 고민이 있습니다.");
        }

        //2.고민 및 선택지 엔티티 생성
        Problem problem=new Problem(user,request.title());
        problem.addChoice(new Choice((request.choice1())));
        problem.addChoice(new Choice((request.choice2())));

        //3.영속화(Cascade 설정으로 인해 Choice 2개도 problem과 함께 INSERT됨)
        Problem savedProblem=problemRepository.save(problem);
        return savedProblem.getId(); //problem id을 controller로 반환
    }

    //72시간 계산(서버->클라 dto 전달)
    @Transactional
    public HomeResponseDto getHomeStatus(Long userId) {
        Optional<Problem> activeProblem=problemRepository
                .findFirstByUserIdAndStatusOrderByCreatedAtDesc(userId, ProblemStatus.PROCEEDING);

        //진행중인 고민이 없으면 빈 상태 반환
        if(activeProblem.isEmpty()){
            return HomeResponseDto.empty();
        }

        Problem problem=activeProblem.get();

        //72시간에서 경과 시간을 뺸 남은 시간 계산해서 controller 반환
        long elapsedHours= Duration.between(problem.getCreatedAt(), LocalDateTime.now()).toHours();

        //72시간 경과 검사 및 자동 랜덤 선택(지연 평가 로직)
        if(elapsedHours>=72){
            List<Choice> choices=problem.getChoices();

            //0부터 '선택지 개수-1' 사이의 난수 생성
            int randomIndex=new Random().nextInt(choices.size());
            Long randomChoiceId=choices.get(randomIndex).getId();

            //엔티티 상태 변경(JPA 더티 체킹으로 인해 메서드 종료 시 UPDATE 쿼리 자동 발생)
            problem.resolve(randomChoiceId);

            //고민 강제 해결. 프엔에는 진행 중인 고민 없는 상태로 반환
            return HomeResponseDto.empty();
        }

        //72시간 안 지난 경우 남은 시간 계산 후 정상 반환
        long remainingHours= 72-elapsedHours;
        return new HomeResponseDto(true,problem.getId(),problem.getTitle(),remainingHours);

    }

    //고민 해결 로직
    @Transactional
    public void resolveProblem(Long userId, Long problemId, ProblemResolveRequestDto request) {
        Problem problem=problemRepository.findById(problemId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 고민입니다."));

        //본인의 고민인지 검증
        if(!problem.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("본인의 고민만 해결할 수 있습니다.");
        }

        //이미 해결된 고민인지 검증
        if(problem.getStatus() == ProblemStatus.SOLVED){
            throw new IllegalStateException("이미 해결된 고민입니다.");
        }

        //선택지를 골라서 고민 해결! 상태 업데이트 및 더티 체킹으로 자동 UPDATE 쿼리 발생
        problem.resolve(request.choiceId());
    }




}
