package com.audio.controllers;

import java.util.List;
import java.util.Scanner;

import com.audio.controllers.base.MenuBase;
import com.audio.model.Acessorio;
import com.audio.model.Som;
import com.audio.model.Estoque;
import com.audio.model.Produto;

public class EstoqueController {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Menu de gerenciamento de estoque.
     */
    private static class EstoqueMenu extends MenuBase {

        /**
         * Construtor que inicializa o menu com opções de estoque.
         */
        public EstoqueMenu() {
            super("Gerenciamento de Estoque",
                    "Adicionar Novo Produto",
                    "Remover Produto",
                    "Listar Produtos",
                    "Buscar Produto",
                    "Atualizar Estoque");
        }

        /**
         * Processa a opção escolhida pelo usuário.
         *
         * @param choice Opção selecionada pelo usuário no menu.
         */
        @Override
        protected void processarOpcao(int choice) {
            switch (choice) {
                case 0 -> MenuController.init();
                case 1 -> adicionarNovoProduto();
                case 2 -> removerProduto();
                case 3 -> listarProdutos();
                case 4 -> buscarProduto();
                case 5 -> atualizarEstoque();
            }
        }

        /**
         * Adiciona um novo produto ao estoque, podendo ser uma bicicleta ou um acessório.
         */
        private void adicionarNovoProduto() {
            System.out.println("\nTipo de Produto:");
            System.out.println("1. Som");
            System.out.println("2. Acessório");
            System.out.print("Escolha o tipo: ");

            try {
                int tipo = Integer.parseInt(scanner.nextLine());
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Preço: ");
                double preco = Double.parseDouble(scanner.nextLine());
                System.out.print("Quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());

                if (tipo == 1) {
                    System.out.print("Marca: ");
                    String marca = scanner.nextLine();
                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine();

                    Estoque.adicionarProduto(new Som(nome, preco, quantidade, marca, modelo));
                } else if (tipo == 2) {
                    System.out.print("Tipo de Acessório: ");
                    String tipoAcessorio = scanner.nextLine();

                    Estoque.adicionarProduto(new Acessorio(nome, preco, quantidade, tipoAcessorio));
                }

                System.out.println("Produto adicionado com sucesso!");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido inserido!");
            }

            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        /**
         * Remove o produto selecionado do estoque.
         */
        private void removerProduto() {
            listarProdutos();
            System.out.print("\nDigite o número do produto para remover (0 para cancelar): ");

            try {
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < Estoque.listarProdutos().size()) {
                    Produto produto = Estoque.listarProdutos().get(index);
                    Estoque.removerProduto(produto);
                    System.out.println("Produto removido com sucesso!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Número inválido!");
            }

            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        /**
         * Lista todos os produtos disponíveis no estoque.
         */
        private void listarProdutos() {
            List<Produto> produtos = Estoque.listarProdutos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto no estoque.");
            } else {
                System.out.println("\nProdutos em Estoque:");
                for (int i = 0; i < produtos.size(); i++) {
                    Produto p = produtos.get(i);
                    System.out.printf("%d. %s - R$ %.2f (%d em estoque)%n",
                            i + 1, p.getNome(), p.getPreco(), p.getQuantidadenoEstoque());
                }
            }

            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        /**
         * Busca um produto no estoque pelo nome.
         */
        private void buscarProduto() {
            System.out.print("Digite o nome do produto: ");
            String nome = scanner.nextLine();

            Estoque.buscarProdutoPorNome(nome).ifPresentOrElse(
                    produto -> System.out.printf("%s - R$ %.2f (%d em estoque)%n",
                            produto.getNome(), produto.getPreco(), produto.getQuantidadenoEstoque()),
                    () -> System.out.println("Produto não encontrado.")
            );

            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        /**
         * Atualiza a quantidade em estoque de um produto selecionado.
         */
        private void atualizarEstoque() {
            listarProdutos();
            System.out.print("\nDigite o número do produto para atualizar (0 para cancelar): ");

            try {
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < Estoque.listarProdutos().size()) {
                    Produto produto = Estoque.listarProdutos().get(index);
                    System.out.print("Digite a nova quantidade em estoque: ");
                    int novaQuantidade = Integer.parseInt(scanner.nextLine());
                    produto.setQuantidadenoEstoque(novaQuantidade);
                    System.out.println("Estoque atualizado com sucesso!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Número inválido!");
            }

            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }
    }

    /**
     * Inicia o gerenciamento de estoque exibindo o menu.
     */
    public static void gerenciarEstoque() {
        new EstoqueMenu().executar();
    }
} 