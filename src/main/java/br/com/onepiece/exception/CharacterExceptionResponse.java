package br.com.onepiece.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CharacterExceptionResponse {

    private Date timeStamp;

    private String message;

    private String details;

}
