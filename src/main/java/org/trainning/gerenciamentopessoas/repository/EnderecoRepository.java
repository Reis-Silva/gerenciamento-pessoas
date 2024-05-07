package org.trainning.gerenciamentopessoas.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.trainning.gerenciamentopessoas.entity.Endereco;

import java.util.List;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

    @Query("from Endereco e where e.idPessoa = :pessoaId")
    List<Endereco> obterLista(Long pessoaId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from Endereco e where e.idPessoa = :pessoaId")
    void deletarTodosEnderecosPorIdPessoa(Long pessoaId);

}
