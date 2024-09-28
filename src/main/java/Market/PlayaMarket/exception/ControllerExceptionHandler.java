package Market.PlayaMarket.exception;

import Market.PlayaMarket.model.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto resourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request
    ){
        return ErrorMessageDto.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .dateError(new Date())
                .description(request.getDescription(true)).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessageDto handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
        return ErrorMessageDto.builder()
                .message("You do not have permission to perform this action")
                .statusCode(HttpStatus.FORBIDDEN.value())
                .dateError(new Date())
                .description(request.getDescription(false))
                .build();
    }
}
