package Market.PlayaMarket.exception;

import Market.PlayaMarket.model.dto.ErrorMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorMessageDto errorResponse = ErrorMessageDto.builder()
                .message("You do not have permission to perform this action")
                .statusCode(HttpStatus.FORBIDDEN.value())
                .dateError(new Date())
                .description(request.getRequestURI())
                .build();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("aplication/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

    }
}
