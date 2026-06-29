package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistema;
import com.davi.specbox.model.Teste;
import com.davi.specbox.model.Versao;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class ExcluirTeste implements Comando {
    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public ExcluirTeste(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Excluir um teste";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();
        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.");
            return;
        }

        System.out.println("========= Exclusão de Teste =========");
        Sistema sistemaEscolhido = leitor.escolherDaLista("Selecione um sistema da lista abaixo:", sistemaList, Sistema::getNome);
        List<Versao> versaoDisponiveis = sistemaEscolhido.getVersoes();

        if (versaoDisponiveis.isEmpty()) {
            System.out.println("Nenhuma versão cadastrada para este sistema.");
            return;
        }

        System.out.println("=====================================");
        Versao versaoEscolhida = leitor.escolherDaLista("Selecione uma versão da lista abaixo:", versaoDisponiveis, Versao::getVersao);
        List<Teste> testes = versaoEscolhida.getTestes();

        if (testes.isEmpty()) {
            System.out.println("Nenhum teste registrado para esta versão.");
            return;
        }

        System.out.println("=====================================");
        Teste testeEscolhido = leitor.escolherDaLista("Selecione o teste que deseja excluir:", testes, Teste::getTitulo);
        System.out.println("=====================================");
        boolean confirmar = leitor.confirmar("Deseja realmente excluir o teste\n\"" + testeEscolhido.getTitulo() + "\"?");
        if (confirmar) {
                service.excluirTeste(sistemaList, sistemaEscolhido.getNome(), versaoEscolhida.getVersao(), testeEscolhido.getId());
                System.out.println("Teste excluído com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
