package Market.PlayaMarket.controller;

import Market.PlayaMarket.model.bd.Product;
import Market.PlayaMarket.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<Product> agregarProducto(@RequestBody Product product) {
        Product nuevoProducto = productService.agregarProducto(product);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> obtenerProductoXId(@PathVariable Integer id) {
        Product producto = productService.obtenerProductoXId(id);
        return producto != null ? new ResponseEntity<>(producto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Product>> obtenerProductoXCategoria(@PathVariable String categoria) {
        List<Product> productos = (List<Product>) productService.obtenerProductoXCategoria(categoria);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(productos);
    }


    @GetMapping
    public ResponseEntity<List<Product>> listarProductos() {
        List<Product> productos = productService.listarProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Employee', 'Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> actualizarProducto(@PathVariable Integer id, @RequestBody Product product) {
        Product productoExistente = productService.obtenerProductoXId(id);
        if (productoExistente != null) {
            product.setIdproducto(id);
            Product productoActualizado = productService.actualizarProducto(product);
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ofertas")
    public ResponseEntity<List<Product>> obtenerProductosEnOferta() {
        List<Product> productosEnOferta = productService.obtenerProductosEnOferta();
        return ResponseEntity.ok(productosEnOferta);
    }
}