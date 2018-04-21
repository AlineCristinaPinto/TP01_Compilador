package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class MontaComandos {

    public MontaComandos() {

    }

    public void montaLista(PseudoComando comando, Boolean isInsideCommand, PseudoListaExecucao listaRecebida) {
        if (!isInsideCommand) {
            switch (comando.getTipoComando()) {
                case "atribuicao":
                    ListaExecucao.preencheLista((Comando) atribuicao(comando.getStringComando()));
                case "print":
                    ListaExecucao.preencheLista((Comando) print(comando.getStringComando()));
                case "println":
                    ListaExecucao.preencheLista((Comando) println(comando.getStringComando()));
                case "readint":
                    ListaExecucao.preencheLista((Comando) readint(comando.getStringComando()));
                case "if":
                    ListaExecucao.preencheLista((Comando) If(comando));
                case "while":
                    ListaExecucao.preencheLista((Comando) While(comando));
            }
        } else {
            switch (comando.getTipoComando()) {
                case "atribuicao":
                    listaRecebida.preencheLista((Comando) atribuicao(comando.getStringComando()));
                case "print":
                    listaRecebida.preencheLista((Comando) print(comando.getStringComando()));
                case "println":
                    listaRecebida.preencheLista((Comando) println(comando.getStringComando()));
                case "readint":
                    listaRecebida.preencheLista((Comando) readint(comando.getStringComando()));
                case "if":
                    listaRecebida.preencheLista((Comando) If(comando));
                case "while":
                    listaRecebida.preencheLista((Comando) While(comando));    
            }
        }
    }

    private ComandoAtribuicao atribuicao(String comando) {
        String variavel = "";
        String conteudo = "";
        int posicao = 0;

        String vetorComando[] = comando.split("");

        for (int i = 0; i < vetorComando.length; i++) {

            if (vetorComando[i].equals(":")) {
                if (vetorComando[i + 1].equals("=")) {
                    posicao = i + 1;
                    break;
                }
            }
            variavel += vetorComando[i];
        }

        for (int i = posicao + 1; i < vetorComando.length; i++) {
            conteudo += vetorComando[i];
        }

        return new ComandoAtribuicao(variavel, conteudo);
    }

    private ComandoPrint print(String comando) {
        String expressao = "";

        expressao = comando.substring((comando.indexOf("(") + 1), (comando.length() - 1));

        return new ComandoPrint(expressao);
    }

    private ComandoPrintln println(String comando) {
        String expressao = "";

        expressao = comando.substring((comando.indexOf("(") + 1), (comando.length() - 1));

        return new ComandoPrintln(expressao);
    }

    private ComandoReadint readint(String comando) {
        String variavel = "";

        variavel = comando.substring((comando.indexOf("(") + 1), (comando.length() - 1));

        return new ComandoReadint(variavel);
    }

    //falta lidar com o else
    private ComandoIf If(PseudoComando comando) {
        PseudoListaExecucao listaIf = new PseudoListaExecucao();
        String expressao = "";

        expressao = comando.getStringComando().substring((comando.getStringComando().indexOf("(") + 1), (comando.getStringComando().length() - 1));

        if (comando.isPreenchido()) {
            for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                montaLista(comando.getPseudoLista().get(i), true, listaIf);
            }
        }
        
        return new ComandoIf(expressao, (PseudoListaExecucao) listaIf.getPseudolistaComandos());
    }

    private ComandoWhile While(PseudoComando comando) {
        PseudoListaExecucao listaWhile = new PseudoListaExecucao();
        String expressao = "";

        expressao = comando.getStringComando().substring((comando.getStringComando().indexOf("(") + 1), (comando.getStringComando().length() - 1));

        if (comando.isPreenchido()) {
            for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                montaLista(comando.getPseudoLista().get(i), true, listaWhile);
            }
        }
        
        return new ComandoWhile(expressao, (PseudoListaExecucao) listaWhile.getPseudolistaComandos());
    }
}
