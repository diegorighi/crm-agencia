package br.com.agencia.crm.agenciacrm.models.enums;

public enum SexoEnum {
    MASCULINO("Masculino"),
    FEMININO("Feminino");

    private String sexo;

    SexoEnum(String sexo) {
        this.sexo = sexo;
    }

    public static SexoEnum fromString(String sexoStr) {
        for (SexoEnum sexo : SexoEnum.values()) {
            if (sexo.name().equalsIgnoreCase(sexoStr)) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("No enum constant " + sexoStr);
    }

    public String getDescricao() {
        return sexo;
    }
}
