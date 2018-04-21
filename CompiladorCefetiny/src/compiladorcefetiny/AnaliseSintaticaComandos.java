package compiladorcefetiny;

/**
 *
 *@author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class AnaliseSintaticaComandos {
    
    private String tipoComandoLido;
    private String palavraLida;
    private String expressaoPreparada;
    private CanalEntrada input;
    private int contaLinhas = 0;
    
    
    public AnaliseSintaticaComandos(CanalEntrada input) {
        this.expressaoPreparada = "";
        this.tipoComandoLido = "";
        this.input = input;

    }
    
    public void terceiraAnaliseSintatica(){
        char caractere = input.get();
        boolean existeAspas = false;
        while (caractere != 0) {
            
            
          caractere = input.get();
        }
            
    }
}
