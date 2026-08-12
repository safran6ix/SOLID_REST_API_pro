package com.example.rest.repository;

import  com.example.rest.model.User;
import java.util.List;
import java.util.Optional;

//Interface Segregation Principle (ISP) 
public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    User update(User user);
    boolean deleteById(Long id);
}
