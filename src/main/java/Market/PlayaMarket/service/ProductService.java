package Market.PlayaMarket.service;

import Market.PlayaMarket.model.bd.Product;
import Market.PlayaMarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product agregarProducto(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product obtenerProductoXId(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> obtenerProductoXCategoria(String categoria) {
        return productRepository.findByCategoria(categoria);
    }

    @Override
    public List<Product> listarProductos() {
        return productRepository.findAll();
    }

    @Override
    public Product actualizarProducto(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> obtenerProductosEnOferta() {
        return productRepository.findByEnOfertaTrue();
    }
}
