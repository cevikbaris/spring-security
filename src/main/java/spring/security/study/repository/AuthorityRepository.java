package spring.security.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.security.study.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {

    Optional<Authority> findByAuthorityName(String role);

}
