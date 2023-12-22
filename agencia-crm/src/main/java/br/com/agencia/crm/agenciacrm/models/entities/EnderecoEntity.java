package br.com.agencia.crm.agenciacrm.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.EnderecoRecordDTO;
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
@Document(collection = "endereco")
public class EnderecoEntity {

    private String logradouro;
    private Integer numero;
    private String complemento;
    private String cidade;
    private String uf;
    private String cep;
    private String pais;

    public EnderecoEntity(EnderecoRecordDTO endereco) {
        this.logradouro = endereco.logradouro();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf().getDescricao();
        this.cep = endereco.cep();
        this.pais = endereco.pais();
    }

    

}
