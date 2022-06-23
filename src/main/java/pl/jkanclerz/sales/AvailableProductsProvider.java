package pl.jkanclerz.sales;

import java.util.Optional;

public interface AvailableProductsProvider {
    Optional<ProductDetails> getById(String productId);
}
