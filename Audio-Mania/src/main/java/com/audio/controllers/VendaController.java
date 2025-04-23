package com.audio.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.audio.controllers.base.MenuBase;
import com.audio.model.Estoque;
import com.audio.model.Produto;
import com.audio.model.VendaSimples;
import com.audio.model.Cliente;

public class VendaController {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<VendaSimples> historicoVendas = new ArrayList<>();

    // Menu de Nova Venda
    private static class NovaVendaMenu extends MenuBase {
        private final VendaSimples venda;
        private Cliente cliente;

        // Construtor do menu de nova venda
        public NovaVendaMenu() {
            super("Nova Venda",
                    "Selecionar Cliente",
                    "Adicionar Produto",
                    "Visualizar Carrinho",
                    "Finalizar Venda");
            this.venda = new VendaSimples();
            this.cliente = null;
        }

        @Override
        protected void processarOpcao(int choice) {
            switch (choice) {
                case 0 -> MenuController.init();
                case 1 -> selecionarCliente();
                case 2 -> adicionarProduto();
                case 3 -> visualizarCarrinho();
                case 4 -> finalizarVenda();
            }
        }

        private void selecionarCliente() {
            System.out.println("\n=== Selecionar Cliente ===");
            System.out.println("1 - Selecionar Cliente Existente");
            System.out.println("2 - Cadastrar Novo Cliente");
            System.out.println("0 - Voltar");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1 -> {
                        System.out.println("Digite o CPF do cliente: ");
                        String cpf = scanner.nextLine();
                        var clienteOpt = ClienteController.getClientePorCPF(cpf);
                        if (clienteOpt.isPresent()){
                            cliente = clienteOpt.get();
                            System.out.println("Cliente selecionado com sucesso");
                            venda.setCliente(cliente);
                        } else {
                            System.out.println("Cliente não encontrado");
                        }
                    }

                    case 2 -> {
                        cliente = ClienteController.cadastrarNovoCliente();
                        if (cliente != null){
                            venda.setCliente(cliente);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção Invalida");
            }
            executar();
        }

        // Adiciona um produto ao carrinho da venda
        private void adicionarProduto() {
            try {
                Produto produto = selecionarProduto();
                if (produto != null) {
                    System.out.print("Digite a quantidade: ");
                    int quantidade = Integer.parseInt(scanner.nextLine());
                    venda.adicionarProduto(produto, quantidade);
                    System.out.println("Produto adicionado com sucesso!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Quantidade inválida!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            executar();
        }

        // Exibe o resumo do carrinho
        private void visualizarCarrinho() {
            System.out.println(venda.gerarResumo());
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        // Finaliza a venda
        private void finalizarVenda() {
            if (cliente == null) {
                System.out.println("É necessario selecionar um cliente primeiro");
                executar();
                return;
            }
            if (confirmarVenda(venda)) {
                MenuController.init();
            } else {
                executar();
            }
        }
    }

    // Menu do Histórico de Vendas
    private static class HistoricoVendasMenu extends MenuBase {
        // Construtor do menu de histórico de vendas
        public HistoricoVendasMenu() {
            super("Histórico de Vendas",
                    "Listar Todas as Vendas",
                    "Buscar por Data",
                    "Relatório de Vendas");
        }

        @Override
        protected void processarOpcao(int choice) {
            switch (choice) {
                case 0 -> MenuController.init();
                case 1 -> listarTodasVendas();
                case 2 -> buscarPorData();
                case 3 -> gerarRelatorio();
            }
        }

        // Lista todas as vendas registradas
        private void listarTodasVendas() {
            if (historicoVendas.isEmpty()) {
                System.out.println("Nenhuma venda registrada.");
            } else {
                for (int i = 0; i < historicoVendas.size(); i++) {
                    System.out.println("\nVenda #" + (i + 1));
                    System.out.println(historicoVendas.get(i).gerarResumo());
                }
            }
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        // busca de vendas por data
        private void buscarPorData() {
            System.out.println("Funcionalidade em desenvolvimento");
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        // geração de relatório de vendas
        private void gerarRelatorio() {
            System.out.println("Funcionalidade em desenvolvimento");
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }
    }

    // Cria um novo processo de venda
    public static void novaVenda() {
        new NovaVendaMenu().executar();
    }

    // Mostra o histórico de vendas
    public static void historicoVendas() {
        new HistoricoVendasMenu().executar();
    }

    // Auxilia na seleção de um produto
    private static Produto selecionarProduto() {
        if (Estoque.listarProdutos().isEmpty()) {
            System.out.println("Não há produtos disponíveis no estoque.");
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            return null;
        }

        System.out.println("\nProdutos Disponíveis:");
        List<Produto> produtos = Estoque.listarProdutos();

        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            System.out.printf("%d. %s - R$ %.2f (%d em estoque)%n",
                    i + 1,
                    p.getNome(),
                    p.getPreco(),
                    p.getQuantidadenoEstoque());
        }

        while (true) {
            try {
                System.out.print("\nSelecione o número do produto (0 para cancelar): ");
                int escolha = Integer.parseInt(scanner.nextLine());

                if (escolha == 0) {
                    return null;
                }

                if (escolha > 0 && escolha <= produtos.size()) {
                    Produto selecionado = produtos.get(escolha - 1);
                    if (selecionado.getQuantidadenoEstoque() > 0) {
                        return selecionado;
                    } else {
                        System.out.println("Produto sem estoque disponível!");
                    }
                } else {
                    System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }
    }

    // Confirma a venda e registra no histórico
    private static boolean confirmarVenda(VendaSimples venda) {
        System.out.println(venda.gerarResumo());
        System.out.print("Confirmar venda? (S/N): ");

        if (scanner.nextLine().equalsIgnoreCase("S")) {
            historicoVendas.add(venda);
            System.out.println("Venda realizada com sucesso!");
            return true;
        } else {
            System.out.println("Venda cancelada.");
            return false;
        }
    }
}
