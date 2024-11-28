package com.fag.infra.testedb;

import java.util.ArrayList;
import java.util.List;

import com.fag.domain.dto.UserAccountDTO;
import com.fag.domain.repositories.IUserRepository;

public class UserTestDBRepository implements IUserRepository {
    List<UserAccountDTO> userAccounts = new ArrayList<>();

    @Override
    public UserAccountDTO createUser(UserAccountDTO dto) {
        userAccounts.add(dto);

        return dto;
    }

    @Override
    public UserAccountDTO findUserBy(String document) {
        if (userAccounts != null) {
            return userAccounts.stream()
                    .filter(user -> user.getDocument().equals(document))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }

    @Override
    public UserAccountDTO getLastData() {
        if (userAccounts != null) {
            return userAccounts.get(userAccounts.size() - 1);
        }

        return null;
    }
}
