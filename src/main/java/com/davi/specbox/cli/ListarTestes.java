package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistema;
import com.davi.specbox.model.Teste;
import com.davi.specbox.model.Versao;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class ListarTestes implements Comando {
    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public ListarTestes(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Visualizar testes cadastrados";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();
        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.");
            return;
        }

        System.out.println("======== Listagem de Testes =========");
        System.out.println("Selecione o tipo de listagem: ");
        System.out.println("1. Listar todos os testes");
        System.out.println("2. Listar apenas um teste");
        System.out.println("=====================================");
        int opcao = leitor.lerInteiro("> ", 1, 2);

        switch (opcao) {
            case 1 -> listagemGeral(sistemaList);
            case 2 -> listagemUnica(sistemaList);
        }
    }

    private void listagemGeral(List<Sistema> sistemaList) {
        Sistema sistemaEscolhido = leitor.escolherDaLista("Selecione um sistema da lista abaixo:", sistemaList, Sistema::getNome);
        List<Versao> versaoDisponiveis = sistemaEscolhido.getVersoes();

        if (versaoDisponiveis.isEmpty()) {
            System.out.println("Nenhuma versão cadastrada para este sistema.");
            return;
        }
        System.out.println("=====================================");
        Versao versaoEscolhida = leitor.escolherDaLista("Selecione uma versão da lista abaixo:", versaoDisponiveis, Versao::getVersao);
        System.out.println("=====================================");
        List<Teste> testes = versaoEscolhida.getTestes();

        if (testes.isEmpty()) {
            System.out.println("Nenhum teste registrado para esta versão.");
            return;
        }
        for (Teste teste : testes) {
           exibirDetalhes(teste);
        }
    }

    private void listagemUnica(List<Sistema> sistemaList) {
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

        while (true) {
            System.out.println("=====================================");
            System.out.println("Testes da versão " + versaoEscolhida.getVersao() + ":");
            for (int i = 0; i < testes.size(); i++) {
                Teste t = testes.get(i);
                String prioridade = t.getPrioridade() != null ? t.getPrioridade().getDescricao() : "Pendente";
                System.out.println(" " + (i + 1) + ". [" + prioridade + "] " + t.getTitulo());
            }
            System.out.println(" " + (testes.size() + 1) + ". Voltar");
            System.out.println("=====================================");

            int escolha = leitor.lerInteiro("Selecione um teste:", 1, testes.size() + 1);
            if (escolha == testes.size() + 1) {
                break;
            }

            Teste teste = testes.get(escolha - 1);
            exibirDetalhes(teste);
            leitor.lerTexto("Pressione Enter para continuar...", false);
        }
    }

    private void exibirDetalhes(Teste teste){
        System.out.println("\n================ Detalhes do Teste ================");
        System.out.println("ID:          " + teste.getId());
        System.out.println("Título:      " + teste.getTitulo());
        System.out.println("Procedimento:" + (teste.getProcedimento() == null || teste.getProcedimento().isBlank() ? " -" : teste.getProcedimento()));
        System.out.println("Retorno:     " + (teste.getRetorno() == null || teste.getRetorno().isBlank() ? " -" : teste.getRetorno()));
        System.out.println("Resultado:   " + (teste.getResultado() != null ? teste.getResultado().getDescricao() : "-"));
        System.out.println("Prioridade:  " + (teste.getPrioridade() != null ? teste.getPrioridade().getDescricao() : "-"));
        System.out.println("Frequência:  " + (teste.getFrequencia() != null ? teste.getFrequencia().getDescricao() : "-"));
        System.out.println("Ambiente:    " + (teste.getAmbiente() != null ? teste.getAmbiente().getDescricao() : "-"));
        System.out.println("Responsável: " + (teste.getResponsavel() == null || teste.getResponsavel().isBlank() ? " -" : teste.getResponsavel()));
        System.out.println("Observações: " + (teste.getObs() == null || teste.getObs().isBlank() ? " -" : teste.getObs()));
        System.out.println("Criado em:   " + teste.getDataCriacao());
        System.out.println("Editado em:  " + (teste.getDataEdicao() != null ? teste.getDataEdicao() : "-"));
        System.out.println("===================================================\n");
    }
}