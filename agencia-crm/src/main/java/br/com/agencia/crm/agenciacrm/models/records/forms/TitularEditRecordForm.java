package br.com.agencia.crm.agenciacrm.models.records.forms;

import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaAssentoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaClasseEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaRefeicaoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;

public record TitularEditRecordForm(
    String sobrenome,
    EstadoCivilEnum estadoCivil,
    String profissao,
    PreferenciaClasseEnum preferenciaClasse,
    PreferenciaAssentoEnum preferenciaAssento,
    PreferenciaRefeicaoEnum preferenciaRefeicao,
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
) {
    
}
