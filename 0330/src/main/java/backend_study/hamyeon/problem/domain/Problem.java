//고민 엔티티

package backend_study.hamyeon.problem.domain;

import backend_study.hamyeon.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY) //지연 로딩을 통한 쿼리 최적화. 연결된 user 정보는 일단 가짜로 가져오고 필요할때만 가져오는것
    @JoinColumn(name="user_id",nullable = false)//problem 테이블에 생성된 외래 키(FK) 칼럼 명:user_id
    private User user;

    @Column(nullable = false,length=25) //고민 제목 25자 제한
    private String title;

    @Enumerated(EnumType.STRING) //Enum 타입 데이터를 db에 어떤 형테의 값으로 저장할지 결정.
    @Column(nullable = false)
    private ProblemStatus status;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name="selected_choice_id")
    private Long selectedChoiceId;

    //cascade=CascadeType.ALL 설정을 통해 Problem 저장 시 Choice 2개도 동시 저장됨
    //mappedBy="problem"은 연관관계의 주인이 되는 자바 엔티티 객체의 변수명
    //(실제 db의 외래키 관리하는 곳은 대상 클래스(Choice)에 있는 변수 problem, 매핑 정보는 거길 참고해라
    //orphanRemoval=true:부모와 연결이 끊어진 자식을 db에서 자동 삭제
    @OneToMany(mappedBy="problem",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Choice> choices=new ArrayList<>();

    public Problem(User user, String title) {
        this.user = user;
        this.title = title;
        this.status=ProblemStatus.PROCEEDING;
        this.createdAt=LocalDateTime.now();
    }

    //선택지 추가 시 양방향 관계 설정->두 객체가 서로를 정확히 인식하도록 두 작업을 하나의 메소드로 묶어(atomic) 데이터 정합성을 맞춤
    public void addChoice(Choice choice) {
        choices.add(choice); //Problem 객체 리스트에 Choice 넣음
        choice.setProblem(this); //Choice 객체에도 자신이 속한 Problem을 세팅
    }

    public void markAsSolved() {
        this.status=ProblemStatus.SOLVED;
    }

    //상태 변경 및 결과 저장 메서드 추가
    public void resolve(Long selectedChoiceId) {
        this.status=ProblemStatus.SOLVED;
        this.selectedChoiceId=selectedChoiceId;
    }
}
