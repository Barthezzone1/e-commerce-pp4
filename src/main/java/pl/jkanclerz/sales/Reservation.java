package pl.jkanclerz.sales;

import java.math.BigDecimal;


@Entity
public class Reservation {
    public Reservation(String id, BigDecimal total, CustomerData customerData) {

    }

    public void registerPayment(PaymentGateway paymentGateway) {

    }

    public String getPaymentUrl() {
        return null;
    }
}
