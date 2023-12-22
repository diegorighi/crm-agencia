package br.com.agencia.crm.agenciacrm.models.enums;

public enum UfEnum {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins"),
    
    EX("Exterior");

    private String uf;

    UfEnum(String uf) {
        this.uf = uf;
    }

    public static UfEnum fromString(String ufStr) {
        for (UfEnum uf : UfEnum.values()) {
            if (uf.getDescricao().equalsIgnoreCase(ufStr)) {
                return uf;
            }
        }
        throw new IllegalArgumentException("No enum constant " + ufStr);
    }

    public String getDescricao() {
        return uf;
    }
}
