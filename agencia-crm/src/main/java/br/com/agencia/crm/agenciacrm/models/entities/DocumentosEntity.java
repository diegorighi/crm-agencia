package br.com.agencia.crm.agenciacrm.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.DocumentosRecordDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "documentos")
public class DocumentosEntity {

    private String cpf;
    private String passaporte;
    private String dataVencimentoPassaporte;

    public DocumentosEntity(DocumentosRecordDTO documentos) {
        this.cpf = documentos.cpf();
        this.passaporte = documentos.passaporte();
        this.dataVencimentoPassaporte = documentos.dataVencimentoPassaporte();
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }

    public void setDataVencimentoPassaporte(String dataVencimentoPassaporte) {
        this.dataVencimentoPassaporte = dataVencimentoPassaporte;
    }

}
