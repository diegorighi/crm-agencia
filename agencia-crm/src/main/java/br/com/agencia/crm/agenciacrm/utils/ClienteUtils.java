package br.com.agencia.crm.agenciacrm.utils;

import java.util.List;
import java.util.stream.Collectors;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;
import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;
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
            ),
            null
        );
    }

    public static ClienteEntity dtoToEntity(ClienteRecordDTO dto) {
        return new ClienteEntity(dto);
    }

    public static ClienteRecordDTO entityToDto(ClienteEntity entity) {
        return new ClienteRecordDTO(
                    new DadosPessoaisRecordDTO(
                        entity.getDadosPessoais().getPrimeiroNome(),
                        entity.getDadosPessoais().getNomeDoMeio(),
                        entity.getDadosPessoais().getSobrenome(),
                        entity.getDadosPessoais().getDataNascimento(),
                        SexoEnum.fromString(entity.getDadosPessoais().getSexo()),
                        EstadoCivilEnum.fromString(entity.getDadosPessoais().getEstadoCivil()),
                        entity.getDadosPessoais().getProfissao()
                    ),
                    new DocumentosRecordDTO(
                        entity.getDocumentos().getCpf(),
                        entity.getDocumentos().getPassaporte(),
                        entity.getDocumentos().getDataVencimentoPassaporte()
                    ),
                    new ContatoRecordDTO(
                        entity.getContato().getEmail(),
                        entity.getContato().getCelular()
                    ),
                    new EnderecoRecordDTO(
                        entity.getEndereco().getLogradouro(),
                        entity.getEndereco().getNumero(),
                        entity.getEndereco().getComplemento(),
                        entity.getEndereco().getCidade(),
                        UfEnum.fromString(entity.getEndereco().getUf()),
                        entity.getEndereco().getCep(),
                        entity.getEndereco().getPais()
                    ),
                    dependentesEntityToDto(entity.getDependentes())
                );
    }

    private static List<ClienteRecordDTO> dependentesEntityToDto(List<ClienteEntity> dependentes) {
        return dependentes.stream()
                .map(ClienteUtils::entityToDto)
                .collect(Collectors.toList());
    }

    public static ClienteEntity formToEntity(ClienteRecordForm form) {
        return new ClienteEntity(formTDto(form));
    }

}
