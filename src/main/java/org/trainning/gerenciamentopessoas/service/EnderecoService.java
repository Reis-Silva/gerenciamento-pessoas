package org.trainning.gerenciamentopessoas.service;

import org.trainning.gerenciamentopessoas.entity.Endereco;

import java.util.List;

public interface EnderecoService {

    List<Endereco> obterLista(String cpf);

    public List<Endereco> incluir(Long id, List<Endereco> enderecos);

    Endereco editar(Endereco endereco);

    void excluir(Long id);
}
