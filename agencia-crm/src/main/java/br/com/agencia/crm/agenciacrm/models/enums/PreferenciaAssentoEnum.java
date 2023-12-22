package br.com.agencia.crm.agenciacrm.models.enums;

public enum PreferenciaAssentoEnum {
    JANELA("Janela"),
    CORREDOR("Corredor"),
    MEIO("Meio");

    private String descricao;

    PreferenciaAssentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PreferenciaAssentoEnum fromString(String preferenciaAssento) {
        for (PreferenciaAssentoEnum preferenciaAssentoEnum : PreferenciaAssentoEnum.values()) {
            if (preferenciaAssentoEnum.getDescricao().equalsIgnoreCase(preferenciaAssento)) {
                return preferenciaAssentoEnum;
            }
        }
        throw new IllegalArgumentException("No enum constant " + preferenciaAssento);
    }
}
