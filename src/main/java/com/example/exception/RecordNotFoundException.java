package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="Enregistrement non trouvé")
public class RecordNotFoundException extends RuntimeException {

}
