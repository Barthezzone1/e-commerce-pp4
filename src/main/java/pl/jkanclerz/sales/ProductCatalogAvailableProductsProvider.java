package pl.jkanclerz.sales;

import pl.jkanclerz.productcatalog.ProductCatalog;
import pl.jkanclerz.productcatalog.ProductData;

import java.util.Optional;

public class ProductCatalogAvailableProductsProvider implements AvailableProductsProvider {

    ProductCatalog productCatalog;

    public ProductCatalogAvailableProductsProvider(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    @Override
    public Optional<ProductDetails> getById(String productId) {
        ProductData data = productCatalog.getDetails(productId);

        if (data == null) {
            return Optional.empty();
        }

        return Optional.of(new ProductDetails(productId, data.getTitle(), data.getPrice()));
    }
}
