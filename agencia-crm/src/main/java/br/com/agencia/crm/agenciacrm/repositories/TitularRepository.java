package br.com.agencia.crm.agenciacrm.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.agencia.crm.agenciacrm.models.entities.TitularEntity;

@Repository
public interface TitularRepository extends ClienteRepository<TitularEntity>{
    
    
}
