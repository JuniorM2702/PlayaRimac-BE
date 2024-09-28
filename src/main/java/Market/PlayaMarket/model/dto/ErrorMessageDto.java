package Market.PlayaMarket.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ErrorMessageDto {
    private Integer statusCode;
    private Date dateError;
    private String message;
    private String description;
}
