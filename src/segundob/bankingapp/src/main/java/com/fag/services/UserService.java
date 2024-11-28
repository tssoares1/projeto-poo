package com.fag.services;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fag.domain.dto.LoginDTO;
import com.fag.domain.dto.UserAccountDTO;
import com.fag.domain.repositories.IUserInterface;
import com.fag.domain.repositories.IUserRepository;

public class UserService {
    private IUserInterface userInterface;

    private IUserRepository userRepository;

    public UserService(IUserInterface userInterface, IUserRepository userRepository) {
        this.userInterface = userInterface;
        this.userRepository = userRepository;
    }

    public UserAccountDTO handleLogin() {
        LoginDTO login = userInterface.getLoginData();

        UserAccountDTO user = userRepository.findUserBy(login.getDocument());

        if (user == null) {
            userInterface.showErrorMsg("Usuario nao encontrado ou credenciais invalidas!");
            return null;
        }

        if (!user.getPassword().equals(login.getPassword())) {
            userInterface.showErrorMsg("Usuario nao encontrado ou credenciais invalidas!");
            return null;
        }

        return user;
    }

    public UserAccountDTO handleRegisterAcc() {
        UserAccountDTO user = userInterface.getCreateUserData();
        UserAccountDTO account = userRepository.getLastData();

        user.setId(UUID.randomUUID().toString());

        user.setCreatedAt(LocalDateTime.now());

        if (account != null) {
            Integer accountNumber = account.getAccountNumber() + 1;
            user.setAccountNumber(accountNumber);
        } else {
            user.setAccountNumber(1);
        }

        userRepository.createUser(user);

        return user;
    }
}
