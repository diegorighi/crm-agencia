package br.com.agencia.crm.agenciacrm.models.enums;

public enum EstadoCivilEnum {
    SOLTEIRO("Solteiro(a)"),
    CASADO("Casado(a)"),
    DIVORCIADO("Divorciado(a)"),
    VIUVO("Viúvo(a)"),
    SEPARADO("Separado(a)"),
    UNIAO_ESTAVEL("União Estável");

    private String estadoCivil;

    EstadoCivilEnum(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }
}
