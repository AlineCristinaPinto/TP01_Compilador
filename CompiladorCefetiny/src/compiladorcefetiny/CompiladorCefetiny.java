package compiladorcefetiny;

import java.util.Scanner;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class CompiladorCefetiny {

    public static void main(String[] args) {
        Scanner ent = new Scanner (System.in);
        LeArquivo l = new LeArquivo();
        ScannerSimbolo s = new ScannerSimbolo (l);
        
        s.analisaCaractere();

        //Atribuicao a = new Atribuicao ();
        //a.realizaAtribuicao("aline:=\"eduardo\"");
        //a.realizaAtribuicao("aline:=10");
        
        /*Variavel v = new Variavel();
        v.setName("aaa");
        v.setType("String");
        v.setValue("String oi");
        
        Variavel v1 = new Variavel();
        v1.setName("bbb");
        v1.setType("String");
        v1.setValue("String oi");
        
        Variavel v2 = new Variavel();
        v2.setName("ccc");
        v2.setType("String");
        v2.setValue("String oi");
       
        Memoria.Insert (v);
        Memoria.Insert (v1);
        Memoria.Insert (v2);
        
        if(Memoria.Search("alice")){
            System.out.println("Dentro");
        } else {
            System.out.println("Fora");
 
        }*/
    }
    
    private static boolean isNumero(String possivelNumero){
       char arrayTemp [] = possivelNumero.toCharArray();
        
        for(char charTemp : arrayTemp){
            if(!((charTemp >47 && charTemp<58) || charTemp == 46))
            {
                return false;
            }
        }
        return true;
    }
}

