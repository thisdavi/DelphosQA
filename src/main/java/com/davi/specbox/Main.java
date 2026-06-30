package com.davi.specbox;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.service.SpecBoxService;
import com.davi.specbox.cli.*;

public class Main {
    public static void main(String[] args) {
        SpecBoxService service = new SpecBoxService();
        LeitorConsole leitor = new LeitorConsole();
        MenuPrincipal menu = new MenuPrincipal(leitor);

        SubMenu menuSistemas = new SubMenu("Sistemas", leitor);
        menuSistemas.registrar(new NovoSistema(service, leitor));
        menuSistemas.registrar(new ExcluirSistema(service, leitor));

        SubMenu menuVersoes = new SubMenu("Versões", leitor);
        menuVersoes.registrar(new NovaVersao(service, leitor));
        menuVersoes.registrar(new ListarVersoes(service, leitor));
        menuVersoes.registrar(new ExcluirVersao(service, leitor));

        SubMenu menuTestes = new SubMenu("Teste", leitor);
        menuTestes.registrar(new NovoTeste(service, leitor));
        menuTestes.registrar(new ListarTestes(service, leitor));
        menuTestes.registrar(new EditarTeste(service, leitor));
        menuTestes.registrar(new ExcluirTeste(service, leitor));

        menu.registrar(menuSistemas);
        menu.registrar(menuVersoes);
        menu.registrar(menuTestes);

        menu.rodar();
    }
}