package br.com.agencia.crm.agenciacrm.models.records.dto;

import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;

public record EnderecoRecordDTO(
    String logradouro,
    Integer numero,
    String complemento,
    String cidade,
    UfEnum uf,
    String cep,
    String pais
) {


}
