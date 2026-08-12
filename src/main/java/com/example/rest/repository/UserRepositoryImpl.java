package com.example.rest.repository;

import com.example.rest.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// Single Responsibility Principle (SRP) - Only handles data storage
public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserRepositoryImpl() {
        // Sample data
        users.add(new User(idCounter.getAndIncrement(), "John Doe", "john@example.com", "123-456-7890"));
        users.add(new User(idCounter.getAndIncrement(), "Jane Smith", "jane@example.com", "098-765-4321"));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public User save(User user) {
        user.setId(idCounter.getAndIncrement());
        users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        Optional<User> existing = findById(user.getId());
        if (existing.isPresent()) {
            int index = users.indexOf(existing.get());
            users.set(index, user);
            return user;
        }
        throw new RuntimeException("User not found with id: " + user.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}