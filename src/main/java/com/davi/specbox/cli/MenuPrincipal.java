package com.davi.specbox.cli;


import com.davi.specbox.io.LeitorConsole;

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuPrincipal {
    private final Map<Integer, Comando> comandos = new LinkedHashMap<>();
    private final LeitorConsole leitor;

    public MenuPrincipal(LeitorConsole leitor) {
        this.leitor = leitor;
    }

    private void exibirMenu() {
        System.out.println("============== SpecBox ==============");
        comandos.forEach((id, cmd) -> System.out.println(id + ". " + cmd.getDescricao()));
        System.out.println("0. Sair");
        System.out.println("=====================================");
    }

    public void registrar(Comando comando) {
        int proximoId = comandos.size() + 1;
        comandos.put(proximoId, comando);
    }

    public void rodar() {
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            int escolha = leitor.lerInteiro("> ", 0, comandos.size());

            if (escolha == 0) {
                System.out.println("=====================================");
                System.out.println("Saindo...");
                rodando = false;
                continue;
            }

            Comando comando = comandos.get(escolha);
            if (comando == null) {
                System.out.println("Opção inválida. Selecione uma das opções abaixo.");
                continue;
            }

            comando.executar();
        }
    }
}