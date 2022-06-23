package pl.bfilas.sales;

public class PaymentDetails {
    String url;
    String reservationId;

    public PaymentDetails(String reservationId, String url) {
        this.url = url;
        this.reservationId = reservationId;
    }

    public static PaymentDetails of(String reservationNumber, String paymentUrl) {
        return new PaymentDetails(reservationNumber, paymentUrl);
    }

    public String getUrl() {
        return url;
    }

    public String getReservationId() {
        return reservationId;
    }
}
