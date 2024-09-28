package Market.PlayaMarket.service;


import Market.PlayaMarket.model.bd.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User obtenerUsuarioPorNomUsuario(
            String nomusuario);
    User guardarUsuario(User usuario);
    List<User> ListarUsuario();
    Optional<User> obtenerUsuarioxId(Integer id);

    User obtenerUsuarioxCorreo(String correo);

}
