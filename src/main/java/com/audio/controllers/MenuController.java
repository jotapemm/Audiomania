package com.audio.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.audio.until;

/**
 * Interface que define operações básicas de um menu.
 */
interface MenuOperacao {
    void exibir();

    void executar();
}

/**
 * Classe base para criação de menus.
 */
abstract class MenuBase implements MenuOperacao {
    protected final List<String> options;
    protected final String titulo;

    /**
     * Construtor para menus com título e opções.
     *
     * @param titulo Título do menu.
     * @param opcoes Opções do menu.
     */
    protected MenuBase(String titulo, String... opcoes) {
        this.titulo = titulo;
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(opcoes));
        this.options.add("Voltar");
    }

    @Override
    public void exibir() {
        MenuController.createMenu(options, titulo);
    }

    @Override
    public void executar() {
        exibir();
        int choice = MenuController.getUserInput(options);
        processarOpcao(choice);
    }

    /**
     * Processa a opção selecionada pelo usuário.
     *
     * @param choice Opção escolhida.
     */
    protected abstract void processarOpcao(int choice);
}

/**
 * Menu específico para operações de venda.
 */
class VendaMenuOperacao extends MenuBase {
    public VendaMenuOperacao() {
        super("Gestão de Vendas",
                "Nova Venda",
                "Gerenciar Clientes",
                "Histórico de Vendas");
    }

    @Override
    protected void processarOpcao(int choice) {
        switch (choice) {
            case 0 -> MenuController.init();
            case 1 -> VendaController.novaVenda();
            case 2 -> ClienteController.gerenciarClientes();
            case 3 -> VendaController.historicoVendas();
        }
    }
}

/**
 * Controlador geral dos menus da aplicação.
 */
public class MenuController {
    private static final Scanner scanner = new Scanner(System.in);

    private MenuController() {
        // Construtor privado para evitar instanciação
    }

    /**
     * Cria e exibe o menu formatado na tela.
     *
     * @param options Lista de opções do menu.
     * @param message Título do menu.
     */
    public static void createMenu(List<String> options, String message) {
        int maxLength = calcularLarguraMaxima(message, options);
        int larguraTotal = maxLength + 8;
        String linha = "═".repeat(larguraTotal);

        imprimirCabecalho(message, linha, larguraTotal);
        imprimirOpcoes(options, larguraTotal);
        imprimirRodape(linha);
    }

    private static int calcularLarguraMaxima(String message, List<String> options) {
        int maxLength = message.length();
        for (String opcao : options) {
            maxLength = Math.max(maxLength, opcao.length());
        }
        return maxLength;
    }

    private static void imprimirCabecalho(String message, String linha, int larguraTotal) {
        System.out.println("\n╔" + linha + "╗");
        System.out.println("║  " + until.centralizarTexto(message, larguraTotal - 4) + "  ║");
        System.out.println("╠" + linha + "╣");
    }

    private static void imprimirOpcoes(List<String> options, int larguraTotal) {
        for (int i = 0; i < options.size() - 1; i++) {
            String opcaoFormatada = (i + 1) + " - " + options.get(i);
            System.out.println("║  " + until.centralizarTexto(opcaoFormatada, larguraTotal - 4) + "  ║");
        }

        String opcaoSair = "0 - " + options.get(options.size() - 1);
        System.out.println("║  " + until.centralizarTexto(opcaoSair, larguraTotal - 4) + "  ║");
    }

    private static void imprimirRodape(String linha) {
        System.out.println("╚" + linha + "╝");
    }

    /**
     * Obtém a entrada do usuário garantindo que seja válida.
     *
     * @param options Lista de opções disponíveis.
     * @return Opção escolhida pelo usuário.
     */
    public static int getUserInput(List<String> options) {
        int opcao = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("\nDigite sua opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                if (validarOpcao(opcao, options.size())) {
                    entradaValida = true;
                } else {
                    System.out.println("Opção inválida! Por favor, escolha uma opção entre 0 e " + (options.size() - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite apenas números!");
            }
        }

        return opcao;
    }

    private static boolean validarOpcao(int opcao, int tamanhoLista) {
        return opcao >= 0 && opcao <= tamanhoLista - 1;
    }

    private static void navegarMenu(MenuOperacao menu) {
        menu.executar();
    }

    /**
     * Inicializa o menu principal da aplicação.
     */
    public static void init() {
        List<String> options = new ArrayList<>();
        options.add("Realizar Venda");
        options.add("Gerenciar Estoque");
        options.add("Gerenciar Clientes");
        options.add("Sair");

        createMenu(options, "Audio Mania");

        switch (getUserInput(options)) {
            case 0 -> System.exit(0);
            case 1 -> navegarMenu(new VendaMenuOperacao());
            case 2 -> EstoqueController.gerenciarEstoque();
            case 3 -> ClienteController.gerenciarClientes();
            default -> System.out.println("Opção inválida!");
        }
    }
}