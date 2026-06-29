package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.*;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class EditarTeste implements Comando {
    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public EditarTeste(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Editar teste existente";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();
        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.");
            return;
        }

        System.out.println("========= Edição de Teste ==========");
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
        Teste testeEscolhido = leitor.escolherDaLista("Selecione o teste que deseja editar:", testes, Teste::getTitulo);

        boolean editando = true;
        while (editando) {
            System.out.println("========== Edição de Teste ===========");
            System.out.println(" 1. Título:       " + testeEscolhido.getTitulo());
            System.out.println(" 2. Procedimento: " + (testeEscolhido.getProcedimento() == null || testeEscolhido.getProcedimento().isBlank() ? "-" : testeEscolhido.getProcedimento()));
            System.out.println(" 3. Retorno:      " + (testeEscolhido.getRetorno() == null || testeEscolhido.getRetorno().isBlank() ? "-" : testeEscolhido.getRetorno()));
            System.out.println(" 4. Resultado:    " + (testeEscolhido.getResultado() != null ? testeEscolhido.getResultado().getDescricao() : "-"));
            System.out.println(" 5. Prioridade:   " + (testeEscolhido.getPrioridade() != null ? testeEscolhido.getPrioridade().getDescricao() : "-"));
            System.out.println(" 6. Frequência:   " + (testeEscolhido.getFrequencia() != null ? testeEscolhido.getFrequencia().getDescricao() : "-"));
            System.out.println(" 7. Ambiente:     " + (testeEscolhido.getAmbiente() != null ? testeEscolhido.getAmbiente().getDescricao() : "-"));
            System.out.println(" 8. Responsável:  " + (testeEscolhido.getResponsavel() == null || testeEscolhido.getResponsavel().isBlank() ? "-" : testeEscolhido.getResponsavel()));
            System.out.println(" 9. Observações:  " + (testeEscolhido.getObs() == null || testeEscolhido.getObs().isBlank() ? "-" : testeEscolhido.getObs()));
            System.out.println(" 0. Salvar e Sair");
            System.out.println("=====================================");

            int opcao = leitor.lerInteiro("> ", 0, 9);
            System.out.println("=====================================");
            switch (opcao) {
                case 1 -> testeEscolhido.setTitulo(leitor.lerTexto("Novo Título:", true));
                case 2 -> testeEscolhido.setProcedimento(leitor.lerTexto("Novo Procedimento:", false));
                case 3 -> testeEscolhido.setRetorno(leitor.lerTexto("Novo Retorno:", false));
                case 4 -> testeEscolhido.setResultado(leitor.lerEnum("Novo Resultado:", Resultado.class, Resultado::getDescricao));
                case 5 -> testeEscolhido.setPrioridade(leitor.lerEnum("Nova Prioridade:", Prioridade.class, Prioridade::getDescricao));
                case 6 -> testeEscolhido.setFrequencia(leitor.lerEnum("Nova Frequência:", Frequencia.class, Frequencia::getDescricao));
                case 7 -> testeEscolhido.setAmbiente(leitor.lerEnum("Novo Ambiente:", Ambiente.class, Ambiente::getDescricao));
                case 8 -> testeEscolhido.setResponsavel(leitor.lerTexto("Novo Responsável:", false));
                case 9 -> testeEscolhido.setObs(leitor.lerTexto("Novas Observações:", false));
                case 0 -> {
                    testeEscolhido.atualizarDataEdicao();
                    service.salvarSistemas(sistemaList);
                    System.out.println("Alterações salvas.");
                    editando = false;
                }
            }
        }
    }
}
