package com.davi.specbox.model;

public enum Resultado {
    //🟢
    //🔴
    //🟠
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    PARCIAL("Parcial");

    private final String descricao;

    Resultado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
