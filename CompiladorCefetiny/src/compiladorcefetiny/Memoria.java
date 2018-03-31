package compiladorcefetiny;

import java.util.HashSet;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class Memoria {
    private static HashSet<Variavel> pilhaMem = new HashSet<Variavel>();
    
    public static Boolean Search (String nomeVariavel){
        
        Variavel[] vetorPilhaMem = new Variavel[pilhaMem.size()];
        pilhaMem.toArray(vetorPilhaMem);
        
        for( int i = 0; i < vetorPilhaMem.length; i++ ){
            if(vetorPilhaMem[i].getName().equals(nomeVariavel)){
                return true;
            }
        }
        
        return false;
    }
    
    public static void Insert (Variavel variavel){
        pilhaMem.add(variavel);
    }
    
}
