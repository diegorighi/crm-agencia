package br.com.agencia.crm.agenciacrm.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;

public interface ClienteRepository extends MongoRepository<ClienteEntity, String>{

}
