package br.com.agencia.crm.agenciacrm.models.records.dto;

public record DependenteRecordDTO(
    String parent_id,
    DadosPessoaisRecordDTO dadosPessoais,
    DocumentosRecordDTO documentos
) implements ClienteDTO {
    
}
