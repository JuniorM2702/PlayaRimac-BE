package Market.PlayaMarket.repository;

import Market.PlayaMarket.model.bd.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoria(String categoria);
    List<Product> findByEnOfertaTrue();

}
