package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder // 생성자를 이용해 오브젝틀 생성하는 것과 비슷
@NoArgsConstructor // 매개변수가 없는 생성자를 구현
@AllArgsConstructor // 클래스의 모든 멤버 변수를 매개변수로 받는 생성자를 구현
@Data // getter, setter 메서드 구현
@Entity
// 엔티티와 테이블 매핑 , @Table을 추가하지 않거나 추가해도 name을 명시하지 않는다면 @Entity의 이름을 테이블 이름으로 간주
// 또 @Entity에 이름을 지정하지 않는 경우 클래스의 이름을 테이블 이름으로 간주한다.
@Table(name = "Todo")
public class TodoEntity {

    @Id // 기본 키가 될 필드 Primary Key
    @GeneratedValue(generator="system-uuid") // ID자동 생성
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String userId;
    private String title;
    private boolean done;
}