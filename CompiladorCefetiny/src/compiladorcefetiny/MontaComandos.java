package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class MontaComandos {

    public MontaComandos() {
    }

    public void montaLista(PseudoComando comando, PseudoListaExecucao listaRecebida, Boolean isInsideCommand) {
        if (!isInsideCommand) {
            switch (comando.getTipoComando()) {
                case "atribuicao":
                    ListaExecucao.preencheLista(atribuicao(comando.getStringComando()));
                    break;
                case "print":
                    ListaExecucao.preencheLista(print(comando.getStringComando()));
                    break;
                case "println":
                    ListaExecucao.preencheLista(println(comando.getStringComando()));
                    break;
                case "readint":
                    ListaExecucao.preencheLista(readint(comando.getStringComando()));
                    break;
                case "if":
                    ListaExecucao.preencheLista(If(comando, isInsideCommand));
                    break;
                case "while":
                    ListaExecucao.preencheLista(While(comando, isInsideCommand));
                    break;
            }
        } else {
            switch (comando.getTipoComando()) {
                case "atribuicao":
                    listaRecebida.preencheLista(atribuicao(comando.getStringComando()));
                    break;
                case "print":
                    listaRecebida.preencheLista(print(comando.getStringComando()));
                    break;
                case "println":
                    listaRecebida.preencheLista(println(comando.getStringComando()));
                    break;
                case "readint":
                    listaRecebida.preencheLista(readint(comando.getStringComando()));
                    break;
                case "if":
                    listaRecebida.preencheLista(If(comando, isInsideCommand));
                    break;
                case "while":
                    listaRecebida.preencheLista(While(comando, isInsideCommand));
                    break;
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
    private ComandoIf If(PseudoComando comando, Boolean isInsideCommand) {

        if (!isInsideCommand) {
            PseudoListaExecucao listaIf = new PseudoListaExecucao();
            String expressao = "";

            expressao = comando.getStringComando().substring((comando.getStringComando().indexOf("(") + 1), (comando.getStringComando().length() - 1));

            if (comando.isPreenchido()) {
                for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                    montaLista(comando.getPseudoLista().get(i), listaIf, true);
                }
            }

            return new ComandoIf(expressao, (PseudoListaExecucao) listaIf.getPseudolistaComandos());
        } else {
            PseudoListaExecucao listaIfInterno = new PseudoListaExecucao();
            String expressao = "";

            expressao = comando.getStringComando().substring((comando.getStringComando().indexOf("(") + 1), (comando.getStringComando().length() - 1));

            if (comando.isPreenchido()) {
                for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                    montaLista(comando.getPseudoLista().get(i), listaIfInterno, true);
                }
            }

            return new ComandoIf(expressao, (PseudoListaExecucao) listaIfInterno.getPseudolistaComandos());
        }
    }

    private ComandoWhile While(PseudoComando comando, Boolean isInsideCommand) {

        if (!isInsideCommand) {
            PseudoListaExecucao listaWhile = new PseudoListaExecucao();
            String expressao = "";

            expressao = comando.getStringComando().substring((comando.getStringComando().indexOf("(") + 1), (comando.getStringComando().length() - 1));

            if (comando.isPreenchido()) {
                for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                    montaLista(comando.getPseudoLista().get(i), listaWhile, true);
                }
            }

            return new ComandoWhile(expressao, listaWhile);
        } else {
            PseudoListaExecucao listaWhileInterno = new PseudoListaExecucao();
            String expressao = "";

            expressao = comando.getStringComando().substring((comando.getStringComando().indexOf("(") + 1), (comando.getStringComando().length() - 1));

            if (comando.isPreenchido()) {
                for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                    montaLista(comando.getPseudoLista().get(i), listaWhileInterno, true);
                }
            }

            return new ComandoWhile(expressao, (PseudoListaExecucao) listaWhileInterno.getPseudolistaComandos());
        }
    }

    private ComandoFor For(PseudoComando comando, Boolean isInsideCommand) {

        if (!isInsideCommand) {
            PseudoListaExecucao listaFor = new PseudoListaExecucao();
            String[] comandoSeparado = comando.getStringComando().split(" ");
            String comandoAtribuicao = comandoSeparado[1];
            String expressao = comandoSeparado[3];

            if (comando.isPreenchido()) {
                for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                    montaLista(comando.getPseudoLista().get(i), listaFor, true);
                }
            }

            return new ComandoFor(comandoAtribuicao, expressao, listaFor);
        } else {
            PseudoListaExecucao listaForInterno = new PseudoListaExecucao();
            String[] comandoSeparado = comando.getStringComando().split(" ");
            String comandoAtribuicao = comandoSeparado[1];
            String expressao = comandoSeparado[3];

            if (comando.isPreenchido()) {
                for (int i = 0; i < comando.getPseudoLista().size(); i++) {
                    montaLista(comando.getPseudoLista().get(i), listaForInterno, true);
                }
            }

            return new ComandoFor(comandoAtribuicao, expressao, listaForInterno);
        }
    }
}
