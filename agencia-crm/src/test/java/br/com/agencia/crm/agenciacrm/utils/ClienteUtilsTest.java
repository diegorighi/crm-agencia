package br.com.agencia.crm.agenciacrm.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.agencia.crm.agenciacrm.models.entities.TitularEntity;
import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaAssentoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaClasseEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaRefeicaoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;
import br.com.agencia.crm.agenciacrm.models.records.dto.ContatoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DadosPessoaisRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DependenteRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.DocumentosRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.EnderecoRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.PreferenciasRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.dto.TitularRecordDTO;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularRecordForm;

public class ClienteUtilsTest {

    private TitularRecordForm clienteForm;
    private TitularEntity clienteEntity;
    private TitularRecordDTO clienteRecordDTO;

    @BeforeEach
    public void init(){
        clienteForm = new TitularRecordForm(
            "",
            "João",
            "Carlos",
            "Silva",
            "13/04/1988",
            SexoEnum.MASCULINO,
            EstadoCivilEnum.CASADO,
            "Engenheiro",
            PreferenciaClasseEnum.ECONOMICA,
            PreferenciaAssentoEnum.JANELA,
            PreferenciaRefeicaoEnum.VEGETARIANA,
            "335.192.518-25",
            "AB12345",
            "30/08/2028",
            "joao.silva@email.com",
            "11999887766",
            "Rua das Flores",
            123,
            "Apto 101",
            "São Paulo",
            UfEnum.SP,
            "01234-567",
            "Brasil"
        );
        clienteRecordDTO = new TitularRecordDTO(
            new DadosPessoaisRecordDTO("João", "Carlos", "Silva", "13/04/1988", SexoEnum.MASCULINO, EstadoCivilEnum.CASADO, "Engenheiro"),
            new PreferenciasRecordDTO(PreferenciaClasseEnum.ECONOMICA, PreferenciaAssentoEnum.JANELA, PreferenciaRefeicaoEnum.VEGETARIANA),
            new DocumentosRecordDTO("335.192.518-25", "AB12345", "30/08/2028"),
            new ContatoRecordDTO("joao.silva@email.com","11999887766"),
            new EnderecoRecordDTO("Rua das Flores", 123, "Apto 101", "São Paulo", UfEnum.SP, "01234-567", "Brasil"),
            new ArrayList<DependenteRecordDTO>()
        );
        clienteEntity = new TitularEntity(clienteRecordDTO);
        
    }
    
    @Test
    public void testaFormToDTOComValoresCorretos(){
        // Converter o ClienteRecordForm para ClienteRecordDTO
        TitularRecordDTO dto = ClienteUtils.titularFormToDto(clienteForm);

        // Verificar se a conversão está correta
        assertEquals(clienteForm.primeiroNome(), dto.dadosPessoais().primeiroNome());
        assertEquals(clienteForm.nomeDoMeio(), dto.dadosPessoais().nomeDoMeio());
        assertEquals(clienteForm.sobrenome(), dto.dadosPessoais().sobrenome());
        assertEquals(clienteForm.dataNascimento(), dto.dadosPessoais().dataNascimento());
        assertEquals(clienteForm.sexo(), dto.dadosPessoais().sexo());
        assertEquals(clienteForm.estadoCivil(), dto.dadosPessoais().estadoCivil());
        assertEquals(clienteForm.profissao(), dto.dadosPessoais().profissao());
        assertEquals(clienteForm.preferenciaClasse(), dto.preferencias().preferenciaClasse());
        assertEquals(clienteForm.preferenciaAssento(), dto.preferencias().preferenciaAssento());
        assertEquals(clienteForm.preferenciaRefeicao(), dto.preferencias().preferenciaRefeicao());
        assertEquals(clienteForm.cpf(), dto.documentos().cpf());
        assertEquals(clienteForm.passaporte(), dto.documentos().passaporte());
        assertEquals(clienteForm.dataVencimentoPassaporte(), dto.documentos().dataVencimentoPassaporte());
        assertEquals(clienteForm.email(), dto.contato().email());
        assertEquals(clienteForm.celular(), dto.contato().celular());
        assertEquals(clienteForm.logradouro(), dto.endereco().logradouro());
        assertEquals(clienteForm.numero(), dto.endereco().numero());
        assertEquals(clienteForm.complemento(), dto.endereco().complemento());
        assertEquals(clienteForm.cidade(), dto.endereco().cidade());
        assertEquals(clienteForm.uf(), dto.endereco().uf());
        assertEquals(clienteForm.cep(), dto.endereco().cep());
        assertEquals(clienteForm.pais(), dto.endereco().pais());
    }

