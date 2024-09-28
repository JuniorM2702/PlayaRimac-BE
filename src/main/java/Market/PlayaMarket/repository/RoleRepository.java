package Market.PlayaMarket.repository;

import Market.PlayaMarket.model.bd.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByNomrol(String nomrol);
}
