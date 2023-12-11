package br.com.agencia.crm.agenciacrm.models.records.dto;

public record ClienteRecordDTO(
    DadosPessoaisRecordDTO dadosPessoais,
    DocumentosRecordDTO documentos,
    ContatoRecordDTO contato,
    EnderecoRecordDTO endereco
) {

}
