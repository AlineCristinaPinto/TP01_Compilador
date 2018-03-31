package analisadorexpressoes;

import java.util.Scanner;

public class AnalisadorExpressoes {

    public static void main(String[] args) {

        ConsoleIn console = new ConsoleIn();
        InterpretadorExpressao interpretador = new InterpretadorExpressao();
        SymbolScanner scanner = new SymbolScanner(console);

        String expressaoVerificada = "";

        boolean bool = false;

        do {
            try {
                console.recebeExpressao();
                expressaoVerificada = scanner.VerificaExpressaoEntrada();
                bool = false;
            } catch (ValidaExpressaoException e) {
                bool = true;
                e.printStackTrace();
            }
        } while (bool);

        interpretador.separaExpressoes(expressaoVerificada);
        System.out.println(interpretador.analisaCalculo());

    }

}
