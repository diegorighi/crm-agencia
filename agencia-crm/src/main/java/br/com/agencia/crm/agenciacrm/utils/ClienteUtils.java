package br.com.agencia.crm.agenciacrm.utils;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.ContatoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DadosPessoaisRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DocumentosRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.EnderecoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;

public class ClienteUtils {
    
    public static ClienteRecordDTO formTDto(ClienteRecordForm form) {
        return new ClienteRecordDTO(
            new DadosPessoaisRecordDTO(
                form.primeiroNome(),
                form.nomeDoMeio(),
                form.sobrenome(),
                form.dataNascimento(),
                form.sexo(),
                form.estadoCivil(),
                form.profissao()
            ),
            new DocumentosRecordDTO(
                form.cpf(),
                form.passaporte(),
                form.dataVencimentoPassaporte()
            ),
            new ContatoRecordDTO(
                form.email(),
                form.celular()
            ),
            new EnderecoRecordDTO(
                form.logradouro(),
                form.numero(),
                form.complemento(),
                form.cidade(),
                form.uf(),
                form.cep(),
                form.pais()
            )
        );
    }

    public static ClienteEntity dtoToEntity(ClienteRecordDTO dto) {
        return new ClienteEntity(dto);
    }
}
