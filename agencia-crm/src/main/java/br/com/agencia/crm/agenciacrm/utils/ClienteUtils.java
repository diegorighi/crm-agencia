package br.com.agencia.crm.agenciacrm.utils;

import java.util.Iterator;
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
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteEditRecordForm;
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

    public static void addDependente(ClienteEntity dependente, List<ClienteEntity> dependentes) {
		dependentes.add(dependente);
	}

	public static void removeDependente(String cpfDependente, List<ClienteEntity> dependentes) {
	    Iterator<ClienteEntity> iterator = dependentes.iterator();
	    while (iterator.hasNext()) {
	        ClienteEntity dependente = iterator.next();
	        if (cpfDependente.equals(dependente.getDocumentos().getCpf())) {
	            iterator.remove();
	            return;
	        }
	    }
	}

    public static Boolean comparaFormComEntity(ClienteEditRecordForm form, ClienteEntity entity) {
        Boolean alteracoes = false;

        if(!form.sobrenome().equals(entity.getDadosPessoais().getSobrenome()))
            alteracoes = true;
        if(!form.estadoCivil().equals(EstadoCivilEnum.fromString(entity.getDadosPessoais().getEstadoCivil())))
            alteracoes = true; 
        if(!form.profissao().equals(entity.getDadosPessoais().getProfissao()))
            alteracoes = true;
        if(!form.passaporte().equals(entity.getDocumentos().getPassaporte()))
            alteracoes = true;
        if(!form.dataVencimentoPassaporte().equals(entity.getDocumentos().getDataVencimentoPassaporte()))
            alteracoes = true;
        if(!form.email().equals(entity.getContato().getEmail()))
            alteracoes = true;
        if(!form.celular().equals(entity.getContato().getCelular()))
            alteracoes = true;
        if(!form.logradouro().equals(entity.getEndereco().getLogradouro()))
            alteracoes = true;
        if(!form.numero().equals(entity.getEndereco().getNumero()))
            alteracoes = true;
        if(!form.complemento().equals(entity.getEndereco().getComplemento()))
            alteracoes = true;
        if(!form.cidade().equals(entity.getEndereco().getCidade()))
            alteracoes = true;
        if(!form.uf().equals(UfEnum.fromString(entity.getEndereco().getCidade())))
            alteracoes = true;
        if(!form.cep().equals(entity.getEndereco().getCep()))
            alteracoes = true;
        if(!form.pais().equals(entity.getEndereco().getPais()))
            alteracoes = true;
        

        return alteracoes;
    }

}
