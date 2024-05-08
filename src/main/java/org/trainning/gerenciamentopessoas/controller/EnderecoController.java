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
import org.trainning.gerenciamentopessoas.entity.Endereco;
import org.trainning.gerenciamentopessoas.service.EnderecoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pessoa/endereco")
@Transactional
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Operation(description = "Criar um cadastro de um endereço para uma Pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a lista do objeto Endereco", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Endereco.class))),
            @ApiResponse(responseCode = "404", description = "Erro de Negócios", content = @Content)
    })
    @PostMapping(value = "/criar/{idPessoa}")
    public ResponseEntity<?> criar(@PathVariable Long idPessoa, @RequestBody List<Endereco> enderecos) {
        enderecos = enderecoService.incluir(idPessoa, enderecos);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecos);
    }

    @Operation(description = "Retorna uma lista de Endereços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de Objetos Endereco", content =  @Content(mediaType = "application/json", schema = @Schema(implementation = Endereco.class)))
    })
    @PostMapping(value = "/listar")
    public ResponseEntity<?> obterLista(@RequestBody Map<String,String> cpf){
        List<Endereco> enderecos = enderecoService.obterLista(cpf.get("cpf"));

        return ResponseEntity.ok().body(enderecos);
    }

    @Operation(description = "Retorna uma edição de dados de um Endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um Objeto Endereco editado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Endereco.class)))
    })
    @PostMapping(value = "/editar")
    public ResponseEntity<?> editar(@RequestBody Endereco endereco) {
        endereco = enderecoService.editar(endereco);

        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @Operation(description = "Deleta os dados de um Endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleta um Objeto Endereco do banco de dados", content = @Content)
    })
    @DeleteMapping(value = "/excluir")
    public ResponseEntity<?> excluir(@RequestBody Map<String,Long> id){
        enderecoService.excluir(id.get("id"));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
