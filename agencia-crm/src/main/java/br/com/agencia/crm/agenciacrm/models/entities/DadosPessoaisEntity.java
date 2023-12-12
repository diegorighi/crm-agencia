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

    private String primeiroNome;
    private String nomeDoMeio;

    @Setter
    private String sobrenome;
    private String dataNascimento;
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
        this.sexo = dadosPessoais.sexo().getSexo();
        this.estadoCivil = dadosPessoais.estadoCivil().getEstadoCivil();
        this.profissao = dadosPessoais.profissao();
    }


}
