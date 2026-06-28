package com.davi.specbox.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sistema {
    private String nome;
    private List<Versao> versoes;

    public Sistema(String nome) {
        this.nome = nome;
        versoes = new ArrayList<>();
    }

    public void adicionarVersao(Versao versao) {
        if (versao != null) {
            versoes.add(versao);
        }
    }

    public void removerVersao(String nomeVersao) {
        versoes.removeIf(versao -> versao.getVersao().equalsIgnoreCase(nomeVersao));
    }

    public Optional<Versao> buscarVersao(String versao) {
        if (versao == null || versao.isBlank()) {
            throw new IllegalArgumentException("Versão inválida");
        }
        return versoes.
                stream().
                filter(v -> v.getVersao().equalsIgnoreCase(versao)).
                findFirst();
    }

    @Override
    public String toString() {
        return nome;
    }

    public List<Versao> getVersoes() {
        return versoes;
    }

    public void setVersoes(List<Versao> versoes) {
        this.versoes = versoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
