package ru.bakhuss.smartsoft.service;

import ru.bakhuss.smartsoft.model.User;

public interface UserService {
    void addUser(User user);

    User findByUsername(String username);
}
