package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.*;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;

public class NovoTeste implements Comando {

    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public NovoTeste(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Registrar novo teste";
    }

    @Override
    public void executar() {
        List<Sistema> sistemaList = service.carregarSistemas();

        if (sistemaList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda.\nCadastre um sistema primeiro.");
            return;
        }
        System.out.println("======== Registro de Teste ==========");
        Sistema sistemaEscolhido = leitor.escolherDaLista(" Selecione um sistema da lista abaixo", sistemaList, Sistema::getNome);
        List<Versao> versaoDisponiveis = sistemaEscolhido.getVersoes();

        if (versaoDisponiveis.isEmpty()) {
            System.out.println("Nenhuma versão cadastrada ainda.\nCadastre uma primeiro.");
            return;
        }
        System.out.println("=====================================");
        Versao versaoEscolhida = leitor.escolherDaLista(" Selecione uma versão da lista abaixo", versaoDisponiveis, Versao::getVersao);

        System.out.println("=====================================");
        System.out.println("Informações do Teste");
        System.out.println("Versão " + versaoEscolhida.getVersao());
        System.out.println("=====================================");
        String titulo = leitor.lerTexto("Título:", true);
        Teste teste = new Teste(titulo);

        System.out.println("=====================================");
        teste.setProcedimento(leitor.lerTexto("Procedimento:", false));
        System.out.println("=====================================");
        teste.setRetorno(leitor.lerTexto("Retorno:", false));
        System.out.println("=====================================");
        teste.setResultado(leitor.lerEnum("Resultado", Resultado.class, Resultado::getDescricao));
        System.out.println("=====================================");
        teste.setPrioridade(leitor.lerEnum("Prioridade:", Prioridade.class, Prioridade::getDescricao));
        System.out.println("=====================================");
        teste.setFrequencia(leitor.lerEnum("Frequência:", Frequencia.class, Frequencia::getDescricao));
        System.out.println("=====================================");
        teste.setAmbiente(leitor.lerEnum("Ambiente:", Ambiente.class, Ambiente::getDescricao ));
        System.out.println("=====================================");
        teste.setResponsavel(leitor.lerTexto("Responsável:", false));
        System.out.println("=====================================");
        teste.setObs(leitor.lerTexto("Observações:", false));

        versaoEscolhida.adicionarTeste(teste);
        service.salvarSistemas(sistemaList);

        System.out.println("Novo teste registrado na versão " + versaoEscolhida.getVersao());
    }
}

