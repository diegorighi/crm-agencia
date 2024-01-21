package br.com.agencia.crm.agenciacrm.controllers;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.agencia.crm.agenciacrm.models.entities.Cliente;
import br.com.agencia.crm.agenciacrm.models.entities.TitularEntity;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DependenteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.TitularRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.DependenteEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.DependenteRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularRecordForm;
import br.com.agencia.crm.agenciacrm.models.wrapper.ResponseWrapper;
import br.com.agencia.crm.agenciacrm.services.ClienteService;
import br.com.agencia.crm.agenciacrm.utils.ClienteUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService service;

    // Injeção de dependência do construtor
    public ClienteController(@Autowired ClienteService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseWrapper<TitularRecordDTO>> cadastrarCliente(
            @RequestBody @Valid TitularRecordForm form) {
        Optional<Cliente> cliente = service.cadastroProcesso(form);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseWrapper<TitularRecordDTO>(
                        ClienteUtils.titularEntityToDto((TitularEntity) cliente.get()),
                        "Cliente cadastrado com sucesso!",
                        true));

    }

    @GetMapping("/listar")
    public ResponseEntity<Page<TitularRecordDTO>> listarCliente(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "50") int tamanho) {

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.Direction.ASC, "nome");
        Page<TitularEntity> listaClientes = service.listarClientes(pageRequest);

        Page<TitularRecordDTO> listaClientesDTO = listaClientes.map(ClienteUtils::titularEntityToDto);
        return ResponseEntity.ok(listaClientesDTO);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable String cpf) {
        ClienteDTO cliente = service.buscarPorCPF(cpf);
        return ResponseEntity.status(200).body(cliente);
    }

    @PostMapping("/dependente/incluir")
    public ResponseEntity<ResponseWrapper<DependenteRecordDTO>> incluirDependente(@RequestBody @Valid DependenteRecordForm form) {
        DependenteRecordDTO dependente = service.incluirDependente(form);
        System.out.println(dependente);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseWrapper<DependenteRecordDTO>(
                        dependente,
                        "Dependente cadastrado com sucesso!",
                        true));
    }

    @PatchMapping("/editar/titular/{cpf}")
    public ResponseEntity<ResponseWrapper<HashMap<String, Object>>> editarTitular(
            @PathVariable String cpf,
            @RequestBody TitularEditRecordForm formEdit) {

        HashMap<String, Object> alteracoes = service.editarTitular(cpf, formEdit);

        return ResponseEntity.ok(
                new ResponseWrapper<HashMap<String, Object>>(
                        alteracoes,
                        "Alterações realizadas com sucesso!",
                        true));
    }

    @DeleteMapping("/excluir/{cpf}")
    public ResponseEntity<ResponseWrapper<String>> excluirCliente(@PathVariable String cpf) {
        Boolean clienteRemovido = service.removerCliente(cpf);

        return ResponseEntity.status(204).body(new ResponseWrapper<String>(
                        cpf,
                        "O Cpf informado foi removido com sucesso!",
                        clienteRemovido));
    }

    // Editar dependente
    @PatchMapping("/dependente/editar/{cpfDependente}")
    public ResponseEntity<ResponseWrapper<HashMap<String, Object>>> editarDependente(
            @PathVariable String cpfDependente,
            @RequestBody final DependenteEditRecordForm formEdit) {

        HashMap<String, Object> alteracoes = service.editarDependente(cpfDependente, formEdit);

        return ResponseEntity.ok(
                new ResponseWrapper<HashMap<String, Object>>(
                        alteracoes,
                        "Alterações realizadas com sucesso!",
                        true));
    }

}
