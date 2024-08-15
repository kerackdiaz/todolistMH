package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.LoginRecord;
import com.mindhub.todolist.dtos.RegisterRecord;

import java.util.Map;

public interface UserService {
    String Register(RegisterRecord register);

    Map<String, Object> login(LoginRecord login);
}
