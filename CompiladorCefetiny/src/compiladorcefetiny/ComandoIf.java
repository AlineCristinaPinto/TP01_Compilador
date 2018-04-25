package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ComandoIf implements Comando{
    private String expressao;
    private PseudoListaExecucao listaComandos;

    public ComandoIf(String expressao, PseudoListaExecucao listaComandos) {
        this.expressao = expressao;
        this.listaComandos = listaComandos;
    }

    @Override
    public void execute() {
        Boolean resultIf = Boolean.getBoolean(Expressao.Exp.calcula(expressao));
       
        if(resultIf){
            
            for (int i = 0; i < listaComandos.getPseudolistaComandos().size(); i++) {
                listaComandos.getPseudolistaComandos().get(i).execute();
            }
            
        }
        
        
    }
    
}