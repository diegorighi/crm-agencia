package br.com.agencia.crm.agenciacrm.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.DadosPessoaisRecordDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dadosPessoais")
public class DadosPessoaisEntity {

    @Setter
    private String primeiroNome;
    
    @Setter
    private String nomeDoMeio;

    @Setter
    private String sobrenome;
    private String dataNascimento;

    @Setter
    private String sexo;

    @Setter
    private String estadoCivil;
    @Setter
    private String profissao;

    public DadosPessoaisEntity(DadosPessoaisRecordDTO dadosPessoais) {
        this.primeiroNome = dadosPessoais.primeiroNome();
        this.nomeDoMeio = dadosPessoais.nomeDoMeio();
        this.sobrenome = dadosPessoais.sobrenome();
        this.dataNascimento = dadosPessoais.dataNascimento();
        this.sexo = dadosPessoais.sexo().getDescricao();
        this.estadoCivil = dadosPessoais.estadoCivil().getDescricao();
        this.profissao = dadosPessoais.profissao();
    }


}
