package com.audio.utils;

public class ValidadorCliente {
    public static boolean validarCPF(String cpf) {

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            System.out.println("CPF deve conter 11 dígitos!");
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            System.out.println("CPF inválido!");
            return false;
        }

        int[] numeros = new int[11];
        for (int i = 0; i < 11; i++) {
            numeros[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += numeros[i] * (10 - i);
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) primeiroDigito = 0;
        if (numeros[9] != primeiroDigito) {
            System.out.println("CPF inválido!");
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += numeros[i] * (11 - i);
        }

        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito > 9) segundoDigito = 0;
        if (numeros[10] != segundoDigito) {
            System.out.println("CPF inválido!");
            return false;
        }

        return true;
    }

    public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (!email.matches(regex)) {
            System.out.println("Email inválido!");
            return false;
        }
        return true;
    }

    public static boolean validarTelefone(String telefone) {
        telefone = telefone.replaceAll("[^0-9]", "");

        if (telefone.length() < 10 || telefone.length() > 11) {
            System.out.println("Telefone deve conter entre 10 e 11 dígitos!");
            return false;
        }
        return true;
    }
}