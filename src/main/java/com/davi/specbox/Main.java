package com.davi.specbox;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.service.SpecBoxService;
import com.davi.specbox.cli.*;

public class Main {
    public static void main(String[] args) {
        SpecBoxService service = new SpecBoxService();
        LeitorConsole leitor = new LeitorConsole();
        MenuPrincipal menu = new MenuPrincipal(leitor);

        menu.registrar(new NovoSistema(service, leitor));
        menu.registrar(new NovaVersao(service, leitor));
        menu.registrar(new NovoTeste(service, leitor));

        menu.rodar();
    }
}