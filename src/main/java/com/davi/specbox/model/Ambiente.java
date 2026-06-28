package com.davi.specbox.model;

public enum Ambiente {
    PRODUCAO("Produção"),
    HOMOLOGACAO("Homologação"),
    DESENVOLVIMENTO("Desenvolvimento");
    private final String descricao;

    Ambiente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
