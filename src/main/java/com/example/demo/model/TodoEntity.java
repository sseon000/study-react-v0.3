package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // 생성자를 이용해 오브젝틀 생성하는 것과 비슷
@NoArgsConstructor // 매개변수가 없는 생성자를 구현
@AllArgsConstructor // 클래스의 모든 멤버 변수를 매개변수로 받는 생성자를 구현
@Data // getter, setter 메서드 구현
public class TodoEntity<T> {
    private String id;
    private String userid;
    private String title;
    private boolean done;
}