    @Test
    public void testaDtoToEntityComValoresCorretos(){
        TitularEntity entity = ClienteUtils.titularDtoToEntity(clienteRecordDTO);
        assertEquals(entity.getDadosPessoais().getPrimeiroNome(), clienteRecordDTO.dadosPessoais().primeiroNome());
        assertEquals(entity.getDadosPessoais().getNomeDoMeio(), clienteRecordDTO.dadosPessoais().nomeDoMeio());
        assertEquals(entity.getDadosPessoais().getSobrenome(), clienteRecordDTO.dadosPessoais().sobrenome());
        assertEquals(entity.getDadosPessoais().getDataNascimento(), clienteRecordDTO.dadosPessoais().dataNascimento());
        assertEquals(entity.getDadosPessoais().getSexo(), clienteRecordDTO.dadosPessoais().sexo().getDescricao());
        assertEquals(entity.getDadosPessoais().getEstadoCivil(), clienteRecordDTO.dadosPessoais().estadoCivil().getDescricao());
        assertEquals(entity.getDadosPessoais().getProfissao(), clienteRecordDTO.dadosPessoais().profissao());
        assertEquals(entity.getPreferencias().getPreferenciaClasse(), clienteRecordDTO.preferencias().preferenciaClasse().getDescricao());
        assertEquals(entity.getPreferencias().getPreferenciaAssento(), clienteRecordDTO.preferencias().preferenciaAssento().getDescricao());
        assertEquals(entity.getPreferencias().getPreferenciaRefeicao(), clienteRecordDTO.preferencias().preferenciaRefeicao().getDescricao());
        assertEquals(entity.getDocumentos().getCpf(), clienteRecordDTO.documentos().cpf());
        assertEquals(entity.getDocumentos().getPassaporte(), clienteRecordDTO.documentos().passaporte());
        assertEquals(entity.getDocumentos().getDataVencimentoPassaporte(), clienteRecordDTO.documentos().dataVencimentoPassaporte());
        assertEquals(entity.getContatos().getEmail(), clienteRecordDTO.contato().email());
        assertEquals(entity.getContatos().getCelular(), clienteRecordDTO.contato().celular());
        assertEquals(entity.getEndereco().getLogradouro(), clienteRecordDTO.endereco().logradouro());
        assertEquals(entity.getEndereco().getNumero(), clienteRecordDTO.endereco().numero());
        assertEquals(entity.getEndereco().getComplemento(), clienteRecordDTO.endereco().complemento());
        assertEquals(entity.getEndereco().getCidade(), clienteRecordDTO.endereco().cidade());
        assertEquals(entity.getEndereco().getUf(), clienteRecordDTO.endereco().uf().getDescricao());
        assertEquals(entity.getEndereco().getCep(), clienteRecordDTO.endereco().cep());
        assertEquals(entity.getEndereco().getPais(), clienteRecordDTO.endereco().pais());
    }

    public void testaEntityToDtoComValoresCorretos(){
        TitularRecordDTO dto = ClienteUtils.titularEntityToDto(clienteEntity);
        assertEquals(dto.dadosPessoais().primeiroNome(), clienteEntity.getDadosPessoais().getPrimeiroNome());
        assertEquals(dto.dadosPessoais().nomeDoMeio(), clienteEntity.getDadosPessoais().getNomeDoMeio());
        assertEquals(dto.dadosPessoais().sobrenome(), clienteEntity.getDadosPessoais().getSobrenome());
        assertEquals(dto.dadosPessoais().dataNascimento(), clienteEntity.getDadosPessoais().getDataNascimento());
        assertEquals(dto.dadosPessoais().sexo(), clienteEntity.getDadosPessoais().getSexo());
        assertEquals(dto.dadosPessoais().estadoCivil(), clienteEntity.getDadosPessoais().getEstadoCivil());
        assertEquals(dto.dadosPessoais().profissao(), clienteEntity.getDadosPessoais().getProfissao());
        assertEquals(dto.preferencias().preferenciaClasse(), clienteEntity.getPreferencias().getPreferenciaClasse());
        assertEquals(dto.preferencias().preferenciaAssento(), clienteEntity.getPreferencias().getPreferenciaAssento());
        assertEquals(dto.preferencias().preferenciaRefeicao(), clienteEntity.getPreferencias().getPreferenciaRefeicao());
        assertEquals(dto.documentos().cpf(), clienteEntity.getDocumentos().getCpf());
        assertEquals(dto.documentos().passaporte(), clienteEntity.getDocumentos().getPassaporte());
        assertEquals(dto.documentos().dataVencimentoPassaporte(), clienteEntity.getDocumentos().getDataVencimentoPassaporte());
        assertEquals(dto.contato().email(), clienteEntity.getContatos().getEmail());
        assertEquals(dto.contato().celular(), clienteEntity.getContatos().getCelular());
        assertEquals(dto.endereco().logradouro(), clienteEntity.getEndereco().getLogradouro());
        assertEquals(dto.endereco().numero(), clienteEntity.getEndereco().getNumero());
        assertEquals(dto.endereco().complemento(), clienteEntity.getEndereco().getComplemento());
        assertEquals(dto.endereco().cidade(), clienteEntity.getEndereco().getCidade());
        assertEquals(dto.endereco().uf(), clienteEntity.getEndereco().getUf());
        assertEquals(dto.endereco().cep(), clienteEntity.getEndereco().getCep());
        assertEquals(dto.endereco().pais(), clienteEntity.getEndereco().getPais());
    }
}
