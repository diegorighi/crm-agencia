package br.com.agencia.crm.agenciacrm.models.records.dto;

import br.com.agencia.crm.agenciacrm.models.entities.ContatoEntity;

public record ContatoRecordDTO(
    String email,
    String celular
) {
    
}
