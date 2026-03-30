//JPA를 사용해 db에 접근하는 인터페이스. 이 선언만으로 기본적 CRUD 쿼리 자동 생성
package backend_study.hamyeon.user.repository;

import backend_study.hamyeon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
