package compiladorcefetiny;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class CompiladorCefetiny {

    public static void main(String[] args) {
        
        Scanner ent = new Scanner (System.in);
        
        LeArquivo l = new LeArquivo();
        ScannerSimbolo s = new ScannerSimbolo (l);
        
        try {
            s.analisaCaractere();
        } catch (ExcecaoSintaxeIncorreta ex) {
            Logger.getLogger(CompiladorCefetiny.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
