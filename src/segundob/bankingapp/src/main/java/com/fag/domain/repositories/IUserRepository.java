package com.fag.domain.repositories;

import com.fag.domain.dto.UserAccountDTO;

public interface IUserRepository {
    UserAccountDTO createUser(UserAccountDTO dto);

    UserAccountDTO findUserBy(String document);

    UserAccountDTO getLastData();
}
