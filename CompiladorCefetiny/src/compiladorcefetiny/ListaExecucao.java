package compiladorcefetiny;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ListaExecucao {

    private static List<Comando> listaComandos;

    public ListaExecucao() {
        listaComandos = new ArrayList<>();
    }

    public static void preencheLista(Comando comando) {

        listaComandos.add(comando);
    }
}
