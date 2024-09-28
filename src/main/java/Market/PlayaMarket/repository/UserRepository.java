package Market.PlayaMarket.repository;


import Market.PlayaMarket.model.bd.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByNomusuario(String nomusuario);
    User findByNombres(String nombres);
    User findByEmail(String correo);
}
