package Market.PlayaMarket.service;

import Market.PlayaMarket.model.bd.Role;
import Market.PlayaMarket.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByNomrol(String nomrol) {
        return roleRepository.findByNomrol(nomrol);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
