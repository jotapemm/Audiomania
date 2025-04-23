package com.audio.controllers;

import com.audio.controllers.base.MenuBase;
import com.audio.model.Cliente;
import com.audio.utils.ValidadorCliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class ClienteController {
    private static final List<Cliente> clientes = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static class ClienteMenu extends MenuBase {
        public ClienteMenu() {
            super("Gerenciamento de Clientes",
                    "Cadastrar Cliente",
                    "Buscar Cliente",
                    "Listar Clientes",
                    "Atualizar Cliente",
                    "Remover Cliente");
        }

        @Override
        protected void processarOpcao(int choice) {
            switch (choice) {
                case 0 -> MenuController.init();
                case 1 -> cadastrarCliente();
                case 2 -> buscarCliente();
                case 3 -> listarClientes();
                case 4 -> atualizarCliente();
                case 5 -> removerCliente();
            }
        }

        private void cadastrarCliente() {
            Cliente novoCliente = cadastrarNovoCliente();
            if (novoCliente != null) {
                System.out.println("\nCliente cadastrado com sucesso!");
            }
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        private void buscarCliente() {
            System.out.println("\n=== Buscar Cliente ===");
            System.out.print("Digite o CPF do cliente: ");
            String cpf = scanner.nextLine();

            Optional<Cliente> clienteOpt = getClientePorCPF(cpf);
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                exibirDetalhesCliente(cliente);
            } else {
                System.out.println("Cliente não encontrado!");
            }
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        private void listarClientes() {
            if (clientes.isEmpty()) {
                System.out.println("\nNenhum cliente cadastrado!");
            } else {
                System.out.println("\n=== Lista de Clientes ===");
                clientes.forEach(cliente -> {
                    System.out.println("\n--------------------");
                    exibirDetalhesCliente(cliente);
                });
            }
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        private void atualizarCliente() {
            System.out.println("\n=== Atualizar Cliente ===");
            System.out.print("Digite o CPF do cliente: ");
            String cpf = scanner.nextLine();

            Optional<Cliente> clienteOpt = getClientePorCPF(cpf);
            if (clienteOpt.isEmpty()) {
                System.out.println("Cliente não encontrado!");
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
                executar();
                return;
            }

            Cliente cliente = clienteOpt.get();
            System.out.println("\nDados atuais do cliente:");
            exibirDetalhesCliente(cliente);

            System.out.println("\nDigite os novos dados (ou pressione ENTER para manter o valor atual):");

            System.out.print("Novo nome [" + cliente.getNome() + "]: ");
            String novoNome = scanner.nextLine();
            if (!novoNome.isEmpty()) cliente.setNome(novoNome);

            System.out.print("Novo telefone [" + cliente.getTelefone() + "]: ");
            String novoTelefone = scanner.nextLine();
            if (!novoTelefone.isEmpty()) cliente.setTelefone(novoTelefone);

            System.out.print("Novo email [" + cliente.getEmail() + "]: ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.isEmpty() && ValidadorCliente.validarEmail(novoEmail)) {
                cliente.setEmail(novoEmail);
            }

            System.out.println("\nDados atualizados com sucesso!");
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }

        private void removerCliente() {
            System.out.println("\n=== Remover Cliente ===");
            System.out.print("Digite o CPF do cliente: ");
            String cpf = scanner.nextLine();

            Optional<Cliente> clienteOpt = getClientePorCPF(cpf);
            if (clienteOpt.isEmpty()) {
                System.out.println("Cliente não encontrado!");
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
                executar();
                return;
            }

            Cliente cliente = clienteOpt.get();
            System.out.println("\nDados do cliente a ser removido:");
            exibirDetalhesCliente(cliente);

            System.out.print("\nConfirma a remoção? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                clientes.remove(cliente);
                System.out.println("Cliente removido com sucesso!");
            } else {
                System.out.println("Operação cancelada!");
            }
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            executar();
        }
    }

    public static void gerenciarClientes() {
        new ClienteMenu().executar();
    }

    public static Optional<Cliente> getClientePorCPF(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst();
    }

    private static void exibirDetalhesCliente(Cliente cliente) {
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Data de Cadastro: " + cliente.getDataCadastro());
    }

    public static Cliente cadastrarNovoCliente() {
        System.out.println("\n=== Cadastro de Cliente ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        String cpf;
        do {
            System.out.print("CPF (apenas números): ");
            cpf = scanner.nextLine();
            if (getClientePorCPF(cpf).isPresent()) {
                System.out.println("CPF já cadastrado!");
                return null;
            }
        } while (!ValidadorCliente.validarCPF(cpf));

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
        } while (!ValidadorCliente.validarEmail(email));

        Cliente cliente = new Cliente(nome, cpf, telefone, email);
        clientes.add(cliente);
        return cliente;
    }

    public static List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public static boolean existeCliente(String cpf) {
        return getClientePorCPF(cpf).isPresent();
    }
}