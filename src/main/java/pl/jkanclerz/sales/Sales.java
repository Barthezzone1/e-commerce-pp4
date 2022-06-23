package pl.jkanclerz.sales;

import pl.jkanclerz.sales.cart.Cart;
import pl.jkanclerz.sales.cart.CartItem;
import pl.jkanclerz.sales.cart.CartStorage;
import pl.jkanclerz.sales.offer.Offer;
import pl.jkanclerz.sales.offer.OfferMaker;
import pl.jkanclerz.sales.payment.DummyPaymentGateway;
import pl.jkanclerz.sales.payment.PaymentData;
import pl.jkanclerz.sales.payment.PaymentGateway;
import pl.jkanclerz.sales.products.ProductDetails;
import pl.jkanclerz.sales.products.ProductDetailsProvider;
import pl.jkanclerz.sales.products.ProductNotAvailableException;
import pl.jkanclerz.sales.reservation.Reservation;
import pl.jkanclerz.sales.reservation.ReservationStorage;

import java.util.UUID;

public class Sales {
    CartStorage cartStorage;
    ProductDetailsProvider productDetailsProvider;
    PaymentGateway paymentGateway;
    ReservationStorage reservationStorage;

    public Sales(
            CartStorage cartStorage,
            ProductDetailsProvider productDetailsProvider,
            PaymentGateway paymentGateway,
            ReservationStorage reservationStorage) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetailsProvider;
        this.paymentGateway = paymentGateway;
        this.reservationStorage = reservationStorage;
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        return calculateOffer(cart);
    }

    private Offer calculateOffer(Cart cart) {
        OfferMaker offerMaker = new OfferMaker();
        return offerMaker.calculateOffer(cart);
    }

    public void addToCart(String customerId, String productId) {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails productDetails = productDetailsProvider.findById(productId)
                .orElseThrow(() -> new ProductNotAvailableException());

        cart.addItem(CartItem.of(
                productId,
                productDetails.getName(),
                productDetails.getPrice()));

        cartStorage.save(customerId, cart);
    }

    public PaymentData acceptOffer(String customerId, ClientData clientData) {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        Offer currentOffer = calculateOffer(cart);

        String id = UUID.randomUUID().toString();
        Reservation reservation = Reservation.of(
                id,
                currentOffer.getTotal(),
                clientData
        );

        PaymentData paymentData = reservation
                .registerPayment(paymentGateway);

        reservationStorage.save(reservation);

        return paymentData;
    }
}
