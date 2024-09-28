package Market.PlayaMarket.service;

import Market.PlayaMarket.model.bd.User;
import Market.PlayaMarket.model.bd.Role;
import Market.PlayaMarket.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User usuario =obtenerUsuarioXNomusuario(username);
        return autenticacionUsuario(
                usuario,
                obtenerListaRoles(usuario.getRoles())
        );
    }

    public User obtenerUsuarioXNomusuario(String nomusuario){
        return userRepository.findByNomusuario(nomusuario);
    }

    public User obtenerUsuarioXCorreo(String correo) {
        return userRepository.findByEmail(correo);
    }

    public List<GrantedAuthority> obtenerListaRoles(Set<Role> roles){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(Role rol : roles){
            authorityList.add(new
                    SimpleGrantedAuthority("ROLE_"+rol.getNomrol()));
        }
        return authorityList;
    }

    private UserDetails autenticacionUsuario(
            User usuario, List<GrantedAuthority> authorityList
    ){
        return new org.springframework.security.core.userdetails.User(
                usuario.getNomusuario(),
                usuario.getPassword(),
                usuario.getActivo(),
                true, true, true,
                authorityList
        );
    }
}
