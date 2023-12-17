package br.com.agencia.crm.agenciacrm.controllers;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.agencia.crm.agenciacrm.exceptions.ClienteJaCadastradoException;
import br.com.agencia.crm.agenciacrm.exceptions.ClienteNaoEncontradoException;
import br.com.agencia.crm.agenciacrm.exceptions.NaoExistemAlteracoesException;
import br.com.agencia.crm.agenciacrm.models.entities.ClienteEntity;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.wrapper.ResponseWrapper;
import br.com.agencia.crm.agenciacrm.services.ClienteService;
import br.com.agencia.crm.agenciacrm.utils.ClienteUtils;

import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService service;

    // Injeção de dependência do construtor
    public ClienteController(@Autowired ClienteService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseWrapper<ClienteRecordDTO>> cadastrarCliente(@RequestBody @Valid final ClienteRecordForm form) {
        if(service.existeCliente(form.cpf())){
            throw new ClienteJaCadastradoException("Cliente já cadastrado!");
        }
        else{
            ClienteEntity cliente = ClienteUtils.formToEntity(form);
            cliente = service.cadastro(cliente);
    
            ClienteRecordDTO novoCliente = ClienteUtils.entityToDto(cliente);
            return ResponseEntity.status(
            HttpStatus.CREATED).body(new ResponseWrapper<ClienteRecordDTO>(
                        novoCliente, 
                        "Cliente cadastrado com sucesso", 
                        true));
        }
    }   

    @GetMapping("/listar")
    public ResponseEntity<Page<ClienteRecordDTO>> listarCliente(
        @RequestParam(value = "pagina", defaultValue = "0") int pagina,
        @RequestParam(value = "tamanho", defaultValue = "50") int tamanho) {

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.Direction.ASC, "nome");
        Page<ClienteEntity> listaClientes = service.listarClientes(pageRequest);

        Page<ClienteRecordDTO> listaClientesDTO = listaClientes.map(ClienteUtils::entityToDto);
        return ResponseEntity.ok(listaClientesDTO);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteRecordDTO> buscarClientePorCPF(@PathVariable String cpf) {
        if(service.existeCliente(cpf)){
            ClienteEntity cliente = service.buscarClientePorCPF(cpf);
            ClienteRecordDTO dto = ClienteUtils.entityToDto(cliente);
            return ResponseEntity.ok(dto);
        }else{
            throw new ClienteNaoEncontradoException("Cliente não encontrado!");
        }
    }   

    @PutMapping("/editar/{cpf}") 
    public ResponseEntity<ClienteRecordDTO> editarCliente(@PathVariable String cpf, 
        @RequestBody @Valid final ClienteEditRecordForm form) {
            if(service.existeCliente(cpf)){
                if(service.exitemAlteracoes(cpf, form)){
                    ClienteEntity cliente = service.buscarClientePorCPF(cpf);
                    service.editarCliente(cliente, form);
                    service.cadastro(cliente);
                    ClienteRecordDTO entityToDto = ClienteUtils.entityToDto(cliente);
                    return ResponseEntity.ok(entityToDto);
                }else
                    throw new NaoExistemAlteracoesException("Não há alterações a serem feitas!");
                
            }else
                throw new ClienteNaoEncontradoException("Cliente não encontrado!");
    }   

    @DeleteMapping("/excluir/{cpf}")
    public void excluirCliente(@PathVariable String cpf) {
        service.removerCliente(cpf);
    }

    @PutMapping("/dependente/incluir/{cpf}")
    public void incluirDependente(@PathVariable String cpf, @RequestBody @Valid ClienteRecordForm form) {
        service.adicionarDependente(cpf, form);
    }
    
    @DeleteMapping("/dependente/excluir/{cpf}/{cpfDependente}")
	public void removerDependente(@PathVariable String cpf, @PathVariable String cpfDependente) {
		service.removerDependente(cpf, cpfDependente);
	}
    
}
