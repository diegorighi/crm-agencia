package br.com.agencia.crm.agenciacrm.models.enums;

    public enum PreferenciaRefeicaoEnum {
        SEM_PREFERENCIA("Sem preferência"),
        VEGETARIANA("Vegetariana"),
        VEGANA("Vegana"),
        SEM_GLUTEN("Sem glúten"),
        SEM_LACTOSE("Sem lactose"),
        SEM_ACUCAR("Sem açúcar"),
        ADVENTISTA("Adventista"),
        SEM_FRUTOS_DO_MAR("Sem frutos do mar"),
        LOW_CARB("Low carb"),
        PALEO("Paleo"),
        KETO("Keto"),
        SEM_SAL("Sem sal"),
        SEM_TEMPERO("Sem tempero"),
        SEM_ACENTO("Sem acento"),
        SEM_PIMENTA("Sem pimenta"),
        SEM_CEBOLA("Sem cebola"),
        SEM_ALHO("Sem alho"),
        SEM_CAFEINA("Sem cafeína"),
        SEM_ALCOOL("Sem álcool");


        private String descricao;

        PreferenciaRefeicaoEnum(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public static PreferenciaRefeicaoEnum fromString(String preferenciaRefeicao) {
            for (PreferenciaRefeicaoEnum preferencia : PreferenciaRefeicaoEnum.values()) {
                if (preferencia.getDescricao().equalsIgnoreCase(preferenciaRefeicao)) {
                    return preferencia;
                }
            }
            throw new IllegalArgumentException("No enum constant " + preferenciaRefeicao);
        }
    }
