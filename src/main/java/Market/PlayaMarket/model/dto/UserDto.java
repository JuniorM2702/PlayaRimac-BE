package Market.PlayaMarket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements DtoEntity {
    private Integer id;
    private String email;
    private String name;
    private String rol;

}
