package com.example.rest.service;

import com.example.rest.model.User;
import com.example.rest.repository.UserRepository;

// Single Responsibility Principle (SRP) - Only handles business logic
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    // Dependency Inversion Principle (DIP) - Depends on abstraction
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User createUser(User user) {
        // Business validation
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);
        user.setId(id);
        return userRepository.update(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}