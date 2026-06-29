package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistema;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class ExcluirSistema implements Comando {
    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public ExcluirSistema(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Excluir um sistema";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();

        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.\nCadastre um primeiro.");
            return;
        }
        System.out.println("======== Exclusão de Sistema =========");
        Sistema sistemaEscolhido = leitor.escolherDaLista("Escolha um sistema na lista abaixo:", sistemaList, Sistema::getNome);
        System.out.println("=====================================");
        boolean confirmar = leitor.confirmar("Deseja realmente excluir o sistema\n\"" + sistemaEscolhido.getNome() + "\"?");
        if (confirmar) {
            service.excluirSistema(sistemaList, sistemaEscolhido.getNome());
            System.out.println("Sistema \"" + sistemaEscolhido.getNome() + "\" excluído com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

}
