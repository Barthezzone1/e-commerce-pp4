package pl.bfilas.sales;

import java.util.List;
import java.util.Optional;

public class ListAvailableProductsProvider implements AvailableProductsProvider {
    private List<ProductDetails> products;

    public ListAvailableProductsProvider(List<ProductDetails> products) {

        this.products = products;
    }

    @Override
    public Optional<ProductDetails> getById(String productId) {
        return products.stream()
                .filter(productDetails -> productDetails.getId().equals(productId))
                .findFirst();
    }
}
