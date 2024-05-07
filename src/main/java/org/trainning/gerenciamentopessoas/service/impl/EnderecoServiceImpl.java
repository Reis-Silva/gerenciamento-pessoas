package org.trainning.gerenciamentopessoas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trainning.gerenciamentopessoas.config.AppConfiguration;
import org.trainning.gerenciamentopessoas.entity.Endereco;
import org.trainning.gerenciamentopessoas.entity.Pessoa;
import org.trainning.gerenciamentopessoas.exception.PessoaException;
import org.trainning.gerenciamentopessoas.repository.EnderecoRepository;
import org.trainning.gerenciamentopessoas.repository.PessoaRepository;
import org.trainning.gerenciamentopessoas.service.EnderecoService;
import org.trainning.gerenciamentopessoas.validation.EnderecoValidator;

import java.util.*;


@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public List<Endereco> obterLista(String cpf) {
        Pessoa pessoa = pessoaRepository.obterPessoaPorCpf(cpf)
                .orElseThrow(() -> new PessoaException(AppConfiguration.messageSource().getMessage("pessoa.nao.encontrada",null, Locale.getDefault())));

        return new ArrayList<>(enderecoRepository.obterLista(pessoa.getId()));
    }

    @Override
    public List<Endereco> incluir(Long idPessoa, List<Endereco> enderecos){
        enderecos.forEach(endereco -> endereco.setIdPessoa(idPessoa));

        List<Endereco> enderecosTemp = new ArrayList<>((Collection) enderecoRepository.findAllById(Collections.singleton(idPessoa)));
        enderecosTemp.addAll(enderecos);
        EnderecoValidator.quantidadePrincipalvalidar(enderecosTemp);

        return (List<Endereco>) enderecoRepository.saveAll(enderecos);
    }

    @Override
    public Endereco editar(Endereco endereco) {
        EnderecoValidator.enderecoExistenteValidar(endereco.getId());

        List<Endereco> enderecosTemp = new ArrayList<>((Collection) enderecoRepository.findAllById(Collections.singleton(endereco.getIdPessoa())));
        enderecosTemp.remove(endereco);
        enderecosTemp.add(endereco);

        EnderecoValidator.quantidadePrincipalvalidar(enderecosTemp);

        return enderecoRepository.save(endereco);
    }

    @Override
    public void excluir(Long id) {
        EnderecoValidator.enderecoExistenteValidar(id);

        enderecoRepository.deleteById(id);
    }
}
