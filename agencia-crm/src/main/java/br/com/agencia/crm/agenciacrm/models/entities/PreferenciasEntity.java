package br.com.agencia.crm.agenciacrm.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.PreferenciasRecordDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "preferencias")
public class PreferenciasEntity {

    private String preferenciaClasse;
    private String preferenciaAssento;
    private String preferenciaRefeicao;

    public PreferenciasEntity(PreferenciasRecordDTO preferencias) {
        this.preferenciaClasse = preferencias.preferenciaClasse().getDescricao();
        this.preferenciaAssento = preferencias.preferenciaAssento().getDescricao();
        this.preferenciaRefeicao = preferencias.preferenciaRefeicao().getDescricao();
    }
}
