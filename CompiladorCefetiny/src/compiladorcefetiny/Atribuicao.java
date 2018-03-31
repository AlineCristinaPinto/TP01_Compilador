package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class Atribuicao {
    
    public void realizaAtribuicao( String expressao ) {
        String variavel = "";
        String conteudo = "";
        int posicao = 0;
        String tipo = "";
                
        String vetorExpressao[] = expressao.split("");
        
        for(int i = 0; i < vetorExpressao.length; i++) {
          
            if(vetorExpressao[i].equals(":")){
                if(vetorExpressao[i+1].equals("=")){
                    posicao = i+1;
                    break;
                }
         
            }
            
            variavel += vetorExpressao[i];
             
        }
        
        for(int i = posicao+1; i < vetorExpressao.length; i++) {
            conteudo += vetorExpressao[i];
        }
    
        /*
            Casos de Atribuição:
        
        - String;
        - numero (int, double);
        - expressao (usar interpretador);
        - booleano (>, <, =, <=, >=);
        
        */
        
        if(conteudo.substring(0, 1).equals("\"") && conteudo.substring(conteudo.length()-1, conteudo.length()).equals("\"")){
            
            tipo = "String";
        
        } else {
            
     // as outras formas de atribuição   
       
        }
        
        //System.out.println(variavel + " " + conteudo + " " + tipo);
    }
    
}
