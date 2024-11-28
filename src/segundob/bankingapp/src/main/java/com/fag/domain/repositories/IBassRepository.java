package com.fag.domain.repositories;

import com.fag.domain.dto.BankslipDTO;

public interface IBassRepository {

    String consultInvoice(String barcode);

    String payInvoice(BankslipDTO payload);

    String generatedQRCode(Double payload);
}
