package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.LoginRecord;
import com.mindhub.todolist.dtos.RegisterRecord;
import com.mindhub.todolist.models.User;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String Register(RegisterRecord register){
        try {
            if(register.username().isBlank()){
                return "el campo username no puede estár vacío";
            }
            if (register.password().isBlank()){
                return "el campo password no puede estár vacío";
            }
            if (register.email().isBlank()){
                return "el campo email no puede estár vacío";
            }
            if (userRepository.existsByUsername(register.username())){
                return "El nombre de usuario ya está en uso";
            }
            if (userRepository.existsByEmail(register.email())){
                return "El email ya esta registrado";
            }

            User user = new User(register.username(), register.password(), register.email());

            userRepository.save(user);

            return "El usuario se ha registrado con exito";
        }catch (Exception e){
            return "no se pudo crear el usuario";
        }
    }

    @Override
    public Map<String, Object> login(LoginRecord login){
        Map<String, Object> response = new HashMap<>();
        try{
            if(login.Username().isBlank()){
                response.put("Error: ","El usuario no puede estar en blanco");
                return response;
            }
            if (login.Password().isBlank()){
                response.put("Error: ","La contraseña no puede estar en blanco");
                return response;
            }
            if(!userRepository.existsByUsername(login.Username())){
                response.put("Error: ", "El usuario no existe");
                return response;
            }

            User user = userRepository.findAllByEmail(login.Password());
            if(!user.getPassword().matches(login.Password())){
                response.put("Error: ","La contraseña es incorrecta");
                return response;
            }
            response.put("hola", user);
            return response;
        }catch (Exception e){
            response.put("error", true);
            response.put("message: ", e.getMessage());
            return  response;
        }
    }

}
