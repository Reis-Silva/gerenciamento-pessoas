package org.trainning.gerenciamentopessoas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trainning.gerenciamentopessoas.config.AppConfiguration;
import org.trainning.gerenciamentopessoas.entity.Pessoa;
import org.trainning.gerenciamentopessoas.exception.PessoaException;
import org.trainning.gerenciamentopessoas.repository.EnderecoRepository;
import org.trainning.gerenciamentopessoas.repository.PessoaRepository;
import org.trainning.gerenciamentopessoas.service.PessoaService;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public PessoaServiceImpl(){

    }
    public PessoaServiceImpl(PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }
    @Override
    public Collection<Pessoa> obterLista() {
        return (Collection<Pessoa>) pessoaRepository.findAll();
    }

    @Override
    public Optional<Pessoa> obterPorCpf(String cpf) {
        return pessoaRepository.obterPessoaPorCpf(cpf);
    }

    @Override
    public Pessoa incluir(Pessoa pessoa) {
        if(obterPorCpf(pessoa.getCpf()).isPresent()){
            throw new PessoaException(AppConfiguration.messageSource().getMessage("pessoa.ja.existe",null, Locale.getDefault()));
        }

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa editar(Pessoa pessoa) {
        obterPorCpf(pessoa.getCpf())
                .orElseThrow(() -> new PessoaException(AppConfiguration.messageSource().getMessage("pessoa.nao.encontrada",null, Locale.getDefault())));

        return pessoaRepository.save(pessoa);
    }

    @Override
    public void excluir(String cpf) {
        Pessoa pessoaTemp = obterPorCpf(cpf)
                .orElseThrow(() -> new PessoaException(AppConfiguration.messageSource().getMessage("pessoa.nao.encontrada",null, Locale.getDefault())));

        enderecoRepository.deletarTodosEnderecosPorIdPessoa(pessoaTemp.getId());
        pessoaRepository.deletarPessoaPorCpf(cpf);
    }
}
