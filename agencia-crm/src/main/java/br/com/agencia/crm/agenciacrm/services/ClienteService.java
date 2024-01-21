package br.com.agencia.crm.agenciacrm.services;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agencia.crm.agenciacrm.exceptions.ClienteJaCadastradoException;
import br.com.agencia.crm.agenciacrm.exceptions.ClienteNaoEncontradoException;
import br.com.agencia.crm.agenciacrm.exceptions.DependenteException;
import br.com.agencia.crm.agenciacrm.models.entities.Cliente;
import br.com.agencia.crm.agenciacrm.models.entities.DependenteEntity;
import br.com.agencia.crm.agenciacrm.models.entities.TitularEntity;
import br.com.agencia.crm.agenciacrm.models.records.dto.ClienteDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DependenteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.ClienteForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.DependenteEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.DependenteRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularEditRecordForm;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularRecordForm;
import br.com.agencia.crm.agenciacrm.repositories.ClienteRepository;
import br.com.agencia.crm.agenciacrm.repositories.DependenteClienteRepository;
import br.com.agencia.crm.agenciacrm.utils.ClienteUtils;

/**
 * Classe de serviço para Cliente
 * 
 * @author Diego Righi
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository<TitularEntity> titularRepository;
    @Autowired
    private DependenteClienteRepository dependenteRepository;

    @Transactional
    public Optional<Cliente> cadastroProcesso(ClienteForm form) {
        // Verifica se cliente já existe na base
        if (existeCliente(form.getCpf()))
            throw new ClienteJaCadastradoException("Cliente já cadastrado!");
        else
            return Optional.of(cadastro(form));
    }

    /**
     * Coesão de responsabilidade: verifica se cliente já existe na base
     * 
     * @param cpf do cliente
     * @return true se cliente já existe, false se não existe
     */
    public Boolean existeCliente(String cpf) {
        Optional<TitularEntity> titular = titularRepository.findByDocumentos_Cpf(cpf);
        Optional<DependenteEntity> dependente = dependenteRepository.findByDocumentos_Cpf(cpf);

        if (titular.isPresent() || dependente.isPresent())
            return true;
        else
            return false;
    }

    /**
     * Coesão de responsabilidade: cadastra cliente na base
     * 
     * @param form cliente a ser cadastrado
     * @return mongoEntity salvo
     */
    private Cliente cadastro(ClienteForm form) {

        if (form.parent_id() == null) {
            TitularEntity titular = ClienteUtils.titularFormToEntity((TitularRecordForm) form);
            return titularRepository.save(titular);
        } else {
            DependenteEntity dependente = ClienteUtils.dependenteFormToEntity((DependenteRecordForm) form);
            return dependenteRepository.save(dependente);
        }

    }

    /**
     * Coesão de responsabilidade: lista todos os clientes da base
     * 
     * @param pageRequest
     * @return lista de clientes paginada
     */
    public Page<TitularEntity> listarClientes(PageRequest pageRequest) {
        Page<TitularEntity> mongoEntities = titularRepository.findAll(pageRequest);
        return new PageImpl<>(mongoEntities.getContent(), pageRequest, mongoEntities.getTotalElements());
    }

    @Cacheable(value = "cliente", key = "#cpf", unless = "#result == null || #exception != null")
    public ClienteDTO buscarPorCPF(String cpf) {
        if (existeCliente(cpf)) {
            Optional<TitularEntity> titular = titularRepository.findByDocumentos_Cpf(cpf);
            Optional<DependenteEntity> dependente = dependenteRepository.findByDocumentos_Cpf(cpf);

            if (!titular.isEmpty()) {
                ClienteDTO titularDTO = ClienteUtils.titularEntityToDto(titular.get());
                return titularDTO;
            } else {
                ClienteDTO dependenteDTO = ClienteUtils.dependenteEntityToDto(dependente.get());
                return dependenteDTO;
            }
        } else
            throw new ClienteNaoEncontradoException("Cliente não encontrado!");
    }

    @Transactional
    public HashMap<String, Object> editarTitular(String cpf, TitularEditRecordForm formEdit) {
        // Busca o titular na base
        Optional<TitularEntity> titular = titularRepository.findByDocumentos_Cpf(cpf);

        // Cria um HashMap para armazenar as alterações
        HashMap<String, Object> alteracoesMap = new HashMap<>();

        // Verifica se o titular existe
        titular.ifPresentOrElse(
                titularValue -> {
                    // Verifica se existem alterações
                    alteracoesMap
                            .putAll(exitemAlteracoesTitular(formEdit, titularValue, new HashMap<String, Object>()));
                    // Se existirem alterações, atualiza o titular
                    if (!alteracoesMap.isEmpty()) {
                        titularValue.getDadosPessoais().setSobrenome(formEdit.sobrenome());
                        titularValue.getDadosPessoais().setEstadoCivil(formEdit.estadoCivil().getDescricao());
                        titularValue.getDadosPessoais().setProfissao(formEdit.profissao());
                        titularValue.getPreferencias()
                                .setPreferenciaClasse(formEdit.preferenciaClasse().getDescricao());
                        titularValue.getPreferencias()
                                .setPreferenciaAssento(formEdit.preferenciaAssento().getDescricao());
                        titularValue.getPreferencias()
                                .setPreferenciaRefeicao(formEdit.preferenciaRefeicao().getDescricao());
                        titularValue.getDocumentos().setPassaporte(formEdit.passaporte());
                        titularValue.getDocumentos().setDataVencimentoPassaporte(formEdit.dataVencimentoPassaporte());
                        titularValue.getContatos().setEmail(formEdit.email());
                        titularValue.getContatos().setCelular(formEdit.celular());
                        titularValue.getEndereco().setLogradouro(formEdit.logradouro());
                        titularValue.getEndereco().setNumero(formEdit.numero());
                        titularValue.getEndereco().setComplemento(formEdit.complemento());
                        titularValue.getEndereco().setCidade(formEdit.cidade());
                        titularValue.getEndereco().setUf(formEdit.uf().getDescricao());
                        titularValue.getEndereco().setCep(formEdit.cep());
                        titularValue.getEndereco().setPais(formEdit.pais());
                        titularRepository.save(titularValue);
                    } else
                        throw new ClienteJaCadastradoException("Não há alterações!");
                },
                () -> {
                    throw new ClienteNaoEncontradoException("Titular não encontrado!");
                });

        // Retorna o mapa de alterações
        return alteracoesMap;
    }

    @Transactional
    public HashMap<String, Object> editarDependente(String cpfDependente, DependenteEditRecordForm formEdit) {
        // Busca o dependente na base
        Optional<DependenteEntity> dependente = dependenteRepository.findByDocumentos_Cpf(cpfDependente);
        
        // Cria um HashMap para armazenar as alterações
        HashMap<String, Object> alteracoesMap = new HashMap<>();
        
        // Verifica se o dependente existe
        dependente.ifPresentOrElse(dependenteValue -> {

            // Verifica se existem alterações
            alteracoesMap.putAll(existemAlteracoesDependente(formEdit, dependenteValue, new HashMap<String, Object>()));

            // Se existirem alterações, atualiza o dependente e o titular
            if (!alteracoesMap.isEmpty()) {
                dependenteValue.getDadosPessoais().setPrimeiroNome(formEdit.primeiroNome());
                dependenteValue.getDadosPessoais().setNomeDoMeio(formEdit.nomeDoMeio());
                dependenteValue.getDadosPessoais().setSobrenome(formEdit.sobrenome());
                dependenteValue.getDadosPessoais().setEstadoCivil(formEdit.estadoCivil().getDescricao());
                dependenteValue.getDadosPessoais().setProfissao(formEdit.profissao());
                dependenteValue.getDocumentos().setPassaporte(formEdit.passaporte());
                dependenteValue.getDocumentos().setDataVencimentoPassaporte(formEdit.dataVencimentoPassaporte());
                dependenteRepository.save(dependenteValue);

                // Altera dados no titular
                TitularEntity titular = titularRepository.findByDocumentos_Cpf(dependenteValue.getParentId()).get();
                titular.getDependentes().forEach(dependenteEntity -> {
                    if (dependenteEntity.getDocumentos().getCpf().equals(cpfDependente)) {
                        dependenteEntity.getDadosPessoais().setPrimeiroNome(formEdit.primeiroNome());
                        dependenteEntity.getDadosPessoais().setNomeDoMeio(formEdit.nomeDoMeio());
                        dependenteEntity.getDadosPessoais().setSobrenome(formEdit.sobrenome());
                        dependenteEntity.getDadosPessoais().setEstadoCivil(formEdit.estadoCivil().getDescricao());
                        dependenteEntity.getDadosPessoais().setProfissao(formEdit.profissao());
                        dependenteEntity.getDocumentos().setPassaporte(formEdit.passaporte());
                        dependenteEntity.getDocumentos()
                                .setDataVencimentoPassaporte(formEdit.dataVencimentoPassaporte());
                    }
                });
                titularRepository.save(titular);

            } else
            throw new DependenteException("Não há alterações!");
            
        },() -> {
            throw new DependenteException("Não há alterações!");
        });

        return alteracoesMap;
    }

    public HashMap<String, Object> exitemAlteracoesTitular(TitularEditRecordForm form, TitularEntity titular,
            HashMap<String, Object> alteracoesMap) {
        return ClienteUtils.comparaFormComEntity(form, titular, alteracoesMap);
    }

    public HashMap<String, Object> existemAlteracoesDependente(DependenteEditRecordForm form, DependenteEntity dadosDaBase,
            HashMap<String, Object> formAlteracoes) {
        return ClienteUtils.comparaFormComEntity(form, dadosDaBase, formAlteracoes);
    }

    @Transactional
    @CacheEvict(value = "cliente", key = "#cpf")
    public Boolean removerCliente(String cpf) {
        Boolean removido = Boolean.FALSE;
        // Verifica se cliente existe
        if(existeCliente(cpf)){
            // Verifica se é titular ou dependente
            Optional<TitularEntity> titular = titularRepository.findByDocumentos_Cpf(cpf);
            Optional<DependenteEntity> dependente = dependenteRepository.findByDocumentos_Cpf(cpf);

            if(titular.isPresent()){
                // Remove todos os dependentes vinculados ao titular
                dependenteRepository.deleteAllByParentId(cpf);
                // Remove o titular
                titularRepository.delete(titular.get());
                removido = Boolean.TRUE;
            }

            if(dependente.isPresent()){
                // Remove o dependente da collection
                dependenteRepository.delete(dependente.get());
                // Remove o dependente do titular
                TitularEntity titularMongoEntity = titularRepository.findByDocumentos_Cpf(dependente.get().getParentId()).get();
                titularMongoEntity.removeDependente(cpf);
                // Atualiza o titular
                titularRepository.save(titularMongoEntity);
                removido = Boolean.TRUE;
            }

        }else
            throw new ClienteNaoEncontradoException("Cliente não encontrado!");

        return removido;
    }

    @Transactional
    public DependenteRecordDTO incluirDependente(DependenteRecordForm form) {
        // Verificar se o titular existe
        Optional<TitularEntity> titular = titularRepository.findByDocumentos_Cpf(form.parent_id());
        if (titular.isPresent()) {
            // Verificar se o dependente já existe
            Optional<DependenteEntity> dependente = dependenteRepository.findByDocumentos_Cpf(form.cpf());
            if (dependente.isPresent())
                throw new DependenteException("Dependente já cadastrado!");
            else {
                // A variável parent_id é o cpf do titular que vem no form
                DependenteEntity dependenteEntity = ClienteUtils.dependenteFormToEntity(form);
                // Precisa cadastrar o dependente no TitularEntity
                titular.get().getDependentes().add(dependenteEntity);
                titularRepository.save(titular.get());
                // Precisa salvar o dependente no DependenteEntity
                dependenteRepository.save(dependenteEntity);
                return updateCacheAndReturnDto(dependenteEntity);
            }
        } else
            throw new ClienteNaoEncontradoException("Titular não encontrado!");
    }

    @CachePut(value = "cliente", key = "#dependenteEntity.documentos.cpf")
    public DependenteRecordDTO updateCacheAndReturnDto(DependenteEntity dependenteEntity) {
        return ClienteUtils.dependenteEntityToDto(dependenteEntity);
    }

}
