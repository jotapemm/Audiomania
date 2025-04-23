package com.audio.model;


/*
 *  Classe Acessorio que herda de Produto
 * 
 *  @param tipo Tipo de acessório
 *  @param nome Nome do acessório
 *  @param preco Preço do acessório
 *  @param quantidadeEstoque Quantidade em estoque do acessório
 */
public class Acessorio extends Produto {
    private final String tipo;
    public Acessorio(String nome, double preco, int quantidadeEstoque, String tipo){
        super(nome, preco, quantidadeEstoque);
        this.tipo = tipo;
    }
    
    @Override
    public void atualizarEstoque(int quantidade){
        setQuantidadenoEstoque(getQuantidadenoEstoque() - quantidade);
    }
}
