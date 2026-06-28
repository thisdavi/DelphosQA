package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistema;
import com.davi.specbox.model.Versao;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class NovaVersao implements Comando {

    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public NovaVersao(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Registrar nova versão";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();
        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.\nCadastre um sistema primeiro.");
            return;
        }

        System.out.println("======== Registro de Versão =========");
        Sistema sistemaEscolhido = leitor.escolherDaLista(" Selecione um sistema da lista abaixo", sistemaList, Sistema::getNome);
        System.out.println("Sistema: " + sistemaEscolhido.getNome());
        String nomeVersao = leitor.lerTexto("Versão: ", true);

        List<Versao> versoes = sistemaEscolhido.getVersoes();

        boolean jaExiste = versoes.stream()
                .anyMatch(v -> v.getVersao().equalsIgnoreCase(nomeVersao));

        if (jaExiste) {
            System.out.println("Versão " + "\"" + nomeVersao + "\" já cadastrada anteriormente.");
            return;
        }

        sistemaEscolhido.adicionarVersao(new Versao(nomeVersao));
        service.salvarSistemas(sistemaList);
        System.out.println("Versão \"" + nomeVersao + "\" cadastrada com sucesso!");
    }
}
