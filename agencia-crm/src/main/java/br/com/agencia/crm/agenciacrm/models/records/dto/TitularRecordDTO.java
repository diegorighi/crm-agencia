package br.com.agencia.crm.agenciacrm.models.records.dto;

import java.util.List;

public record TitularRecordDTO(
    DadosPessoaisRecordDTO dadosPessoais,
    PreferenciasRecordDTO preferencias,
    DocumentosRecordDTO documentos,
    ContatoRecordDTO contato,
    EnderecoRecordDTO endereco,
    List<DependenteRecordDTO> dependentes
) implements ClienteDTO {

}
