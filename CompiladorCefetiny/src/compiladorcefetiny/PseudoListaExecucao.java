package compiladorcefetiny;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class PseudoListaExecucao {

    private List<Comando> PseudolistaComandos;
    

    public PseudoListaExecucao() {
        PseudolistaComandos = new ArrayList<>();
    }

    public void preencheLista(Comando comando) {

        PseudolistaComandos.add(comando);
    }

    public List<Comando> getPseudolistaComandos() {
        return PseudolistaComandos;
    }
    
}
