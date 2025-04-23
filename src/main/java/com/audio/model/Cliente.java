package com.audio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private List<VendaSimples> historicoCompras;
    private LocalDate dataCadastro;

    public Cliente() {
        this.historicoCompras = new ArrayList<>();
        this.dataCadastro = LocalDate.now();
    }

    public Cliente(String nome, String cpf, String telefone, String email) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void adicionarCompra(VendaSimples venda) {
        this.historicoCompras.add(venda);
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s | CPF: %s",
                nome, cpf);
    }
}