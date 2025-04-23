package com.audio.controllers.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.audio.controllers.MenuController;
import com.audio.controllers.interfaces.MenuOperacao;

public abstract class MenuBase implements MenuOperacao {
    protected final List<String> options;
    protected final String titulo;

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

    protected abstract void processarOpcao(int choice);
} 