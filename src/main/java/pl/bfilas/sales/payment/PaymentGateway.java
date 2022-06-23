package pl.bfilas.sales.payment;

public interface PaymentGateway {

    RegisterPaymentResponse handle(RegisterPaymentRequest registerPaymentRequest);
}
