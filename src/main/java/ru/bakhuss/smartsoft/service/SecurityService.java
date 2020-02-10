package ru.bakhuss.smartsoft.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
