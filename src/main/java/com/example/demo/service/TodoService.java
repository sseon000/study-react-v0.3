package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service 레이어는 HTTP나 데이터베이스 같은 외부 컴포넌트로부터 추상화돼 우리가 온전히 비즈니스 로직에만 집중할 수 있었다.

@Slf4j // 로그가 필요한 클래스에 로그 라이브러리 추가
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // TodoEntity 저장
        repository.save(entity);
        // TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    // validation code refactor
    private void validate(TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity can't be null");
            throw new RuntimeException("Entity can't be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    /* 2022.12.15 KSH 추가
     * 검증, 세이브, 새로운 목록 조회
     */
    public List<TodoEntity> create(final TodoEntity entity) {
        //validation - 넘어온 엔티티가 유요한지 검사
        validate(entity);

        //save() - 엔티티를 저장하고 로그
        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        //findByUserId() - 저장된 엔티티를 포함하는 새 목록 조회 후 리턴
        log.info("return : ",repository.findByUserId(entity.getUserId()));
        return repository.findByUserId(entity.getUserId());
    }

    /* 2022.12.25 KSH 추가
     * 목록 조회
     */
    public List<TodoEntity> retrieve(final String userID) {
        log.info("retrieve : ",repository.findByUserId(userID));
        return repository.findByUserId(userID);
    }
}
