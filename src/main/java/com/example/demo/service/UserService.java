package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String email, final String password
                                       //,final PasswordEncoder encoder // 2023.01.10 KSH 추가
                                       ) {

        /* 2023.01.10 KSH 추가
         * // matches 메서드를 이용해 패스워드가 같은지 확인
         * if(originalUser != null && encoder.matches(password, originalUser.getPassword()) {
         * retrun originalUser;
         * }
         * return null;
         */
        // 삭제예정 2023.01.10 KSH 추가
        return userRepository.findByEmailAndPassword(email, password);
    }
}
