package dev.gedas.webeid.rest;

import dev.gedas.webeid.rest.models.IdTokenValidationRequest;
import dev.gedas.webeid.rest.models.IdTokenValidationResult;
import eu.webeid.security.exceptions.AuthTokenException;
import eu.webeid.security.validator.AuthTokenValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web-eid")
public class IdTokenController {

    private final AuthTokenValidator tokenValidator;

    public IdTokenController(AuthTokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @PostMapping("validate")
    public IdTokenValidationResult ValidateIdToken(@RequestBody IdTokenValidationRequest validationRequest) {
        try {
            tokenValidator.validate(validationRequest.getToken(), validationRequest.getNonce());
        } catch (AuthTokenException e) {
            return new IdTokenValidationResult("fail", e.getMessage());
        }

        return new IdTokenValidationResult("ok", null);
    }
}

