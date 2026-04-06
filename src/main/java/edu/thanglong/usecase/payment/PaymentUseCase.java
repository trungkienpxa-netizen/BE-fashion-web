package edu.thanglong.usecase.payment;

import edu.thanglong.domain.model.Payment;
import edu.thanglong.presentation.dto.request.PaymentCallbackRequest;

public interface PaymentUseCase {
    Payment getByOrderId(String orderId);
    Payment handleCallback(PaymentCallbackRequest request);
}