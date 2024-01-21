package br.com.agencia.crm.agenciacrm.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.agencia.crm.agenciacrm.models.entities.DependenteEntity;
import br.com.agencia.crm.agenciacrm.models.entities.TitularEntity;
import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaAssentoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaClasseEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaRefeicaoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;
import br.com.agencia.crm.agenciacrm.models.records.dto.ContatoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DadosPessoaisRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DependenteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DocumentosRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.EnderecoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.PreferenciasRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.TitularRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.DependenteEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.DependenteRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularRecordForm;

public class ClienteUtils {

    public static TitularRecordDTO titularFormToDto(TitularRecordForm form) {
        return new TitularRecordDTO(
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

    public static DependenteRecordDTO dependenteFormToDto(DependenteRecordForm form) {
        return new DependenteRecordDTO(
            form.parent_id(),
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
            )
        );
    }

    public static TitularEntity titularDtoToEntity(TitularRecordDTO dto) {
        return new TitularEntity(dto);
    }

    public static TitularRecordDTO titularEntityToDto(TitularEntity entity) {
        return new TitularRecordDTO(
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

    private static List<DependenteRecordDTO> dependentesEntityToDto(List<DependenteEntity> dependentes) {
        return dependentes.stream()
                .map(ClienteUtils::dependenteEntityToDto)
                .collect(Collectors.toList());
    }

    public static TitularEntity titularFormToEntity(TitularRecordForm form) {
        return new TitularEntity(titularFormToDto(form));
    }

    public static DependenteEntity dependenteFormToEntity(DependenteRecordForm form) {
        return new DependenteEntity(dependenteFormToDto(form));
    }

    public static void addDependente(TitularEntity dependente, List<TitularEntity> dependentes) {
		dependentes.add(dependente);
	}

	public static void removeDependente(String cpfDependente, List<TitularEntity> dependentes) {
	    Iterator<TitularEntity> iterator = dependentes.iterator();
	    while (iterator.hasNext()) {
	        TitularEntity dependente = iterator.next();
	        if (cpfDependente.equals(dependente.getDocumentos().getCpf())) {
	            iterator.remove();
	            return;
	        }
	    }
	}

    public static HashMap<String, Object> comparaFormComEntity(TitularEditRecordForm form, TitularEntity entity, HashMap<String, Object> alteracoesMap) {

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

    public static HashMap<String, Object> comparaFormComEntity(DependenteEditRecordForm form, DependenteEntity entity, HashMap<String, Object> alteracoesMap) {

        if(!form.primeiroNome().equals(entity.getDadosPessoais().getPrimeiroNome())){
            alteracoesMap.put("primeiroNome", form.primeiroNome());
        }
        if(!form.nomeDoMeio().equals(entity.getDadosPessoais().getNomeDoMeio())){
            alteracoesMap.put("nomeDoMeio", form.nomeDoMeio());
        }
        if(!form.sobrenome().equals(entity.getDadosPessoais().getSobrenome())){
            alteracoesMap.put("sobrenome", form.sobrenome());
        }
        if(!form.estadoCivil().equals(EstadoCivilEnum.fromString(entity.getDadosPessoais().getEstadoCivil()))){
            alteracoesMap.put("estadoCivil", form.estadoCivil());
        }
        if(!form.profissao().equals(entity.getDadosPessoais().getProfissao())){
            alteracoesMap.put("profissao", form.profissao());
        }
        if(!form.passaporte().equals(entity.getDocumentos().getPassaporte())){
            alteracoesMap.put("passaporte", form.passaporte());
        }
        if(!form.dataVencimentoPassaporte().equals(entity.getDocumentos().getDataVencimentoPassaporte())){
            alteracoesMap.put("dataVencimentoPassaporte", form.dataVencimentoPassaporte());
        }

        return alteracoesMap;
    }

    public static <T> HashMap<String, T> mapeamentoAlteracoes(String campo, T valor){
        HashMap<String, T> alteracoes = new HashMap<>();
        alteracoes.put(campo, valor);
        return alteracoes;
    }

    public static DependenteRecordDTO dependenteEntityToDto(DependenteEntity dependente) {
        return new DependenteRecordDTO(
            dependente.getParentId(),
            new DadosPessoaisRecordDTO(
                dependente.getDadosPessoais().getPrimeiroNome(),
                dependente.getDadosPessoais().getNomeDoMeio(),
                dependente.getDadosPessoais().getSobrenome(),
                dependente.getDadosPessoais().getDataNascimento(),
                SexoEnum.fromString(dependente.getDadosPessoais().getSexo()),
                EstadoCivilEnum.fromString(dependente.getDadosPessoais().getEstadoCivil()),
                dependente.getDadosPessoais().getProfissao()
            ),
            new DocumentosRecordDTO(
                dependente.getDocumentos().getCpf(),
                dependente.getDocumentos().getPassaporte(),
                dependente.getDocumentos().getDataVencimentoPassaporte()
            )
        );
    }

}
