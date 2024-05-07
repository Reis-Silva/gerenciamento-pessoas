package org.trainning.gerenciamentopessoas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.trainning.gerenciamentopessoas.config.AppConfiguration;
import org.trainning.gerenciamentopessoas.entity.Endereco;
import org.trainning.gerenciamentopessoas.entity.Pessoa;
import org.trainning.gerenciamentopessoas.exception.EnderecoException;
import org.trainning.gerenciamentopessoas.exception.PessoaException;
import org.trainning.gerenciamentopessoas.repository.EnderecoRepository;
import org.trainning.gerenciamentopessoas.repository.PessoaRepository;
import org.trainning.gerenciamentopessoas.service.impl.EnderecoServiceImpl;
import org.trainning.gerenciamentopessoas.service.impl.PessoaServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    @InjectMocks
    PessoaServiceImpl pessoaServiceImpl;

    @InjectMocks
    EnderecoServiceImpl enderecoServiceImpl;

    @Mock
    PessoaRepository pessoaRepository;

    @Mock
    EnderecoRepository enderecoRepository;

    Pessoa pessoa;
    Pessoa pessoaDois;
    Pessoa pessoaTres;

    Pessoa pessoaTemp;

    List<Pessoa> pessoas = new ArrayList<>();

    List<Pessoa> pessoasTemp = new ArrayList<>();

    List<Endereco> enderecos = new ArrayList<>();

    List<Endereco> enderecosDois = new ArrayList<>();
    List<Endereco> enderecosTemp = new ArrayList<>();


    @BeforeEach
    public void setup(){
        //criar_sucesso
        Endereco endereco = new Endereco(0L, "Fit Icoaraci", 66810010, 472, "Belém", "Pará", true, 0L);
        enderecos.add(endereco);
        pessoa = new Pessoa(0L,"cesar", LocalDateTime.of(1992, 3,23, 0, 0, 0),"00000000001",enderecos);

        //criar_regra_negocio_pessoa_existe_erro - Mesmo CPF
        pessoaDois = new Pessoa(1L,"cesar", LocalDateTime.of(1992, 3,23, 0, 0, 0),"00000000001",enderecos);

        //criar_regra_negocio_Endereco_principal_erro - Dois endereços sendo principal
        Endereco enderecoDois = new Endereco(1L, "Fit Icoaraci", 66810010, 472, "Belém", "Pará", true, 0L);
        enderecosDois.add(endereco);
        enderecosDois.add(enderecoDois);

        //obterLista
        pessoaTres = new Pessoa(1L,"cesar", LocalDateTime.of(1992, 3,23, 0, 0, 0),"00000000002",enderecos);
        pessoas.add(pessoa);
        pessoas.add(pessoaTres);
    }

    @Test
    void criar_sucesso() {
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(enderecoRepository.saveAll(enderecos)).thenReturn(enderecos);

        pessoaTemp = pessoaServiceImpl.incluir(pessoa);
        enderecosTemp = enderecoServiceImpl.incluir(pessoaTemp.getId(), pessoaTemp.getEndereco());

        pessoa.getEndereco().clear();
        pessoa.getEndereco().addAll(enderecosTemp);

        assertEquals(pessoa, pessoaTemp);
        assertEquals(enderecos, enderecosTemp);
    }

    @Test
    void criar_regra_negocio_pessoa_existe_erro() {
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(enderecoRepository.saveAll(enderecos)).thenReturn(enderecos);

        pessoaTemp = pessoaServiceImpl.incluir(pessoa);
        enderecosTemp = enderecoServiceImpl.incluir(pessoaTemp.getId(), pessoaTemp.getEndereco());

        pessoa.getEndereco().clear();
        pessoa.getEndereco().addAll(enderecosTemp);

        assertEquals(pessoa, pessoaTemp);
        assertEquals(enderecos, enderecosTemp);

        when(pessoaRepository.save(pessoaDois))
                .thenThrow(new PessoaException(AppConfiguration.messageSource().getMessage("pessoa.ja.existe",null, Locale.getDefault())));

        final PessoaException e = assertThrows(PessoaException.class, ()->{
            pessoaRepository.save(pessoaDois);
        });

        assertThat(e.getMessage(), is(AppConfiguration.messageSource().getMessage("pessoa.ja.existe",null, Locale.getDefault())));
    }

    @Test
    void criar_regra_negocio_Endereco_principal_erro() {
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(enderecoRepository.saveAll(enderecos)).thenReturn(enderecos);

        pessoaTemp = pessoaServiceImpl.incluir(pessoa);
        enderecosTemp = enderecoServiceImpl.incluir(pessoaTemp.getId(), pessoaTemp.getEndereco());

        pessoa.getEndereco().clear();
        pessoa.getEndereco().addAll(enderecosTemp);

        assertEquals(pessoa, pessoaTemp);
        assertEquals(enderecos, enderecosTemp);

        when(enderecoRepository.saveAll(enderecosDois))
                .thenThrow(new EnderecoException(AppConfiguration.messageSource().getMessage("endereco.mais.um.principal",null, Locale.getDefault())));

        final EnderecoException e = assertThrows(EnderecoException.class, ()->{
            enderecoRepository.saveAll(enderecosDois);
        });

        assertThat(e.getMessage(), is(AppConfiguration.messageSource().getMessage("endereco.mais.um.principal",null, Locale.getDefault())));

    }

    @Test
    void obterLista() {
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        pessoasTemp = (List<Pessoa>) pessoaServiceImpl.obterLista();

        assertEquals(pessoas, pessoasTemp);
    }

    @Test
    void editar_sucesso(){
        pessoaRepository.save(pessoa);
    }

    @Test
    void editar_pessoa_nao_encontrada() {
        when(pessoaRepository.save(pessoa))
                .thenThrow(new PessoaException(AppConfiguration.messageSource().getMessage("pessoa.nao.encontrada",null, Locale.getDefault())));

        final PessoaException e = assertThrows(PessoaException.class, ()->{
            pessoaRepository.save(pessoa);
        });

        assertThat(e.getMessage(), is(AppConfiguration.messageSource().getMessage("pessoa.nao.encontrada",null, Locale.getDefault())));
    }

    @Test
    void excluir_Sucesso() {
        pessoaRepository.deletarPessoaPorCpf(pessoa.getCpf());
    }
}