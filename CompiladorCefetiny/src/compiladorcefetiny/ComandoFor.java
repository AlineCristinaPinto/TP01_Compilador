package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ComandoFor implements Comando {

    private String comandoAtribuicao;
    private String tipoOperacao;
    private String expressao;
    private PseudoListaExecucao listaComandos;

    private String variavel = "";
    private String conteudo = "";

    public ComandoFor(String comandoAtribuicao, String tipoOperacao, String expressao, PseudoListaExecucao listaComandos) {
        this.comandoAtribuicao = comandoAtribuicao;
        this.tipoOperacao = tipoOperacao;
        this.expressao = expressao;
        this.listaComandos = listaComandos;
    }

    @Override
    public void execute() {
        atribuicao(comandoAtribuicao).execute();
        Variavel variavelTemp;
        variavelTemp = Memoria.searchVariable(variavel);

        if (tipoOperacao.equals("to")) {
            while (Expressao.Exp.calcula(expressao).equals("true")) {
                for (int i = 0; i < listaComandos.getPseudolistaComandos().size(); i++) {
                    listaComandos.getPseudolistaComandos().get(i).execute();
                }
                variavelTemp.setValue(Integer.parseInt(variavelTemp.getValue().toString()) + 1);
            }

        } else if (tipoOperacao.equals("downto")) {
            while (Expressao.Exp.calcula(expressao).equals("true")) {
                for (int i = 0; i < listaComandos.getPseudolistaComandos().size(); i++) {
                    listaComandos.getPseudolistaComandos().get(i).execute();
                }
                variavelTemp.setValue(Integer.parseInt(variavelTemp.getValue().toString()) - 1);
            }
        }
        Memoria.delete(variavel);
    }

    private ComandoAtribuicao atribuicao(String comando) {

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

}
