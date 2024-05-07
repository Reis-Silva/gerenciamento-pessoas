package org.trainning.gerenciamentopessoas.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trainning.gerenciamentopessoas.entity.Endereco;
import org.trainning.gerenciamentopessoas.entity.Pessoa;
import org.trainning.gerenciamentopessoas.service.EnderecoService;
import org.trainning.gerenciamentopessoas.service.PessoaService;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Transactional
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping(value = "/criar")
    public ResponseEntity<?> criar(@RequestBody Pessoa pessoa) {
        pessoa = pessoaService.incluir(pessoa);
        List<Endereco> enderecos = enderecoService.incluir(pessoa.getId(), pessoa.getEndereco());
        pessoa.getEndereco().clear();
        pessoa.getEndereco().addAll(enderecos);

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<?> obterLista(){
        List<Pessoa> pessoas = (List<Pessoa>) pessoaService.obterLista();

        return ResponseEntity.ok().body(pessoas);
    }

    @PostMapping(value = "/editar")
    public ResponseEntity<?> editar(@RequestBody Pessoa pessoa) {
        pessoa = pessoaService.editar(pessoa);

        return ResponseEntity.status(HttpStatus.OK).body(pessoa);
    }

    @DeleteMapping(value = "/excluir", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> excluir(@RequestParam("cpf") String cpf){
        pessoaService.excluir(cpf);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
