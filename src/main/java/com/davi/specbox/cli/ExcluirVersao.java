package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistema;
import com.davi.specbox.model.Versao;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class ExcluirVersao implements Comando {

    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public ExcluirVersao(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Excluir uma versão";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();

        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.\nCadastre um primeiro.");
            return;
        }

        Sistema sistemaEscolhido = leitor.escolherDaLista("Selecione um sistema da lista abaixo:", sistemaList, Sistema::getNome);
        List<Versao> versaoDisponiveis = sistemaEscolhido.getVersoes();

        System.out.println("=====================================");
        Versao versaoEscolhida = leitor.escolherDaLista("Escolha a versão da lista abaixo: ", versaoDisponiveis, Versao::getVersao);
        System.out.println("=====================================");
        boolean confirmar = leitor.confirmar("Deseja realmente excluir a versão\n\"" + versaoEscolhida.getVersao() + "\"?");
        if (confirmar) {
            service.excluirVersao(sistemaList, sistemaEscolhido.getNome(), versaoEscolhida.getVersao());
            System.out.println("Versão excluída com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
