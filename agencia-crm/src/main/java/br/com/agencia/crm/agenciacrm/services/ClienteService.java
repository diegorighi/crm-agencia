package br.com.agencia.crm.agenciacrm.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;
import br.com.agencia.crm.agenciacrm.repositories.ClienteRepository;
import br.com.agencia.crm.agenciacrm.utils.ClienteUtils;

/**
 * Classe de serviço para Cliente
 * @author Diego Righi
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    /**
     * Coesão de responsabilidade: verifica se cliente já existe na base
     * @param cpf do cliente
     * @return true se cliente já existe, false se não existe
     */
    public Boolean existeCliente(String cpf) {
        return repository.existsByDocumentosCpf(cpf);
    }

    /**
     * Coesão de responsabilidade: cadastra cliente na base
     * @param mongoEntity cliente a ser cadastrado
     * @return mongoEntity salvo
     */
    @Transactional
    public ClienteEntity cadastro(ClienteEntity mongoEntity) {
        return repository.save(mongoEntity);
    }

    /**
     * Coesão de responsabilidade: lista todos os clientes da base
     * @param pageRequest
     * @return lista de clientes paginada
     */
    public Page<ClienteEntity> listarClientes(PageRequest pageRequest) {
        Page<ClienteEntity> mongoEntities = repository.findAll(pageRequest);
        return new PageImpl<>(mongoEntities.getContent(), pageRequest, mongoEntities.getTotalElements());
    }

    @Cacheable(value = "cliente", key = "#cpf", unless = "#result == null || #exception != null")
    public ClienteEntity buscarClientePorCPF(String cpf) {
        return repository.findByDocumentosCpf(cpf);
    }

    public Boolean exitemAlteracoes(String cpf, ClienteEditRecordForm form) {
        ClienteEntity cliente = repository.findByDocumentosCpf(cpf);
        return ClienteUtils.comparaFormComEntity(form, cliente);
    }

    @Transactional
    @CachePut(value = "cliente", key = "#cliente.documentos.cpf")
    public ClienteEntity editarCliente(ClienteEntity cliente, ClienteEditRecordForm form){
        //Atualiza dados do cliente
        cliente.getDadosPessoais().setSobrenome(form.sobrenome());
        cliente.getDadosPessoais().setEstadoCivil(form.estadoCivil().getEstadoCivil()); 
        cliente.getDadosPessoais().setProfissao(form.profissao());  
        cliente.getDocumentos().setPassaporte(form.passaporte());
        cliente.getDocumentos().setDataVencimentoPassaporte(form.dataVencimentoPassaporte());
        cliente.getContato().setEmail(form.email());
        cliente.getContato().setCelular(form.celular());
        cliente.getEndereco().setLogradouro(form.logradouro()); 
        cliente.getEndereco().setNumero(form.numero());
        cliente.getEndereco().setComplemento(form.complemento());
        cliente.getEndereco().setCidade(form.cidade());
        cliente.getEndereco().setUf(form.uf().getUf());
        cliente.getEndereco().setCep(form.cep());
        cliente.getEndereco().setPais(form.pais());
        
        return cliente;
    }

    @CacheEvict(value = "clientes", key = "#cpf")
    public void removerCliente(String cpf) {
        if(existeCliente(cpf)){
            repository.deleteByDocumentosCpf(cpf);
        }else{
            throw new RuntimeException("Cliente não encontrado");
        }
    }

    @CachePut(value = "clientes", key = "#cpf")
    public void adicionarDependente(String cpf, ClienteRecordForm form) {
        if(existeCliente(cpf)){
            if(dependenteExiste(form.cpf())){
                throw new RuntimeException("Dependente já cadastrado");
            }else{
                ClienteEntity mongoEntity = repository.findByDocumentosCpf(cpf);
                ClienteEntity dependente = ClienteUtils.formToEntity(form);
    
                ClienteUtils.addDependente(dependente, mongoEntity.getDependentes());
                repository.save(mongoEntity);
            }
        }else{
            throw new RuntimeException("Cliente não encontrado");
        }
    }
    
    @CacheEvict(value = "clientes", key = "#cpf")
	public void removerDependente(String cpf, String cpfDependente) {
		if (existeCliente(cpf)) {
			if (dependenteExiste(cpfDependente)) {
				ClienteEntity mongoEntity = repository.findByDocumentosCpf(cpf);

                ClienteUtils.removeDependente(cpfDependente, mongoEntity.getDependentes());
				repository.save(mongoEntity);
			} else {
				throw new RuntimeException("Dependente não encontrado");
			}
		} else {
			throw new RuntimeException("Cliente não encontrado");
		}
	}

    //Converte lista de entidades para lista de DTOs
    private List<ClienteRecordDTO> converteListaEntityParaListaDTO(Page<ClienteEntity> mongoEntities) {
        List<ClienteRecordDTO> clienteRecordDTOs = mongoEntities.stream()
                .map(ClienteUtils::entityToDto)
                .collect(Collectors.toList());
        return clienteRecordDTOs;
    }

    //Verifica se dependente existe na base
    private Boolean dependenteExiste(String cpf) {
        if(repository.existsByDependentesDocumentosCpf(cpf))
            return true;
        else
            return false;
    }
}
