package whereQR.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    protected ErrorResponse handleDataException(CustomException customException) {
        return new ErrorResponse(customException.getErrorType(), customException.getMessage(), customException.getPath());
    }

    @ExceptionHandler(value = { Exception.class })
    protected ErrorResponse handleDataException(Exception exception) {
        return new ErrorResponse(ErrorType.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

}
