package pl.bfilas.sales;

import java.util.UUID;

import pl.bfilas.sales.cart.Cart;
import pl.bfilas.sales.cart.CartItem;
import pl.bfilas.sales.cart.CartStorage;
import pl.bfilas.sales.offer.Offer;
import pl.bfilas.sales.offer.OfferMaker;
import pl.bfilas.sales.payment.DummyPaymentGateway;
import pl.bfilas.sales.payment.PaymentData;
import pl.bfilas.sales.payment.PaymentGateway;
import pl.bfilas.sales.products.ProductDetails;
import pl.bfilas.sales.products.ProductDetailsProvider;
import pl.bfilas.sales.products.ProductNotAvailableException;
import pl.bfilas.sales.reservation.Reservation;
import pl.bfilas.sales.reservation.ReservationStorage;

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
