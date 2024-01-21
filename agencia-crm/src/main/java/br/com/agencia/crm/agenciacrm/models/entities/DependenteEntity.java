package br.com.agencia.crm.agenciacrm.models.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import br.com.agencia.crm.agenciacrm.models.records.dto.DependenteRecordDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor
@Document("dependente")
public class DependenteEntity implements Cliente, Serializable {

    @Id
	private String id;

    @Field("parent_id") @NonNull private String parentId;

    @NonNull private DadosPessoaisEntity dadosPessoais;
    @NonNull private DocumentosEntity documentos;

    public DependenteEntity(DependenteRecordDTO cliente) {
        this.parentId = cliente.parent_id();
		this.dadosPessoais = new DadosPessoaisEntity(cliente.dadosPessoais());
		this.documentos = new DocumentosEntity(cliente.documentos());
	}
    
    @Override
    public String getDadosPessoaisNomeCompleto() {
        StringBuilder nomeCompleto = new StringBuilder();
		nomeCompleto.append(dadosPessoais.getPrimeiroNome());
		
		if(dadosPessoais.getNomeDoMeio() == null) nomeCompleto.append(" ");
		else nomeCompleto.append(" ").append(dadosPessoais.getNomeDoMeio()).append(" ");

		nomeCompleto.append(dadosPessoais.getSobrenome());
		return nomeCompleto.toString();
    }

    @Override
    public String getDadosPessoaisDataNascimento() {
        return dadosPessoais.getDataNascimento();
    }

    @Override
    public String getDocumentosCpf() {
        return documentos.getCpf();
    }

    @Override
    public String getDocumentosPassaporte() {
        return documentos.getPassaporte();
    }
    
}
