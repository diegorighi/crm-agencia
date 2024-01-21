package br.com.agencia.crm.agenciacrm.models.records.forms;

import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Empty;

import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaAssentoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaClasseEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaRefeicaoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TitularRecordForm(

    @Empty String parent_id,

    @NotEmpty(message = "O primeiro nome não pode estar vazio.")
    @Size(min = 2, max = 50, message = "O primeiro nome deve ter entre 2 e 50 caracteres.")
    String primeiroNome,

    @Size(max = 50, message = "O nome do meio não deve exceder 50 caracteres.")
    String nomeDoMeio,

    @NotEmpty(message = "O sobrenome não pode estar vazio.")
    @Size(min = 2, max = 50, message = "O sobrenome deve ter entre 2 e 50 caracteres.")
    String sobrenome,

    @NotEmpty(message = "A data de nascimento não pode estar vazia.")
    String dataNascimento,

    @NotNull(message = "O sexo não pode estar vazio.")
    SexoEnum sexo,

    @NotNull(message = "O estado civil não pode estar vazio.")
    EstadoCivilEnum estadoCivil,

    @NotEmpty(message = "A profissão não pode estar vazia.")
    @Size(max = 100, message = "A profissão não deve exceder 100 caracteres.")
    String profissao,

    @NotNull(message = "A preferência de classe não pode estar vazia.")
    PreferenciaClasseEnum preferenciaClasse,

    @NotNull(message = "A preferência de assento não pode estar vazia.")
    PreferenciaAssentoEnum preferenciaAssento,

    @NotNull(message = "A preferência de refeição não pode estar vazia.")
    PreferenciaRefeicaoEnum preferenciaRefeicao,

    @NotEmpty(message = "O CPF não pode estar vazio.")
    @CPF(message = "CPF inválido.")
    String cpf,

    @NotEmpty(message = "O passaporte não pode estar vazio.")
    @Size(min = 5, max = 20, message = "O passaporte deve ter entre 5 e 20 caracteres.")
    String passaporte,

    @NotEmpty(message = "A data de vencimento do passaporte não pode estar vazia.")
    String dataVencimentoPassaporte,

    @NotEmpty(message = "O email não pode estar vazio.")
    @Email(message = "Email inválido.")
    String email,

    @NotEmpty(message = "O número de celular não pode estar vazio.")
    @Size(min = 11, max = 30, message = "O celular deve ter entre 11 e 30 caracteres.")
    String celular,

    @NotEmpty(message = "O logradouro não pode estar vazio.")
    @Size(max = 100, message = "O logradouro não deve exceder 100 caracteres.")
    String logradouro,

    @Max(value = 99999, message = "O número não pode ser maior que 99999.")
    Integer numero,

    @Size(max = 50, message = "O complemento não deve exceder 50 caracteres.")
    String complemento,

    @NotEmpty(message = "A cidade não pode estar vazia.")
    @Size(max = 50, message = "A cidade não deve exceder 50 caracteres.")
    String cidade,

    @NotNull(message = "A UF não pode estar vazia.")
    UfEnum uf,

    @NotEmpty(message = "O CEP não pode estar vazio.")
    String cep,

    @NotEmpty(message = "O país não pode estar vazio.")
    @Size(max = 50, message = "O país não deve exceder 50 caracteres.")
    String pais
) implements ClienteForm {
    @Override
    public String getCpf() {
        return this.cpf;
    }

    @Override
    public String parent_id() {
        return this.parent_id;
    }

   
    
}
