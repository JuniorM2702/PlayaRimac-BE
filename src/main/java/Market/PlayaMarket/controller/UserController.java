package Market.PlayaMarket.controller;

import Market.PlayaMarket.exception.ResourceNotFoundException;
import Market.PlayaMarket.model.bd.Role;
import Market.PlayaMarket.model.bd.User;
import Market.PlayaMarket.model.dto.UserDto;
import Market.PlayaMarket.service.RoleService;
import Market.PlayaMarket.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    @GetMapping("")
    public ResponseEntity<List<UserDto>> listarUsuario(){
        List<UserDto> usuarioDtoList = userService.ListarUsuario().stream()
                .map(userService::convertToDto)
                .collect(Collectors.toList());
        if(usuarioDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarioDtoList, HttpStatus.OK);
    }

    @PostMapping("/save-user")
    public ResponseEntity<User> registrarUser(@RequestBody User usuario) {
        Role userRole = roleService.findByNomrol("User");
        if (userRole == null) {
            userRole = new Role();
            userRole.setNomrol("User");
            roleService.saveRole(userRole);
        }
        usuario.getRoles().add(userRole);

        return new ResponseEntity<>(userService.guardarUsuario(usuario), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuarioxId(@PathVariable Integer id) {
        User usuario = userService.obtenerUsuarioxId(id).orElseThrow(
                () -> new ResourceNotFoundException("ID User: " + id + " don't exist!"));
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Integer id, @RequestBody User usuario) {
        User nuevoUsario = userService.obtenerUsuarioxId(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID User " + id + " don't exist!"));
        nuevoUsario.setNomusuario(usuario.getNomusuario());
        nuevoUsario.setActivo(true);
        nuevoUsario.setApellidos(usuario.getApellidos());
        nuevoUsario.setEmail(usuario.getEmail());
        nuevoUsario.setNombres(usuario.getNombres());
        nuevoUsario.setPassword(usuario.getPassword());

        return new ResponseEntity<>(userService.guardarUsuario(nuevoUsario), HttpStatus.OK);
    }

}
