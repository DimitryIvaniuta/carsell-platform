package com.carsell.platform.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

    /**
     * Exposes the CSRF token as JSON.
     * Spring Security automatically populates the request attribute "org.springframework.security.web.csrf.CsrfToken"
     * which is automatically injected into the method argument.
     */
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(final CsrfToken token) {
        // The CsrfToken argument is automatically populated by Spring Security's CsrfFilter.
        return token;
    }

}
