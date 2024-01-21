package br.com.agencia.crm.agenciacrm.utils;

import br.com.agencia.crm.agenciacrm.models.enums.EstadoCivilEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaAssentoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaClasseEnum;
import br.com.agencia.crm.agenciacrm.models.enums.PreferenciaRefeicaoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.SexoEnum;
import br.com.agencia.crm.agenciacrm.models.enums.UfEnum;
import br.com.agencia.crm.agenciacrm.models.records.forms.TitularRecordForm;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CadastroClienteTest {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private TitularRecordForm criarClienteFormComCPF(String cpf) {
        return new TitularRecordForm(
            "",
            "João",
            "Carlos",
            "Silva",
            "1990-01-15",
            SexoEnum.MASCULINO,
            EstadoCivilEnum.CASADO,
            "Engenheiro",
            PreferenciaClasseEnum.ECONOMICA,
            PreferenciaAssentoEnum.JANELA,
            PreferenciaRefeicaoEnum.VEGETARIANA,
            cpf,
            "AB12345",
            "2025-08-20",
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
    }

    @Test
    public void valoresCorretosClienteRecordForm() {
        TitularRecordForm clienteForm = criarClienteFormComCPF("335.192.518-25");
        var violations = validator.validate(clienteForm);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void cpfInvalidoClienteRecordForm() {
        TitularRecordForm clienteForm = criarClienteFormComCPF("123.456.789-00");
        var violations = validator.validate(clienteForm);
        assertFalse(violations.isEmpty());
    }
}
