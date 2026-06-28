package com.davi.specbox.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Versao {
    private String versao;
    private List<Teste> listaTestes;

    public Versao(String versao) {
        this.versao = versao;
        listaTestes = new ArrayList<>();
    }

    public void adicionarTeste(Teste teste) {
        if (teste != null) {
            listaTestes.add(teste);
        }
    }

    public void removerTeste(String id) {
        listaTestes.removeIf(teste -> teste.getId().equals(id));
    }

    public Optional<Teste> buscarTeste(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido");
        }
        return listaTestes.
                stream().
                filter(teste -> Objects.equals(teste.getId(), id)).
                findFirst();
    }

    @Override
    public String toString() {
        return versao;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public List<Teste> getTestes() {
        return listaTestes;
    }

    public void setTestes(List<Teste> testes) {
        this.listaTestes = testes;
    }
}
