package com.davi.specbox.io;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class LeitorConsole {
    private final Scanner entrada = new Scanner(System.in);

    public String lerTexto(String pergunta, boolean obrigatorio) {
        while (true) {
            System.out.print(pergunta + " ");
            String valor = entrada.nextLine().trim();

            if (obrigatorio && valor.isBlank()) {
                System.out.println("Esse campo é obrigatório. Tente novamente");
                continue;
            }
            return valor;
        }
    }

    public int lerInteiro(String pergunta, int min, int max) {
        while (true) {
            System.out.print(pergunta + " ");
            String numero = entrada.nextLine().trim();

            try {
                int valor = Integer.parseInt(numero);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Digite um número entre " + min + " e " + max);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números");
            }
        }
    }

    public <T extends Enum<T>> T lerEnum(String pergunta, Class<T> enumClass) {
        return lerEnum(pergunta, enumClass, Enum::toString);
    }

    public <T extends Enum<T>> T lerEnum(String pergunta, Class<T> enumClass, Function<T, String> comoExibir) {
        T[] valores = enumClass.getEnumConstants();

        System.out.println(pergunta);
        for (int i = 0; i < valores.length; i++) {
            System.out.println(" " + (i + 1) + ". " + comoExibir.apply(valores[i]));
        }
        int escolha = lerInteiro("Opção:", 1, valores.length);
        return valores[escolha - 1];
    }

    public <T> T escolherDaLista(String pergunta, List<T> lista, Function<T, String> comoExibir) {
        System.out.println(pergunta);
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + comoExibir.apply(lista.get(i)));
        }
        System.out.println("=====================================");
        int escolha = lerInteiro("> ", 1, lista.size());
        return lista.get(escolha - 1);
    }

    public boolean confirmar(String pergunta) {
        while (true) {
            System.out.println(pergunta +"\n's' para sim / 'n' para não");
            System.out.println("=====================================");
            System.out.print("> ");
            String resposta = entrada.nextLine().trim().toLowerCase();
            System.out.println("=====================================");
            if (resposta.equals("s")) return true;
            if (resposta.equals("n")) return false;

            System.out.println("Digite 's' para sim ou 'n' para não");
        }
    }
}
