package org.trainning.gerenciamentopessoas.service;

import org.trainning.gerenciamentopessoas.entity.Pessoa;

import java.util.Collection;
import java.util.Optional;

public interface PessoaService {

    Collection<Pessoa> obterLista();

    Optional<Pessoa> obterPorCpf(String cpf);

    Pessoa incluir(Pessoa pessoa);

    Pessoa editar(Pessoa pessoa);

    void excluir(String cpf);
}
