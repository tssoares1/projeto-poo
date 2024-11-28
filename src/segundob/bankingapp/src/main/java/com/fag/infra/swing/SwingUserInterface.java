package com.fag.infra.swing;

import javax.swing.JOptionPane;

import com.fag.domain.dto.BankslipDTO;
import com.fag.domain.dto.LoginDTO;
import com.fag.domain.dto.UserAccountDTO;
import com.fag.domain.repositories.IUserInterface;

public class SwingUserInterface implements IUserInterface {
        @Override
        public Integer showInitialScreenMenu() {
                String menu = "----------BANKINGAPP--------\n"
                                .concat("[1] Login\n")
                                .concat("[2] Cadastro\n")
                                .concat("[3] Sair");

                String escolha = JOptionPane.showInputDialog(
                                null,
                                menu,
                                "Menu Inicial",
                                JOptionPane.INFORMATION_MESSAGE);

                return Integer.parseInt(escolha);
        }

        @Override
        public LoginDTO getLoginData() {
                LoginDTO user = new LoginDTO();

                String document = JOptionPane.showInputDialog(
                                null,
                                "Informe seu documento",
                                "Informe os dados",
                                1);

                String password = JOptionPane.showInputDialog(
                                null,
                                "Informe sua senha",
                                "Informe os dados",
                                1);

                user.setDocument(document);
                user.setPassword(password);

                return user;
        }

        @Override
        public UserAccountDTO getCreateUserData() {
                UserAccountDTO user = new UserAccountDTO();

                String document = JOptionPane.showInputDialog(
                                null,
                                "Informe seu documento",
                                "Informe os dados",
                                1);

                String name = JOptionPane.showInputDialog(
                                null,
                                "Informe seu nome",
                                "Informe os dados",
                                1);

                String email = JOptionPane.showInputDialog(
                                null,
                                "Informe seu email",
                                "Informe os dados",
                                1);

                String password = JOptionPane.showInputDialog(
                                null,
                                "Informe sua senha",
                                "Informe os dados",
                                1);

                user.setDocument(document);
                user.setEmail(email);
                user.setName(name);
                user.setPassword(password);

                return user;
        }

        @Override
        public Integer showHomeMenu(String userName) {
                String menu = "----------BEM VINDO " + userName + "!--------\n"
                                .concat("[1] Consulta boleto\n")
                                .concat("[2] Pagamento boleto\n")
                                .concat("[3] Gerar QR Code\n")
                                .concat("[4] Logout");

                String escolha = JOptionPane.showInputDialog(
                                null,
                                menu,
                                "Home Banco",
                                JOptionPane.INFORMATION_MESSAGE);

                return Integer.parseInt(escolha);
        }

        @Override
        public void showErrorMsg(String msg) {
                JOptionPane.showMessageDialog(
                                null,
                                "ERRO: " + msg,
                                "ERRO!",
                                JOptionPane.ERROR_MESSAGE);
        }

        @Override
        public void showExitMessage() {
                JOptionPane.showMessageDialog(
                                null,
                                "Obrigado por utilizar a aplicação!",
                                "Logout",
                                JOptionPane.CANCEL_OPTION);
        }

        @Override
        public String getBarcode() {
                return JOptionPane.showInputDialog(
                                null,
                                "Insira o código de barras",
                                "Código de barras",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        @Override
        public BankslipDTO getPaymentsBankslipInfo() {
                BankslipDTO bankslipDTO = new BankslipDTO();
                String barcode = JOptionPane.showInputDialog(
                                null,
                                "Insira o código de barras a ser pago",
                                "Código de barras",
                                JOptionPane.INFORMATION_MESSAGE);
                String transactionId = JOptionPane.showInputDialog(
                                null,
                                "Insira o identificador de pagamento",
                                "Identificador",
                                JOptionPane.INFORMATION_MESSAGE);
                String amount = JOptionPane.showInputDialog(
                                null,
                                "Insira o valor a ser pago",
                                "Valor",
                                JOptionPane.INFORMATION_MESSAGE);

                bankslipDTO.setValue(Double.parseDouble(amount));
                bankslipDTO.setBarcode(barcode);
                bankslipDTO.setTransactionId(transactionId);

                return bankslipDTO;
        }

        @Override
        public void showBankslipData(String data) {
                JOptionPane.showMessageDialog(
                                null,
                                data,
                                "Dados boleto",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        @Override
        public void showPixData(String data) {
                JOptionPane.showMessageDialog(
                                null,
                                data,
                                "Dados PIX",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        @Override
        public Double getPixData() {
                String amount = JOptionPane.showInputDialog(
                                null,
                                "Insira o valor do PIX",
                                "Valor transação",
                                JOptionPane.INFORMATION_MESSAGE);

                return Double.parseDouble(amount);
        }

        @Override
        public void showLogoutMessage() {
                JOptionPane.showMessageDialog(
                                null,
                                "Saindo da sua conta!",
                                "Logout",
                                JOptionPane.CANCEL_OPTION);
        }
}
