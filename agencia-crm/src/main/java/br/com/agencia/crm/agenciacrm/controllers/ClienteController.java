package br.com.agencia.crm.agenciacrm.controllers;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;
import br.com.agencia.crm.agenciacrm.services.ClienteService;
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
    public void cadastrarCliente(@RequestBody @Valid final ClienteRecordForm form) {
        if(!service.existeCliente(form.cpf()))
            service.cadastro(form);
        else
            throw new RuntimeException("Cliente já cadastrado");
    }   

    @GetMapping("/listar")
    public ResponseEntity<Page<ClienteRecordDTO>> listarCliente(
        @RequestParam(value = "pagina", defaultValue = "0") int pagina,
        @RequestParam(value = "tamanho", defaultValue = "50") int tamanho) {

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.Direction.ASC, "nome");
        Page<ClienteRecordDTO> listaClientes = service.listarClientes(pageRequest);
        
        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteRecordDTO> buscarClientePorCPF(@PathVariable String cpf) {
        return ResponseEntity.ok(service.buscarClientePorCPF(cpf));
    }   

    @PutMapping("/editar/{cpf}") 
    public ResponseEntity<ClienteRecordDTO> editarCliente(@PathVariable String cpf, @RequestBody @Valid final ClienteEditRecordForm form) {
        return ResponseEntity.ok(service.editarCliente(cpf, form));
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
