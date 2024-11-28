package dev.rdomingos.testeudemy.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}