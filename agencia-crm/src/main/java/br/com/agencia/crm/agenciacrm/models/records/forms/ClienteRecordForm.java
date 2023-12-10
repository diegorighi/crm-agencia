package br.com.agencia.crm.agenciacrm.models.records.forms;

import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;

public record ClienteRecordForm(
    String primeiroNome,
    String nomeDoMeio,
    String sobrenome,
    String dataNascimento,
    SexoEnum sexo,
    EstadoCivilEnum estadoCivil,
    String profissao,
    String cpf,
    String passaporte,
    String dataVencimentoPassaporte,
    String email,
    String celular,
    String logradouro,
    Integer numero,
    String complemento,
    String cidade,
    UfEnum uf,
    String cep,
    String pais
) {}
