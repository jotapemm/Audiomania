package com.audio.model;

public class Som extends Produto{
    private String marca;
    private String modelo;

    public Som(String nome, double preco, int quantidadeEstoque, String marca, String modelo) {
        super(nome, preco, quantidadeEstoque);
        this.marca = marca;
        this.modelo = modelo;
    }

    @Override
    public void atualizarEstoque(int quantidade) {
        this.setQuantidadenoEstoque(this.getQuantidadenoEstoque() - quantidade);
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }


    public void setMarca(String Marca) {
        this.marca = Marca;
    }

    public void setModelo(String Modelo) {
        this.modelo = Modelo;
    }


    @Override
    public String toString() {
        return "Som{" +
                "nome='" + getNome() + '\'' +
                ", preco=" + getPreco() +
                ", quantidadeEstoque=" + getQuantidadenoEstoque() +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}

