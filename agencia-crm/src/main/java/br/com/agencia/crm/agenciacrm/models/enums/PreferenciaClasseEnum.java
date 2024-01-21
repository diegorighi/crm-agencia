package br.com.agencia.crm.agenciacrm.models.enums;

public enum PreferenciaClasseEnum {
    ECONOMICA("Econômica"),
    PREMIUM("Premium"),
    EXECUTIVA("Executiva"),
    PRIMEIRA_CLASSE("Primeira Classe");

    private String descricao;

    PreferenciaClasseEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PreferenciaClasseEnum fromString(String preferenciaClasse) {
        for (PreferenciaClasseEnum preferencia : PreferenciaClasseEnum.values()) {
            if (preferencia.getDescricao().equalsIgnoreCase(preferenciaClasse)) {
                return preferencia;
            }
        }
        throw new IllegalArgumentException("No enum constant " + preferenciaClasse);
    }
}
