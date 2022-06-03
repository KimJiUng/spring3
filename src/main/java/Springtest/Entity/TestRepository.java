package Springtest.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity,Integer> {
                                    // JPARepository 클래스로부터 상속<엔티티명, pk자료형>
}
