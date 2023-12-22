package br.com.agencia.crm.agenciacrm.controllers;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;

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
import br.com.agencia.crm.agenciacrm.models.wrapper.ResponseWrapper;
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
    
            return ResponseEntity.status(
                HttpStatus.CREATED).body(new ResponseWrapper<ClienteRecordDTO>(
                        ClienteUtils.entityToDto(cliente), 
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

    @GetMapping("/dependente/{cpfDependente}")
    public ResponseEntity<List<ClienteRecordDTO>> buscarDependentePorCPF(@PathVariable String cpfDependente) {
        ClienteEntity dependente = service.buscarDependentePorCPF(cpfDependente);
        if(dependente == null){
            throw new ClienteNaoEncontradoException("Dependente não encontrado!");
        }else{
            ClienteRecordDTO dto = ClienteUtils.entityToDto(dependente);
            List<ClienteRecordDTO> dtoDependente = dto.dependentes();
            return ResponseEntity.ok(dtoDependente);
        }
    }

    @PutMapping("/editar/{cpf}") 
    public ResponseEntity<ResponseWrapper<HashMap<String, Object>>> editarCliente(@PathVariable String cpf, 
        @RequestBody @Valid final ClienteEditRecordForm form) {
            HashMap<String, Object> mapaAlteracoes = new HashMap<>();

            if(service.buscarClientePorCPF(cpf) != null){
                ClienteEntity cliente = service.buscarClientePorCPF(cpf);
                mapaAlteracoes = service.exitemAlteracoes(form, cliente, mapaAlteracoes);
                if(!mapaAlteracoes.isEmpty()){
                    service.editarCliente(cliente, form);
                    service.cadastro(cliente);
                    return ResponseEntity.status(
                        HttpStatus.OK).body(new ResponseWrapper<HashMap<String, Object>>(
                        mapaAlteracoes,
                        "Mapa de valores alterados. Cliente alterado com sucesso!", 
                        true));
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
    public ResponseEntity<ResponseWrapper<ClienteRecordDTO>> incluirDependente(@PathVariable String cpf, @RequestBody @Valid ClienteRecordForm form) {
        // Verifica se o CPF já está cadastrado como dependente
        if(service.buscarDependentePorCPF(cpf) != null) {
            throw new ClienteJaCadastradoException("OPERAÇÃO NÃO PERMITIDA! Este CPF já está cadastrado como um dependente!");
        }

        // Verifica se o cliente existe na base de dados
        if(!service.existeCliente(cpf)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }

        // Verifica se o dependente já existe na base de dados
        if(service.buscarDependentePorCPF(form.cpf()) != null) {
            throw new ClienteJaCadastradoException("Dependente já cadastrado!");
        }

        // Adiciona o dependente ao cliente
        ClienteEntity cliente = service.buscarClientePorCPF(cpf);
        cliente = service.adicionarDependente(cliente, form);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseWrapper<ClienteRecordDTO>(
                ClienteUtils.formToDto(form), 
                "Dependente cadastrado com sucesso", 
                true
            )
        );
    }

    //Editar dependente
    @PutMapping("/dependente/editar/{cpfDependente}")
    public ResponseEntity<ResponseWrapper<HashMap<String, Object>>> editarDependente(
        @PathVariable String cpfDependente, 
        @RequestBody final ClienteEditRecordForm formEdit) {

        HashMap<String, Object> mapaAlteracoes = new HashMap<>();

        // Busca titular e lista de dependentes
        ClienteEntity titular = service.buscarDependentePorCPF(cpfDependente);
        List<ClienteEntity> listaDependentes = titular.getDependentes();

            if(!listaDependentes.isEmpty()){
                for(ClienteEntity dependente : listaDependentes){
                    if(dependente.getDocumentos().getCpf().equals(cpfDependente)){
                        mapaAlteracoes = service.existemAlteracoesDependente(formEdit, dependente, mapaAlteracoes);

                        if(!mapaAlteracoes.isEmpty()){
                            service.editarCliente(dependente, formEdit);
                            service.cadastro(titular);
                            return ResponseEntity.status(
                                HttpStatus.OK).body(new ResponseWrapper<HashMap<String, Object>>(
                                mapaAlteracoes,
                                "Mapa de valores alterados. Dependente alterado com sucesso!", 
                                true));
                        }else
                            throw new NaoExistemAlteracoesException("Não há alterações a serem feitas!");   

                    }else
                        throw new ClienteNaoEncontradoException("Dependente não encontrado na lista de dependentes!");
                }
            }else
                throw new ClienteNaoEncontradoException("Dependente não encontrado!");
            return null;
    }
    
    @DeleteMapping("/dependente/excluir/{cpf}/{cpfDependente}")
	public void removerDependente(@PathVariable String cpf, @PathVariable String cpfDependente) {
		service.removerDependente(cpf, cpfDependente);
	}
    
}
