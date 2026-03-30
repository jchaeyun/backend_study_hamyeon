//사용자 엔티티
package backend_study.hamyeon.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users") //'user'는 대부분의 db에서 예약어이므로 복수형 사용해 명시적으로 지정(기본적으로  클래스 이름을 테이블 이름으로 자동 지정)
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED) //@NoArgs~가 생성하는 기본생성자의 접근 제어자를 protected로 제한. JPA 접근 허용하되, 외부에서 무분별한 객체 생성 막기 위함
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//db 테이블의 기본키 생성 전략을 정의. IDENTITY:기본키 생성을 db에 완전히 위임.
    private Long id;

    @Column(nullable = false, length=12) //null 안되고 12자 제한
    private String nickname;

    public User(String nickname){
        this.nickname = nickname;
    }
}
