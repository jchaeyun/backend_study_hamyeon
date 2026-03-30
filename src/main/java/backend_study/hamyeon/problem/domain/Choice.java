//선택지 엔티티
package backend_study.hamyeon.problem.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="problem_id",nullable = false)
    private Problem problem;

    @Column(nullable = false,length = 25) //내용 25자 제한
    private String content;

    @Column(nullable = false)
    private boolean isPicked=false;

    public Choice(String content) {
        this.content = content;
    }

    protected void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void pickThis(){
        this.isPicked=true;
    }
}
