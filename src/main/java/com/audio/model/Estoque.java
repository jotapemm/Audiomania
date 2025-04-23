package com.audio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Estoque {
    private static final List<Produto> produtos = new ArrayList<>();

    // Construtor privado para evitar instanciação
    private Estoque() {}

    // Métodos de gerenciamento de produtos
    public static void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public static void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    public static List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public static Optional<Produto> buscarProdutoPorNome(String nome) {
        return produtos.stream()
                      .filter(p -> p.getNome().equalsIgnoreCase(nome))
                      .findFirst();
    }

    public static boolean verificarDisponibilidade(Produto produto, int quantidade) {
        return produto.getQuantidadenoEstoque() >= quantidade;
    }

    // Método para atualizar o estoque após uma venda
    public static void atualizarEstoque(Produto produto, int quantidade) {
        if (verificarDisponibilidade(produto, quantidade)) {
            produto.atualizarEstoque(quantidade);
        } else {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque");
        }
    }
} 