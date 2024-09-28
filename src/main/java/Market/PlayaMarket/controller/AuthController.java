package Market.PlayaMarket.controller;


import Market.PlayaMarket.model.bd.User;
import Market.PlayaMarket.model.dto.UserSecurityDto;
import Market.PlayaMarket.service.UserDetailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AtomicInteger anonUserIdCounter = new AtomicInteger(0);
    private UserDetailService userDetailService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    @Transactional(readOnly = true)
    public ResponseEntity<UserSecurityDto> autenticarUsuario(
            @RequestParam("usuario") String usuario,
            @RequestParam("password") String password
    ) throws  Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario, password
                    ));
            if(authentication.isAuthenticated()){
                User objUser = userDetailService
                        .obtenerUsuarioXNomusuario(usuario);
                String token = generarToken(objUser);
                String rol = objUser.getRoles().stream()
                        .map(role -> role.getNomrol())
                        .findFirst()
                        .orElse("User");
                UserSecurityDto usuarioSeguridadDto =
                        new UserSecurityDto(objUser.getIdusuario(),
                                usuario,token,rol, objUser.getNombres(), objUser.getApellidos());
                return new ResponseEntity<>(usuarioSeguridadDto, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
        }catch (Exception ex){
            throw  new Exception("Wrong User or Password!");
        }
    }

    private String generarToken(User usuario){
        String clave = "@PlayaRimac!";
        List<GrantedAuthority> grantedAuthorityList =
                userDetailService.obtenerListaRoles(usuario.getRoles());
        String token = Jwts.builder()
                .setId(usuario.getIdusuario().toString())
                .setSubject(usuario.getNomusuario())
                .claim("authorities",
                        grantedAuthorityList.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 15778476000L))
                .signWith(SignatureAlgorithm.HS512, clave.getBytes())
                .compact();
        return token;
    }

    @GetMapping("/session")
    public ResponseEntity<UserSecurityDto> generarSesionAnonima() {
        int anonUserId = anonUserIdCounter.getAndIncrement();
        String anonUsername = "guest";
        String rol = "User";

        String token = generarTokenAnonimo(anonUserId, anonUsername);

        UserSecurityDto usuarioSeguridadDto = new UserSecurityDto(anonUserId, anonUsername, token, rol, null, null);

        return new ResponseEntity<>(usuarioSeguridadDto, HttpStatus.OK);
    }

    private String generarTokenAnonimo(int userId, String username) {
        String clave = "@PlayaRimac!";
        String rol = "User";
        long unAnioEnMilisegundos = 365L * 24L * 60L * 60L * 1000L;
        String token = Jwts.builder()
                .setId(String.valueOf(userId))
                .setSubject(username)
                .claim("authorities", List.of(rol))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + unAnioEnMilisegundos))
                .signWith(SignatureAlgorithm.HS512, clave.getBytes())
                .compact();
        return token;
    }

}