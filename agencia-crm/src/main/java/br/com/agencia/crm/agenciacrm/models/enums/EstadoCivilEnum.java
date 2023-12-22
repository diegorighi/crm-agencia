package br.com.agencia.crm.agenciacrm.models.enums;

public enum EstadoCivilEnum {
    SOLTEIRO("Solteiro"),
    CASADO("Casado"),
    DIVORCIADO("Divorciado"),
    VIUVO("Viúvo"),
    SEPARADO("Separado"),
    UNIAO_ESTAVEL("União Estável");

    private String estadoCivil;

    EstadoCivilEnum(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public static EstadoCivilEnum fromString(String estadoCivilStr) {
        for (EstadoCivilEnum estadoCivilAux : EstadoCivilEnum.values()) {
            if (estadoCivilAux.name().equalsIgnoreCase(estadoCivilStr)) {
                return estadoCivilAux;
            }
        }
        throw new IllegalArgumentException("No enum constant " + estadoCivilStr);
    }

    public String getDescricao() {
        return estadoCivil;
    }
}
