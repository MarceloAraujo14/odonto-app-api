package br.com.odontoapp.schedule.core.enums;

public enum ServiceType {
    LIMPEZA(1),
    CLAREAMENTO(4),
    MANUTENCAO(1),
    COLOCACAO_APARELHO(2),
    ORCAMENTO(1);

    ServiceType(int duration) {
    }

}
