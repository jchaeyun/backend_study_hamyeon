package backend_study.hamyeon.problem.repository;

//사용자 ID와 상태값을 기준으로 가장 최근의 고민 데이터를 조회하는 메서드

import backend_study.hamyeon.problem.domain.Problem;
import backend_study.hamyeon.problem.domain.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    //특정 유저의 특정 상태(PROCEEDING 등)인 가장 최근 고민(first order by desc) 조회
    Optional<Problem> findFirstByUserIdAndStatusOrderByCreatedAtDesc(Long userId, ProblemStatus status);


}