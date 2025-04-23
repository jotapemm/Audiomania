package com.audio;

public class until {

      public static void clearScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
      }
      
      public static String centralizarTexto(String texto, int tamanho) {
            return String.format("%-" + tamanho + "s", texto);
      }
}
