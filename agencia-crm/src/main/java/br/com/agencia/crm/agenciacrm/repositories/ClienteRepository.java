package br.com.agencia.crm.agenciacrm.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface ClienteRepository<T> extends MongoRepository<T, String>{

    Boolean existsByDocumentos_Cpf(String cpf);

    Optional<T> findByDocumentos_Cpf(String cpf);

    void deleteByDocumentosCpf(String cpf);

}
