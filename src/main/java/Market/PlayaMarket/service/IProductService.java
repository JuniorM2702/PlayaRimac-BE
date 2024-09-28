package Market.PlayaMarket.service;

import Market.PlayaMarket.model.bd.Product;

import java.util.List;

public interface IProductService {
    Product agregarProducto(Product product);
    Product obtenerProductoXId(Integer id);
    List<Product> obtenerProductoXCategoria(String categoria);
    List<Product> listarProductos();
    Product actualizarProducto(Product product);
    List<Product> obtenerProductosEnOferta();
}
