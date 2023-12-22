package br.com.agencia.crm.agenciacrm.models.records.dto;

import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaAssentoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaClasseEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaRefeicaoEnum;

public record PreferenciasRecordDTO(

    PreferenciaClasseEnum preferenciaClasse,
    PreferenciaAssentoEnum preferenciaAssento,
    PreferenciaRefeicaoEnum preferenciaRefeicao

) {
    
}
