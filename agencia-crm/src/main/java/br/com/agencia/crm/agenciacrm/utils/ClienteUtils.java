package br.com.agencia.crm.agenciacrm.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;
import br.com.agencia.crm.agenciacrm.models.entities.ContatoEntity;
import br.com.agencia.crm.agenciacrm.models.entities.DadosPessoaisEntity;
import br.com.agencia.crm.agenciacrm.models.entities.DocumentosEntity;
import br.com.agencia.crm.agenciacrm.models.entities.EnderecoEntity;
import br.com.agencia.crm.agenciacrm.models.entities.PreferenciasEntity;
import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaAssentoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaClasseEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaRefeicaoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.ContatoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DadosPessoaisRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DocumentosRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.EnderecoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.PreferenciasRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;

public class ClienteUtils {

    public static ClienteRecordDTO formToDto(ClienteRecordForm form) {
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
            new PreferenciasRecordDTO(
                form.preferenciaClasse(),
                form.preferenciaAssento(), 
                form.preferenciaRefeicao()
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
                    new PreferenciasRecordDTO(
                        PreferenciaClasseEnum.fromString(entity.getPreferencias().getPreferenciaClasse()),
                        PreferenciaAssentoEnum.fromString(entity.getPreferencias().getPreferenciaAssento()),
                        PreferenciaRefeicaoEnum.fromString(entity.getPreferencias().getPreferenciaRefeicao())
                    ),
                    new DocumentosRecordDTO(
                        entity.getDocumentos().getCpf(),
                        entity.getDocumentos().getPassaporte(),
                        entity.getDocumentos().getDataVencimentoPassaporte()
                    ),
                    new ContatoRecordDTO(
                        entity.getContatos().getEmail(),
                        entity.getContatos().getCelular()
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
        return new ClienteEntity(formToDto(form));
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

    public static HashMap<String, Object> comparaFormComEntity(ClienteEditRecordForm form, ClienteEntity entity, HashMap<String, Object> alteracoesMap) {

        if(!form.sobrenome().equals(entity.getDadosPessoais().getSobrenome())){
            alteracoesMap.put("sobrenome", form.sobrenome());
        }
        if(!form.estadoCivil().equals(EstadoCivilEnum.fromString(entity.getDadosPessoais().getEstadoCivil()))){
            alteracoesMap.put("estadoCivil", form.estadoCivil());
        }
        if(!form.profissao().equals(entity.getDadosPessoais().getProfissao())){
            alteracoesMap.put("profissao", form.profissao());
        }

        if(!form.preferenciaClasse().equals(PreferenciaClasseEnum.fromString(entity.getPreferencias().getPreferenciaClasse()))){
            alteracoesMap.put("preferenciaClasse", form.preferenciaClasse());
        }

        if(!form.preferenciaAssento().equals(PreferenciaAssentoEnum.fromString(entity.getPreferencias().getPreferenciaAssento()))){
            alteracoesMap.put("preferenciaAssento", form.preferenciaAssento());
        }

        if(!form.preferenciaRefeicao().equals(PreferenciaRefeicaoEnum.fromString(entity.getPreferencias().getPreferenciaRefeicao()))){
            alteracoesMap.put("preferenciaRefeicao", form.preferenciaRefeicao());
        }

        if(!form.passaporte().equals(entity.getDocumentos().getPassaporte())){
            alteracoesMap.put("passaporte", form.passaporte());
        }
        if(!form.dataVencimentoPassaporte().equals(entity.getDocumentos().getDataVencimentoPassaporte())){
            alteracoesMap.put("dataVencimentoPassaporte", form.dataVencimentoPassaporte());
        }
        if(!form.email().equals(entity.getContatos().getEmail())){
            alteracoesMap.put("email", form.email());
        }
        if(!form.celular().equals(entity.getContatos().getCelular())){
            alteracoesMap.put("celular", form.celular());
        }
        if(!form.logradouro().equals(entity.getEndereco().getLogradouro())){
            alteracoesMap.put("logradouro", form.logradouro());
        }
        if(!form.numero().equals(entity.getEndereco().getNumero())){
            alteracoesMap.put("numero", form.numero());
        }
        if(!form.complemento().equals(entity.getEndereco().getComplemento())){
            alteracoesMap.put("complemento", form.complemento());
        }
        if(!form.cidade().equals(entity.getEndereco().getCidade())){
            alteracoesMap.put("cidade", form.cidade());
        }
        if(!form.uf().equals(UfEnum.fromString(entity.getEndereco().getUf()))){
            alteracoesMap.put("uf", form.uf());
        }
        if(!form.cep().equals(entity.getEndereco().getCep())){
            alteracoesMap.put("cep", form.cep());
        }
        if(!form.pais().equals(entity.getEndereco().getPais())){
            alteracoesMap.put("pais", form.pais());
        }

        return alteracoesMap;
    }

    public static <T> HashMap<String, T> mapeamentoAlteracoes(String campo, T valor){
        HashMap<String, T> alteracoes = new HashMap<>();
        alteracoes.put(campo, valor);
        return alteracoes;
    }

}
