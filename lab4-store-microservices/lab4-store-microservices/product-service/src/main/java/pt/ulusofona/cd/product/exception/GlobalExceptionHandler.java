package pt.ulusofona.cd.product.exception;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(ResourceNotFoundException.class) ProblemDetail notFound(ResourceNotFoundException e){return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(DuplicateResourceException.class) ProblemDetail conflict(DuplicateResourceException e){return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,e.getMessage());}
 @ExceptionHandler(SupplierNotFoundException.class)
 ProblemDetail supplierNotFound(SupplierNotFoundException e){return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(SupplierUnavailableException.class) ProblemDetail unavailable(SupplierUnavailableException e){return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE,e.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ProblemDetail validation(MethodArgumentNotValidException e){ProblemDetail p=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Request validation failed");Map<String,String> m=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->m.put(x.getField(),x.getDefaultMessage()));p.setProperty("errors",m);return p;}
}
