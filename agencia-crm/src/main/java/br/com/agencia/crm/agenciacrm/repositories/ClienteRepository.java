package br.com.agencia.crm.agenciacrm.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;

@Repository
public interface ClienteRepository extends MongoRepository<ClienteEntity, String>{

    Boolean existsByDocumentosCpf(String cpf);

    ClienteEntity findByDocumentosCpf(String cpf);

    void deleteByDocumentosCpf(String cpf);

    boolean existsByDependentesDocumentosCpf(String cpf);

	ClienteEntity findByDependentesDocumentosCpf(String cpfDependente);

    ClienteEntity findByDocumentosCpfOrDependentesDocumentosCpf(String cpfTitular, String cpfDependente);

}
