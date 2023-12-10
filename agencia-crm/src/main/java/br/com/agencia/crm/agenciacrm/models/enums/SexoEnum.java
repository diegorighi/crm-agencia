package br.com.agencia.crm.agenciacrm.models.enums;

public enum SexoEnum {
    MASCULINO("Masculino"),
    FEMININO("Feminino");

    private String sexo;

    SexoEnum(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }
}
