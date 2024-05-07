package org.trainning.gerenciamentopessoas.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.trainning.gerenciamentopessoas.entity.Pessoa;

import java.util.Optional;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    @Query("from Pessoa p where p.cpf = :cpf")
    Optional<Pessoa> obterPessoaPorCpf(String cpf);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from Pessoa p where p.cpf = :cpf")
    void deletarPessoaPorCpf(String cpf);


}
