package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// persistence 레이어는 데이터 베이스와 통신하며 필요한 쿼리를 보내고 해석해 엔티티 오브젝트로 변환해 주는 역할

@Repository
/* 인터페이스이므로 JpaRepository 확장해 사용
 * JpaRepository 첫번째 매개변수는 제네릭타입 T는 테이블에 매핑될 엔티티
 *               두번째 매개변수는 엔티티의 기본키 타입
 */
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // 실행 쿼리 = SELECT * FROM TodoRepository WHERE userId = '{userId}'
    // 메서드 이름은 쿼리, 매개변수는 쿼리 where문 조건
    List<TodoEntity> findByUserId(String userId);

    /*
     더 복잡한 쿼리는 @Query 어노테이션을 사용해 지정
     ?1은 메서드의 매개변수의 순서 위치
     @Query("SELECT * FROM Todo t where t.userId = ?1")
     List<TodoEntity> findByUserId(String userId);
     메서드 이름 작성방법과 예제는 다음 공식 사이트의 레퍼런스를 통해 확인
     https://docs.spring.io/spring-data/jpa/docs/current/reference/
     html/#jpa.query-method.query-creation
     */
}
