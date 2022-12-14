package com.splitscale.fordastore.api.url.delete;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.splitscale.shield.endpoints.url.delete.DeleteUrlEndpoint;

@RestController
@CrossOrigin(allowedHeaders = "Authorization")
@RequestMapping("/api/urls")
public class DeleteUrlController {
  DeleteUrlEndpoint endpoint;

  public DeleteUrlController(DeleteUrlEndpoint endpoint) {
    this.endpoint = endpoint;
  }

  @ResponseBody
  @DeleteMapping(path = "/{urlId}")
  public ResponseEntity<String> deleteUrl(@PathVariable Long urlId,
      @RequestHeader(value = "Authorization") String jwsToken) throws IOException, GeneralSecurityException {

    endpoint.delete(urlId, jwsToken);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<String> handleInternalServerError(IOException e) {
    return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(GeneralSecurityException.class)
  public ResponseEntity<String> handleGeneralSecurityException(GeneralSecurityException e) {
    return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
  }
}
