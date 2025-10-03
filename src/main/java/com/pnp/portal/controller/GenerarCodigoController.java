package com.pnp.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerarCodigoController {

    @GetMapping("/generarCodigo")
    public String generarCodigo(@RequestParam String banco) {
        int codigo = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(codigo);
    }
}
