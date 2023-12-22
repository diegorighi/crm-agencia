package br.com.agencia.crm.agenciacrm.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import lombok.AllArgsConstructor;
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
@Document("clientes")
public class ClienteEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NonNull private DadosPessoaisEntity dadosPessoais;
    @NonNull private PreferenciasEntity preferencias;
    @NonNull private DocumentosEntity documentos;
    @NonNull private ContatoEntity contatos;
    @NonNull private EnderecoEntity endereco;
	private List<ClienteEntity> dependentes = new ArrayList<ClienteEntity>();

	public ClienteEntity(ClienteRecordDTO cliente) {
		this.dadosPessoais = new DadosPessoaisEntity(cliente.dadosPessoais());
		this.preferencias = new PreferenciasEntity(cliente.preferencias());
		this.documentos = new DocumentosEntity(cliente.documentos());
		this.contatos = new ContatoEntity(cliente.contato());
		this.endereco = new EnderecoEntity(cliente.endereco());
	}

	

}
