package br.com.agencia.crm.agenciacrm.models.records.dto;

import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;

public record DadosPessoaisRecordDTO(
    String primeiroNome,
    String nomeDoMeio,
    String sobrenome,
    String dataNascimento,
    SexoEnum sexo,
    EstadoCivilEnum estadoCivil,
    String profissao
) {
    
}
