package br.com.agencia.crm.agenciacrm.repositories;

import org.springframework.stereotype.Repository;

import br.com.agencia.crm.agenciacrm.models.entities.DependenteEntity;

@Repository
public interface DependenteClienteRepository extends ClienteRepository<DependenteEntity> {
    void deleteAllByParentId(String cpf);
}
