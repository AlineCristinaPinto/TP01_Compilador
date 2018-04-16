package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class MontaComandos {

    public MontaComandos() {

    }

    public void montaLista(PseudoComando comando) {

        switch (comando.getTipoComando()) {
            case "atribuicao":
                ListaExecucao.preencheLista((Comando) atribuicao(comando.getStringComando()));
            case "print":
                ListaExecucao.preencheLista((Comando) print(comando.getStringComando()));
            case "println":
                ListaExecucao.preencheLista((Comando) println(comando.getStringComando()));
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
    private ComandoPrint println(String comando) {
        String expressao = "";

        expressao = comando.substring((comando.indexOf("(") + 1), (comando.length() - 1));

        return new ComandoPrint(expressao);
    }
}
