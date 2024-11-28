package com.fag.infra.console;

import java.util.Scanner;

import com.fag.domain.dto.BankslipDTO;
import com.fag.domain.dto.LoginDTO;
import com.fag.domain.dto.UserAccountDTO;
import com.fag.domain.repositories.IUserInterface;

public class ConsoleUI implements IUserInterface {

    private Scanner reader = new Scanner(System.in);

    @Override
    public Integer showInitialScreenMenu() {
        System.out.println("Bem vindo ao BankingAPP!");
        System.out.println("[1] Realizar login");
        System.out.println("[2] Criar conta");
        System.out.println("[3] Sair");

        Integer option = reader.nextInt();

        reader.nextLine();

        return option;
    }

    @Override
    public Integer showHomeMenu(String userName) {
        System.out.println("Olá " + userName + "! O que deseja fazer hoje?");
        System.out.println("[1] Consultar boleto");
        System.out.println("[2] Pagamento boleto");
        System.out.println("[3] Gerar QR Code PIX");
        System.out.println("[4] Logout");

        Integer option = reader.nextInt();

        return option;
    }

    @Override
    public com.fag.domain.dto.LoginDTO getLoginData() {
        LoginDTO data = new LoginDTO();

        System.out.println("Informe seu documento:");
        String document = reader.nextLine();

        System.out.println("Informe sua senha:");
        String password = reader.nextLine();

        data.setDocument(document);
        data.setPassword(password);

        return data;
    }

    @Override
    public UserAccountDTO getCreateUserData() {
        UserAccountDTO data = new UserAccountDTO();

        System.out.println("Informe seu documento:");
        String document = reader.nextLine();

        System.out.println("Informe seu email:");
        String email = reader.nextLine();

        System.out.println("Informe seu nome:");
        String name = reader.nextLine();

        System.out.println("Informe sua senha:");
        String password = reader.nextLine();

        data.setDocument(document);
        data.setEmail(email);
        data.setName(name);
        data.setPassword(password);

        return data;
    }

    @Override
    public void showErrorMsg(String msg) {
        System.out.println("ERRO: " + msg);
    }

    @Override
    public void showExitMessage() {
        System.out.println("Obrigado por utilizar a aplicacao!");
    }

    @Override
    public String getBarcode() {
        System.out.println("Insira o código de barras:");
        String barcode = reader.nextLine();

        return barcode;
    }

    @Override
    public BankslipDTO getPaymentsBankslipInfo() {
        BankslipDTO bankslipDTO = new BankslipDTO();

        System.out.println("Insira o código de barras:");
        String barcode = reader.nextLine();

        System.out.println("Insira o identificador de pagamento:");
        String id = reader.nextLine();

        System.out.println("Insira o valor de pagamento:");
        String amount = reader.nextLine();

        bankslipDTO.setValue(Double.parseDouble(amount));
        bankslipDTO.setBarcode(barcode);
        bankslipDTO.setTransactionId(id);

        return bankslipDTO;
    }

    @Override
    public void showBankslipData(String data) {
        System.out.println("Dados do boleto: " + data);
    }

    @Override
    public Double getPixData() {
        System.out.println("Insira valor do PIX:");
        Double amount = reader.nextDouble();

        return amount;
    }

    @Override
    public void showPixData(String data) {
        System.out.println("Dados do PIX: " + data);
    }

    @Override
    public void showLogoutMessage() {
        System.out.println("Saindo da sua conta!");
    }
}
