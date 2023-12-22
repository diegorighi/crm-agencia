package br.com.agencia.crm.agenciacrm.models.records.dto;

import java.util.List;

public record ClienteRecordDTO(
    DadosPessoaisRecordDTO dadosPessoais,
    PreferenciasRecordDTO preferencias,
    DocumentosRecordDTO documentos,
    ContatoRecordDTO contato,
    EnderecoRecordDTO endereco,
    List<ClienteRecordDTO> dependentes
) {

}
