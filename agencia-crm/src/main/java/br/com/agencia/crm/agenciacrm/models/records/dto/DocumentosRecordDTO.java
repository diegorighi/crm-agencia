package br.com.agencia.crm.agenciacrm.models.records.dto;

import br.com.agencia.crm.agenciacrm.models.entities.DocumentosEntity;

public record DocumentosRecordDTO(
    String cpf,
    String passaporte,
    String dataVencimentoPassaporte
) {

    
}
