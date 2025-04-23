package com.audio.model;


public abstract class Produto {
    private final String nome;
    private final double preco;
    private int quantidadenoEstoque;


    /**
     * Construtor para a classe Produto
     * 
     * @param nome Nome do produto
     * @param preco Preço do produto
     * @param quantidadenoEstoque Quantidade inicial em estoque
     */
    public Produto(String nome, double preco, int quantidadenoEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadenoEstoque = quantidadenoEstoque;
    }

    /**
     * Método abstrato para atualizar o estoque
     * 
     * @param quantidade Quantidade a ser atualizada
     */
    public abstract void atualizarEstoque(int quantidade);

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadenoEstoque() {
        return quantidadenoEstoque;
    }

    public void setQuantidadenoEstoque(int quantidadenoEstoque) {
        this.quantidadenoEstoque = quantidadenoEstoque;
    }


}