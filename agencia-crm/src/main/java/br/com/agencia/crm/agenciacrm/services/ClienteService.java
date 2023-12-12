package br.com.agencia.crm.agenciacrm.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Transactional
    public void cadastro(ClienteRecordForm form) {
        if(!existeCliente(form.cpf())){
            ClienteRecordDTO formTDto = ClienteUtils.formTDto(form);
            ClienteEntity mongoEntity = ClienteUtils.dtoToEntity(formTDto);
            repository.save(mongoEntity);
        }else{
            throw new RuntimeException("Cliente já cadastrado");
        }
    }

    public Boolean existeCliente(String cpf) {
        return repository.existsByDocumentosCpf(cpf);
    }

    public Page<ClienteRecordDTO> listarClientes(PageRequest pageRequest) {
        Page<ClienteEntity> mongoEntities = repository.findAll(pageRequest);

        List<ClienteRecordDTO> clienteRecordDTOs = mongoEntities.stream()
                .map(ClienteUtils::entityToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(clienteRecordDTOs, pageRequest, mongoEntities.getTotalElements());
    }

    public ClienteRecordDTO buscarClientePorCPF(String cpf) {
        ClienteEntity mongoEntity = repository.findByDocumentosCpf(cpf);
        return ClienteUtils.entityToDto(mongoEntity);
    }

    @Transactional
    public ClienteRecordDTO editarCliente(String cpf, ClienteEditRecordForm form){
        //Verifica se cliente existe na base
        if(existeCliente(cpf)){
            //Busca cliente na base
            ClienteEntity mongoEntity = repository.findByDocumentosCpf(cpf);
            //Atualiza dados do cliente
            mongoEntity.getDadosPessoais().setSobrenome(form.sobrenome());
            mongoEntity.getDadosPessoais().setEstadoCivil(form.estadoCivil().getEstadoCivil()); 
            mongoEntity.getDadosPessoais().setProfissao(form.profissao());  
            mongoEntity.getDocumentos().setPassaporte(form.passaporte());
            mongoEntity.getDocumentos().setDataVencimentoPassaporte(form.dataVencimentoPassaporte());
            mongoEntity.getContato().setEmail(form.email());
            mongoEntity.getContato().setCelular(form.celular());
            mongoEntity.getEndereco().setLogradouro(form.logradouro()); 
            mongoEntity.getEndereco().setNumero(form.numero());
            mongoEntity.getEndereco().setComplemento(form.complemento());
            mongoEntity.getEndereco().setCidade(form.cidade());
            mongoEntity.getEndereco().setUf(form.uf().getUf());
            mongoEntity.getEndereco().setCep(form.cep());
            mongoEntity.getEndereco().setPais(form.pais());
            //Salva alterações  
            repository.save(mongoEntity);
            //Retorna cliente atualizado
            return ClienteUtils.entityToDto(mongoEntity);

        }else{
            throw new RuntimeException("Cliente não encontrado");
        }
    }

    public void removerCliente(String cpf) {
        if(existeCliente(cpf)){
            repository.deleteByDocumentosCpf(cpf);
        }else{
            throw new RuntimeException("Cliente não encontrado");
        }
    }

    public void adicionarDependente(String cpf, ClienteRecordForm form) {
        if(existeCliente(cpf)){
            if(dependenteExiste(form.cpf())){
                throw new RuntimeException("Dependente já cadastrado");
            }else{
                ClienteEntity mongoEntity = repository.findByDocumentosCpf(cpf);
                ClienteEntity dependente = ClienteUtils.formToEntity(form);
    
                mongoEntity.addDependente(dependente);
                repository.save(mongoEntity);
            }
        }else{
            throw new RuntimeException("Cliente não encontrado");
        }
    }

    private boolean dependenteExiste(String cpf) {
        if(repository.existsByDependentesDocumentosCpf(cpf))
            return true;
        else
            return false;
    }
}
