package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistema;
import com.davi.specbox.model.Versao;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class ListarVersoes implements Comando {
    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public ListarVersoes(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Listar versões";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();
        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.");
            return;
        }

        Sistema sistema = leitor.escolherDaLista("Escolha o sistema na lista abaixo", sistemaList, Sistema::getNome);
        List<Versao> versoes = sistema.getVersoes();
        System.out.println("======== Listagem de Versões =========");
        for (Versao versao : versoes) {
            System.out.println("Versão: " + versao.getVersao());
        }
    }
}
