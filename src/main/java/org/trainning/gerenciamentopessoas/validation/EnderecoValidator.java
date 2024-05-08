package org.trainning.gerenciamentopessoas.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trainning.gerenciamentopessoas.config.AppConfiguration;
import org.trainning.gerenciamentopessoas.entity.Endereco;
import org.trainning.gerenciamentopessoas.exception.EnderecoException;
import org.trainning.gerenciamentopessoas.repository.EnderecoRepository;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

@Component
public class EnderecoValidator {

    @Autowired
    private static EnderecoRepository enderecoRepository;

    public static void quantidadePrincipalvalidar(List<Endereco> enderecos){
        Predicate<Endereco> quantidadePrincipal = Endereco::isPrincipal;

        if(enderecos.stream().filter(quantidadePrincipal).count() > 1){
            throw new EnderecoException(AppConfiguration.messageSource().getMessage("endereco.mais.um.principal",null, Locale.getDefault()));
        }
    }
}
