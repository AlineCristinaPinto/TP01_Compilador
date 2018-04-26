package compiladorcefetiny;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ListaExecucao {

    private static List<Comando> listaComandos = new ArrayList<>(); ;

    public ListaExecucao() {}

    public static void preencheLista(Comando comando) {
        listaComandos.add(comando);
    }

    public static void executar() {
        for (int i = 0; i < listaComandos.size(); i++) {
            listaComandos.get(i).execute();
        }
    }
}
