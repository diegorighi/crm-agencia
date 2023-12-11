package br.com.agencia.crm.agenciacrm.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;
import br.com.agencia.crm.agenciacrm.repositories.ClienteRepository;
import br.com.agencia.crm.agenciacrm.utils.ClienteUtils;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Transactional
    public void cadastro(ClienteRecordForm form) {
        ClienteRecordDTO formTDto = ClienteUtils.formTDto(form);
        ClienteEntity mongoEntity = ClienteUtils.dtoToEntity(formTDto);

        repository.save(mongoEntity);
    }

    public Boolean existeCliente(String cpf) {
        return repository.existsByDocumentosCpf(cpf);
    }

    public List<ClienteRecordDTO> listarClientes() {
        List<ClienteEntity> mongoEntities = repository.findAll();

        List<ClienteRecordDTO> clienteRecordDTOs = mongoEntities.stream()
                .map(ClienteUtils::entityToDto) // Fix: Define the missing method entityToDto in the ClienteUtils class
                .collect(Collectors.toList());

        return clienteRecordDTOs;
    }
    
    
}
