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
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Document("clientes")
public class ClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	private String id;

    private DadosPessoaisEntity dadosPessoais;
    private DocumentosEntity documentos;
    private ContatoEntity contato;
    private EnderecoEntity endereco;
    private List<ClienteEntity> dependentes = new ArrayList<ClienteEntity>();

    @Deprecated
    public ClienteEntity(){}

    public ClienteEntity(ClienteRecordDTO cliente) {
        this.dadosPessoais = new DadosPessoaisEntity(cliente.dadosPessoais());
        this.documentos = new DocumentosEntity(cliente.documentos());
        this.contato = new ContatoEntity(cliente.contato());
        this.endereco = new EnderecoEntity(cliente.endereco());
    }   

    public void addDependente(ClienteEntity dependente){
        this.dependentes.add(dependente);
    }

}
