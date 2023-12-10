package br.com.agencia.crm.agenciacrm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;
import br.com.agencia.crm.agenciacrm.repositories.ClienteRepository;
import br.com.agencia.crm.agenciacrm.utils.ClienteUtils;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public void cadastro(ClienteRecordForm form) {
        ClienteRecordDTO formTDto = ClienteUtils.formTDto(form);
        ClienteEntity mongoEntity = ClienteUtils.dtoToEntity(formTDto);

        repository.save(mongoEntity);
    }
}
