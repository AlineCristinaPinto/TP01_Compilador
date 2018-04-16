package analisadorexpressoes;

import java.util.Scanner;

public class ConsoleIn implements InputChannel {

    private String expressaoBruta;
    private int contadorCaractere;

    public ConsoleIn() {
        this.contadorCaractere = 0;
    }

    public void recebeExpressao() {
        this.contadorCaractere = 0;
        Scanner recebe = new Scanner(System.in);
        System.out.println("Insira uma expressão: ");
        expressaoBruta = recebe.nextLine();
    }

    @Override
    public char get() {
        if (contadorCaractere == expressaoBruta.length()) {
            return 0;
        }

        char caractere = expressaoBruta.charAt(contadorCaractere);
        contadorCaractere++;
        return caractere;
    }

    @Override
    public void unget() {
        contadorCaractere--;
    }

}
