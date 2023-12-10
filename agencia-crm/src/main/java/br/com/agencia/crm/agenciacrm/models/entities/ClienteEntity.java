package br.com.agencia.crm.agenciacrm.models.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "clientes")
public class ClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	private String id;

    private DadosPessoaisEntity dadosPessoais;
    private DocumentosEntity documentos;
    private ContatoEntity contato;
    private EnderecoEntity dadosEndereco;

    public ClienteEntity(ClienteRecordDTO cliente) {
        this.dadosPessoais = new DadosPessoaisEntity(cliente.dadosPessoais());
        this.documentos = new DocumentosEntity(cliente.documentos());
        this.contato = new ContatoEntity(cliente.contato());
        this.dadosEndereco = new EnderecoEntity(cliente.endereco());
    }   

}
