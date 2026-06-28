package com.davi.specbox.model;

public enum Prioridade {
    CRITICA("Crítica"),
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");
    private final String descricao;

    Prioridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
