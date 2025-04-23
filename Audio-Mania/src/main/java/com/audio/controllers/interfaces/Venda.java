package com.audio.controllers.interfaces;

import com.audio.model.Produto;

public interface Venda {
    void adicionarProduto(Produto produto, int quantidade);
    double calcularTotal();
    String gerarResumo();
} 