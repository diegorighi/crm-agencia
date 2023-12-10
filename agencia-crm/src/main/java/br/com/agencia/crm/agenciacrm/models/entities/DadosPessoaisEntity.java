package br.com.agencia.crm.agenciacrm.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.DadosPessoaisRecordDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Document(collection = "dadosPessoais")
public class DadosPessoaisEntity {

    private String primeiroNome;
    private String nomeDoMeio;
    private String sobrenome;
    private String dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String profissao;

    public DadosPessoaisEntity(DadosPessoaisRecordDTO dadosPessoais) {
        this.primeiroNome = dadosPessoais.primeiroNome();
        this.nomeDoMeio = dadosPessoais.nomeDoMeio();
        this.sobrenome = dadosPessoais.sobrenome();
        this.dataNascimento = dadosPessoais.dataNascimento();
        this.sexo = dadosPessoais.sexo().getSexo();
        this.estadoCivil = dadosPessoais.estadoCivil().getEstadoCivil();
        this.profissao = dadosPessoais.profissao();
    }


}
