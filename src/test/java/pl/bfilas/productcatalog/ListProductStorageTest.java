package pl.bfilas.productcatalog;

import org.junit.jupiter.api.Test;

import pl.bfilas.productcatalog.ListProductStorage;
import pl.bfilas.productcatalog.ProductData;
import pl.bfilas.productcatalog.ProductStorage;

import static org.junit.jupiter.api.Assertions.*;

public class ListProductStorageTest {

    @Test
    void itAllowsToStoreAndLoadProduct() {
        ProductData product = thereIsExampleProduct();
        ProductStorage listProductStorage = thereIsListProductStorage();

        listProductStorage.save(product);
        ProductData loaded = listProductStorage.load(product.getId());

        assertEquals(product.getId(), loaded.getId());
        assertEquals(product.getName(), loaded.getName());
    }

    private ProductStorage thereIsListProductStorage() {
        return new ListProductStorage();
    }

    private ProductData thereIsExampleProduct() {
        return new ProductData("lego", "Nice One");
    }
}
