package br.com.agencia.crm.agenciacrm.controllers;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteRecordForm;
import br.com.agencia.crm.agenciacrm.services.ClienteService;

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
        if(service.existeCliente(form.cpf()))
            throw new RuntimeException("Cliente já cadastrado");
        else
        service.cadastro(form);
    }   

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteRecordDTO>> listarCliente() {
        List<ClienteRecordDTO> listaClientes = service.listarClientes();
        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/{cpf}")
    public void buscarClientePorCPF(@PathVariable String cpf) {

    }   

    @PutMapping("/editar/{cpf}") 
    public void editarCliente(@RequestBody String cpf) {

    }   

    @DeleteMapping("/excluir/{cpf}")
    public void excluirCliente(@PathVariable String cpf) {

    }
    
}
