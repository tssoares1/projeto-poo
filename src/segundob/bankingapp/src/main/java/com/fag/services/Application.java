package com.fag.services;

import com.fag.domain.dto.UserAccountDTO;
import com.fag.domain.repositories.IBassRepository;
import com.fag.domain.repositories.IUserInterface;
import com.fag.domain.repositories.IUserRepository;

public class Application {
    public void execute(IUserInterface gui, IUserRepository userRepo, IBassRepository celcoin) {
        UserService userService = new UserService(gui, userRepo);
        BankingService bankingService = new BankingService(gui, celcoin);

        while (true) {
            Integer option = gui.showInitialScreenMenu();

            switch (option) {
                case 1:
                    UserAccountDTO user = userService.handleLogin();

                    if (user != null) {
                        bankingService.execute(user);
                    }
                    break;
                case 2:
                    UserAccountDTO createdAcc = userService.handleRegisterAcc();

                    if (createdAcc != null) {
                        bankingService.execute(createdAcc);
                    }
                    break;
                case 3:
                    gui.showExitMessage();
                    return;
            }
        }

    }
}
