package projectspring.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String message){
        super(message);
    }

    public FileNotFoundException(String message, Throwable exception){
        super(message, exception);
    }
}
