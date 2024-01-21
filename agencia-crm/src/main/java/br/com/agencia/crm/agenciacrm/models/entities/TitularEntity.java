package br.com.agencia.crm.agenciacrm.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.agencia.crm.agenciacrm.models.records.dto.TitularRecordDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor
@Document("titular")
public class TitularEntity implements Cliente, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NonNull private DadosPessoaisEntity dadosPessoais;
    @NonNull private PreferenciasEntity preferencias;
    @NonNull private DocumentosEntity documentos;
    @NonNull private ContatoEntity contatos;
    @NonNull private EnderecoEntity endereco;
	private List<DependenteEntity> dependentes = new ArrayList<DependenteEntity>();

	public TitularEntity(TitularRecordDTO cliente) {
		this.dadosPessoais = new DadosPessoaisEntity(cliente.dadosPessoais());
		this.preferencias = new PreferenciasEntity(cliente.preferencias());
		this.documentos = new DocumentosEntity(cliente.documentos());
		this.contatos = new ContatoEntity(cliente.contato());
		this.endereco = new EnderecoEntity(cliente.endereco());
	}

	@Override
	public String getDadosPessoaisNomeCompleto() {
		StringBuilder nomeCompleto = new StringBuilder();
		nomeCompleto.append(dadosPessoais.getPrimeiroNome());
		
		if(dadosPessoais.getNomeDoMeio() == null) nomeCompleto.append(" ");
		else nomeCompleto.append(" ").append(dadosPessoais.getNomeDoMeio()).append(" ");

		nomeCompleto.append(dadosPessoais.getSobrenome());
		return nomeCompleto.toString();
	}

	@Override
	public String getDadosPessoaisDataNascimento() {
		return dadosPessoais.getDataNascimento();
	}

	@Override
	public String getDocumentosCpf() {
		return documentos.getCpf();
	}

	@Override
	public String getDocumentosPassaporte() {
		return documentos.getPassaporte();
	}

	public List<Optional<DependenteEntity>> dependentes() {
		List<Optional<DependenteEntity>> dependentes = new ArrayList<Optional<DependenteEntity>>();
		for(DependenteEntity dependente : this.dependentes) {
			dependentes.add(Optional.of(dependente));
		}
		return dependentes;
	} 

	public void removeDependente(String cpf) {
		List<DependenteEntity> dependentesARemover = new ArrayList<>();

		this.dependentes.forEach(dependente -> {
			if(dependente.getDocumentosCpf().equals(cpf)) {
				dependentesARemover.add(dependente);
			}
		});

		this.dependentes.removeAll(dependentesARemover);
	}

}
