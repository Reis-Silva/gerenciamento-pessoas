package org.trainning.gerenciamentopessoas.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trainning.gerenciamentopessoas.entity.Endereco;
import org.trainning.gerenciamentopessoas.service.EnderecoService;

import java.util.List;

@RestController
@RequestMapping("/pessoa/endereco")
@Transactional
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping(value = "/criar/{idPessoa}")
    public ResponseEntity<?> criar(@PathVariable Long idPessoa, @RequestBody List<Endereco> enderecos) {
        enderecos = enderecoService.incluir(idPessoa, enderecos);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecos);
    }

    @PostMapping(value = "/listar", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> obterLista(@RequestParam("cpf") String cpf){
        List<Endereco> enderecos = enderecoService.obterLista(cpf);

        return ResponseEntity.ok().body(enderecos);
    }

    @PostMapping(value = "/editar")
    public ResponseEntity<?> editar(@RequestBody Endereco endereco) {
        endereco = enderecoService.editar(endereco);

        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @DeleteMapping(value = "/excluir", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> excluir(@RequestParam("id") Long id){
        enderecoService.excluir(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
