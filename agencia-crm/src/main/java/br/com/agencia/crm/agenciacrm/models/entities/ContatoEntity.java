package br.com.agencia.crm.agenciacrm.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.ContatoRecordDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contatos")
public class ContatoEntity {

    private String email;
    private String celular;

    public ContatoEntity(ContatoRecordDTO contato) {
        this.email = contato.email();
        this.celular = contato.celular();
    }
}
