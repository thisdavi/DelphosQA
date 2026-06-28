package com.davi.specbox.model;

public enum Frequencia {
    SEMPRE("Sempre"),
    AS_VEZES("Às vezes"),
    RARAMENTE("Raramente"),
    UMA_VEZ("Uma vez");

    private final String descricao;

    Frequencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
