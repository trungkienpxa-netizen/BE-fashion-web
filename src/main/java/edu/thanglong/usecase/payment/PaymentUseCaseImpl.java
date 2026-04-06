package edu.thanglong.usecase.payment;

import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.Order;
import edu.thanglong.domain.model.Payment;
import edu.thanglong.domain.repository.OrderRepository;
import edu.thanglong.domain.repository.PaymentRepository;
import edu.thanglong.presentation.dto.request.PaymentCallbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentUseCaseImpl implements PaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final OrderRepository   orderRepository;

    @Override
    public Payment getByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", orderId));
    }

    @Override
    public Payment handleCallback(PaymentCallbackRequest req) {
        Payment payment = paymentRepository.findByOrderId(req.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment", req.getOrderId()));

        payment.setStatus(req.isSuccess()
                ? Payment.PaymentStatus.SUCCESS
                : Payment.PaymentStatus.FAILED);
        payment.setTransactionId(req.getTransactionId());
        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        // Nếu thanh toán thành công → xác nhận đơn hàng
        if (req.isSuccess()) {
            orderRepository.findById(payment.getOrderId()).ifPresent(order -> {
                order.setStatus(Order.OrderStatus.CONFIRMED);
                orderRepository.save(order);
            });
        }

        return saved;
    }
}