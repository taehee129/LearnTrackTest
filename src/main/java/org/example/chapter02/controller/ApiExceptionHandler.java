package org.example.chapter02.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String VALIDATION_CODE = "VALIDATION_ERROR";

    /** @Valid @RequestBody 바인딩 실패 (DTO 필드 검증) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> FieldError.of(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .toList();

        ErrorResponse body = ErrorResponse.of(VALIDATION_CODE, "요청 본문 검증에 실패했습니다.", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /** @Validated + (Query/Path/자체 타입 검증)에서 발생하는 제약 위반 */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<FieldError> errors = ex.getConstraintViolations().stream()
                .map(this::toFieldError)
                .toList();

        ErrorResponse body = ErrorResponse.of(VALIDATION_CODE, "요청 파라미터 검증에 실패했습니다.", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /** @ModelAttribute 바인딩 실패 (폼/쿼리스트링 → 객체) */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> FieldError.of(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .toList();

        ErrorResponse body = ErrorResponse.of(VALIDATION_CODE, "요청 바인딩에 실패했습니다.", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /** 필수 요청 파라미터 누락 */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ErrorResponse body = ErrorResponse.of(
                VALIDATION_CODE,
                "필수 요청 파라미터가 누락되었습니다.",
                List.of(FieldError.of(ex.getParameterName(), null, "required parameter is missing"))
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /** 경로 변수 누락 (비정상 매핑) */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleMissingPathVariable(MissingPathVariableException ex) {
        ErrorResponse body = ErrorResponse.of(
                VALIDATION_CODE,
                "경로 변수(Path Variable)가 누락되었습니다.",
                List.of(FieldError.of(ex.getVariableName(), null, "missing path variable"))
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /** 타입 변환 실패 (?page=abc 와 같이 타입 불일치) */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        Object value = ex.getValue();
        String reason = "type mismatch (required: " +
                (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown") + ")";
        ErrorResponse body = ErrorResponse.of(
                VALIDATION_CODE,
                "요청 파라미터 타입이 올바르지 않습니다.",
                List.of(FieldError.of(name, value, reason))
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /** JSON 파싱/역직렬화 실패 (형식 오류) */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse body = ErrorResponse.of(
                VALIDATION_CODE,
                "요청 본문(JSON) 형식이 올바르지 않습니다.",
                List.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /** 그 외 – 필요시 별도 비즈니스 예외를 분리해 매핑 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEtc(Exception ex) {
        ErrorResponse body = ErrorResponse.of(
                "INTERNAL_ERROR",
                "서버 내부 오류가 발생했습니다.",
                List.of()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private FieldError toFieldError(ConstraintViolation<?> v) {
        // propertyPath 예: "create.arg0.name" 또는 "name"
        String path = v.getPropertyPath() == null ? null : v.getPropertyPath().toString();
        Object invalid = v.getInvalidValue();
        String msg = v.getMessage();
        return FieldError.of(path, invalid, msg);
    }

}
