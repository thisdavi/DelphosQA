package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;

import java.util.LinkedHashMap;
import java.util.Map;

public class SubMenu implements Comando {

    private final String descricao;
    private final LeitorConsole leitor;
    private final Map<Integer, Comando> comandos = new LinkedHashMap<>();

    public SubMenu(String descricao, LeitorConsole leitor) {
        this.descricao = descricao;
        this.leitor = leitor;
    }

    public void registrar(Comando comando) {
        int proximoId = comandos.size() + 1;
        comandos.put(proximoId, comando);
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public void executar() {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n============== " + descricao.toUpperCase() + " ==============");
            comandos.forEach((id, cmd) ->
                    System.out.println(id + ". " + cmd.getDescricao())
            );
            System.out.println("0. Voltar");
            System.out.println("=====================================");
            int escolha = leitor.lerInteiro("> ", 0, comandos.size());

            if (escolha == 0) {
                rodando = false;
            } else {
                Comando cmd = comandos.get(escolha);
                if (cmd == null) {
                    System.out.println("Opção inválida.");
                } else {
                    cmd.executar();
                }
            }
        }
    }
}

