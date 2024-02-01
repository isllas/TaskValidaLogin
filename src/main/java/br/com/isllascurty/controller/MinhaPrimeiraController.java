package br.com.isllascurty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
//http://localhost:8080/primeiraRota -----

public class MinhaPrimeiraController {
    
    /* Metodos de acesso HTTP
     * GET - Buscar informação
     * POST - Adicionar informação
     * PUT - Alterar uma informação
     * DELETE - Remover uma informação
     * PATCH - Alterar somente uma parte da informação
     * 
     */

    @GetMapping("/primeiroMetodo")
    public String primeiraMensagem(){

        return("Funcionou");

    }
}
