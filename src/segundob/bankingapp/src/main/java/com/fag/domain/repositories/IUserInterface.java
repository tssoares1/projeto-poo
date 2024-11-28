package com.fag.domain.repositories;

import com.fag.domain.dto.BankslipDTO;
import com.fag.domain.dto.LoginDTO;
import com.fag.domain.dto.UserAccountDTO;

public interface IUserInterface {

    Integer showInitialScreenMenu();

    Integer showHomeMenu(String userName);

    LoginDTO getLoginData();

    UserAccountDTO getCreateUserData();

    void showErrorMsg(String msg);

    void showExitMessage();

    String getBarcode();

    BankslipDTO getPaymentsBankslipInfo();

    void showBankslipData(String data);

    Double getPixData();

    void showPixData(String data);

    void showLogoutMessage();

}