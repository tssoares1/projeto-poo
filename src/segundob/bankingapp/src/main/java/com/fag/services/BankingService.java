package com.fag.services;

import com.fag.domain.dto.BankslipDTO;
import com.fag.domain.dto.UserAccountDTO;
import com.fag.domain.repositories.IBassRepository;
import com.fag.domain.repositories.IUserInterface;

public class BankingService {
    private IUserInterface userInterface;
    private IBassRepository celcoin;

    public BankingService(IUserInterface userInterface, IBassRepository celcoin) {
        this.userInterface = userInterface;
        this.celcoin = celcoin;
    }

    public void execute(UserAccountDTO dto) {
        while (true) {
            Integer option = userInterface.showHomeMenu(dto.getName());

            switch (option) {
                case 1:
                    String barcode = userInterface.getBarcode();
                    userInterface.showBankslipData(celcoin.consultInvoice(barcode));
                    break;
                case 2:
                    BankslipDTO data = userInterface.getPaymentsBankslipInfo();
                    userInterface.showBankslipData(celcoin.payInvoice(data));
                    break;

                case 3:
                    Double amount = userInterface.getPixData();
                    userInterface.showPixData(celcoin.generatedQRCode(amount));
                    break;
                case 4:
                    userInterface.showLogoutMessage();
                    return;
                default:
                    break;
            }
        }

    }

}
