package org.trainning.gerenciamentopessoas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trainning.gerenciamentopessoas.config.AppConfiguration;
import org.trainning.gerenciamentopessoas.entity.Endereco;
import org.trainning.gerenciamentopessoas.entity.Pessoa;
import org.trainning.gerenciamentopessoas.exception.PessoaException;
import org.trainning.gerenciamentopessoas.service.EnderecoService;
import org.trainning.gerenciamentopessoas.service.PessoaService;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
@Transactional
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoService enderecoService;

    @Operation(description = "Criar um cadastro de uma Pessoa com endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o Objeto Pessoa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
            @ApiResponse(responseCode = "404", description = "Erro de Negócios", content = @Content)
    })
    @PostMapping(value = "/criar")
    public ResponseEntity<?> criar(@RequestBody Pessoa pessoa) {
        pessoa = pessoaService.incluir(pessoa);
        List<Endereco> enderecos = enderecoService.incluir(pessoa.getId(), pessoa.getEndereco());
        pessoa.getEndereco().clear();
        pessoa.getEndereco().addAll(enderecos);

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @Operation(description = "Retorna uma lista de Pessoas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Objeto Pessoa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
    })
    @GetMapping(value = "/listar")
    public ResponseEntity<?> obterLista(){
        List<Pessoa> pessoas = (List<Pessoa>) pessoaService.obterLista();

        return ResponseEntity.status(HttpStatus.OK).body(pessoas);
    }

    @Operation(description = "Retorna uma Pessoa pelo seu CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um Objeto Pessoa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
    })
    @GetMapping(value = "/buscar")
    public ResponseEntity<?> buscar(@RequestBody Map<String,String> cpf){
        Optional<Pessoa> pessoa = pessoaService.obterPorCpf(cpf.get("cpf"));

        pessoa.orElseThrow(() -> new PessoaException(AppConfiguration.messageSource().getMessage("pessoa.nao.encontrada",null, Locale.getDefault())));

        return ResponseEntity.status(HttpStatus.OK).body(pessoa);
    }

    @Operation(description = "Retorna uma edição de dados de uma Pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um Objeto Pessoa editado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
    })
    @PostMapping(value = "/editar")
    public ResponseEntity<?> editar(@RequestBody Pessoa pessoa) {
        pessoa = pessoaService.editar(pessoa);

        return ResponseEntity.status(HttpStatus.OK).body(pessoa);
    }

    @Operation(description = "Deleta os dados de uma Pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleta os Objetos Pessoa e Endereco do banco de dados", content = @Content),
    })
    @DeleteMapping(value = "/excluir")
    public ResponseEntity<?> excluir(@RequestBody Map<String,String> cpf){
        pessoaService.excluir(cpf.get("cpf"));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
