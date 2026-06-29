package com.davi.specbox.service;

import com.davi.specbox.model.Sistema;
import com.davi.specbox.model.Teste;
import com.davi.specbox.model.Versao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SpecBoxService {
    private static final Path PASTA = Path.of(System.getProperty("user.home"), ".specbox");
    private static final Path ARQUIVO = PASTA.resolve("specbox_testes.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void salvarSistemas(List<Sistema> sistemas) {
        try {
            Files.createDirectories(PASTA);
            try (BufferedWriter escritor = Files.newBufferedWriter(ARQUIVO, UTF_8)) {
                escritor.write(gson.toJson(sistemas));
            }
        } catch (IOException ioException) {
            System.out.println("Erro ao salvar sistema: " + ioException.getMessage());
        }
    }

    public List<Sistema> carregarSistemas() {
        if (Files.exists(ARQUIVO)) {
            try (BufferedReader ler = Files.newBufferedReader(ARQUIVO, UTF_8)) {
                List<Sistema> sistemas = gson.fromJson(ler, new TypeToken<List<Sistema>>() {
                        }.getType()
                );

                return sistemas != null ? sistemas : new ArrayList<>();

            } catch (IOException e) {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }


    public List<Sistema> salvarTeste(List<Sistema> sistemas, String nomeSistema, String versao, Teste
            teste) throws IOException {

        if (nomeSistema == null || nomeSistema.isBlank()) {
            throw new IllegalArgumentException("Sistema inválido");
        }
        if (versao == null || versao.isBlank()) {
            throw new IllegalArgumentException("Versão inválida");
        }

        Sistema sistemaOpt = sistemas.stream()
                .filter(s -> s.getNome().equalsIgnoreCase(nomeSistema))
                .findFirst()
                .orElseGet(() -> {
                    Sistema sistemaNovo = new Sistema(nomeSistema);
                    sistemas.add(sistemaNovo);
                    return sistemaNovo;
                });

        Versao versaoOpt = sistemaOpt.buscarVersao(versao)
                .orElseGet(() -> {
                    Versao versaoNova = new Versao(versao);
                    sistemaOpt.adicionarVersao(versaoNova);
                    return versaoNova;
                });

        Optional<Teste> existe = versaoOpt.buscarTeste(teste.getId());
        if (existe.isPresent()) {
            versaoOpt.removerTeste(teste.getId());
            teste.atualizarDataEdicao();
        }
        versaoOpt.adicionarTeste(teste);
        salvarSistemas(sistemas);
        return sistemas;
    }

    public List<Sistema> excluirTeste(List<Sistema> sistemas, String nomeSistema, String versao, String idTeste) {

        Optional<Sistema> sistemaOpt = sistemas.stream()
                .filter(s -> s.getNome().equalsIgnoreCase(nomeSistema))
                .findFirst();

        if (sistemaOpt.isEmpty()) {
            return sistemas;
        }

        Sistema sistemaEncontrado = sistemaOpt.get();

        Optional<Versao> versaoOpt = sistemaEncontrado.buscarVersao(versao);
        if (versaoOpt.isEmpty()) {
            return sistemas;
        }
        Versao versaoEncontrada = versaoOpt.get();
        versaoEncontrada.removerTeste(idTeste);

        salvarSistemas(sistemas);
        return sistemas;
    }

    public void excluirSistema(List<Sistema> sistemas, String nomeSistema) {
        sistemas.removeIf(s -> s.getNome().equalsIgnoreCase(nomeSistema));
        salvarSistemas(sistemas);
    }

    public void excluirVersao(List<Sistema> sistemas, String nomeSistema, String versao) {
        sistemas.stream()
                .filter(s -> s.getNome().equalsIgnoreCase(nomeSistema))
                .findFirst()
                .ifPresent(sistema -> {
                    sistema.removerVersao(versao);
                    salvarSistemas(sistemas);
                });
    }
}
