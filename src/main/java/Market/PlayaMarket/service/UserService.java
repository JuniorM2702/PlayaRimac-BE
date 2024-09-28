package Market.PlayaMarket.service;

import Market.PlayaMarket.model.bd.Role;
import Market.PlayaMarket.model.bd.User;
import Market.PlayaMarket.model.dto.UserDto;
import Market.PlayaMarket.repository.RoleRepository;
import Market.PlayaMarket.repository.UserRepository;
import Market.PlayaMarket.util.DtoUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DtoUtil dtoUtil;

    @Override
    public User obtenerUsuarioPorNomUsuario(String nomusuario) {
        return userRepository.findByNomusuario(nomusuario);
    }

    @Override
    public User guardarUsuario(User usuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setApellidos(usuario.getApellidos());
        usuario.setEmail(usuario.getEmail());
        usuario.setNombres(usuario.getNombres());
        usuario.setNomusuario(usuario.getNomusuario());
        usuario.setActivo(true);
        Role usuarioRol = roleRepository.findByNomrol("User");
        usuario.setRoles(new HashSet<>(Arrays.asList(usuarioRol)));
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return userRepository.save(usuario);
    }

    @Override
    public List<User> ListarUsuario() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> obtenerUsuarioxId(Integer id) {
        Optional<User> usuario = userRepository.findById(id);
        if(usuario.isEmpty()){
            return Optional.empty();
        }
        return usuario;
    }

    @Override
    public User obtenerUsuarioxCorreo(String correo) {
        return userRepository.findByEmail(correo);
    }

    public UserDto convertToDto(User user) {
        UserDto userDto = (UserDto) dtoUtil.convertirADto(user, new UserDto());
        String roleName = user.getRoles().stream()
                .map(Role::getNomrol)
                .findFirst()
                .orElse(null);
        userDto.setRol(roleName);
        userDto.setName(user.getNombres()+" "+ user.getApellidos());
        userDto.setId(user.getIdusuario());
        userDto.setEmail(user.getEmail());
        return userDto;
    }


}
