package Market.PlayaMarket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSecurityDto {
    private Integer id;
    private String usuario;
    private String token;
    private String rol;
    private String nombres;
    private String apellidos;
}
