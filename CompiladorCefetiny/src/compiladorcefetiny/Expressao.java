package testeexpressao;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */

import java.util.ArrayList;
import java.util.List;

public class Expressao {
    private String expressao;
    private List<String> operandos;
    private List<String> operadores;
    
    public static class Exp{
        private static String expressao;
        private static List<String> operandos;
        private static List<String> operadores;
        
        public static String calcula(String expressao){
            Exp.expressao = expressao;
            operandos = new ArrayList();
            operadores = new ArrayList();
            prepara();
            processa();
            return resultado();
        }
        
        public static void prepara(){
            
            int iniCadeia = 0;
            String valorVariavel = null;
            
            expressao = expressao.replace(" and ", "&");
            expressao = expressao.replace(" or ", "|");
            expressao = expressao.replace("not(", "!(");
       
            Variavel var = null;
                
            expressao = expressao.replace(" ", "");
                        
            for(int i = 0; i < expressao.length(); i++){
                iniCadeia = i;
                   
                while(expressao.charAt(i) != '(' && expressao.charAt(i) != '^' && expressao.charAt(i) != '*' && expressao.charAt(i) != '/' && expressao.charAt(i) != '+' && expressao.charAt(i) != '-' && expressao.charAt(i) != ')' && expressao.charAt(i) != '=' && expressao.charAt(i) != '<' && expressao.charAt(i) != '>' && expressao.charAt(i) != '&' && expressao.charAt(i) != '|'){
                    if( i+1 == expressao.length()){
                        i++; break;
                    }
                    else if( i+1 < expressao.length() ) i++;
                }   
                if(iniCadeia != i){
                        
                    if( nomeDeVariavel( expressao.substring(iniCadeia, i) ) && Memoria.searchVariableExists( expressao.substring(iniCadeia, i) ) ){
                        var = Memoria.searchVariable( expressao.substring(iniCadeia, i) );
                     
                        if( var.getType().compareTo("boolean") == 0 ){               
                            expressao = expressao.substring(0, iniCadeia) + "2=2" + expressao.substring( i, expressao.length() );
                            i = i - ( i - iniCadeia ) + 3;
                        }
                        else if( var.getType().compareTo("string") == 0 ){ 
                            valorVariavel = (String) var.getValue();
                            expressao = expressao.substring(0, iniCadeia) + "\"" + var.getValue() + "\"" + expressao.substring( i, expressao.length() );
                            i = i - ( i - iniCadeia ) + 2 + valorVariavel.length();
                        }
                        else {
                            valorVariavel = (String) var.getValue();
                            expressao = expressao.substring(0, iniCadeia) + var.getValue() + expressao.substring( i, expressao.length() );
                            i = i - ( i - iniCadeia ) + valorVariavel.length();
                        }   
                    }
                                        
                } else i--;
            } 
            if( somaDeStrings() ){
                boolean caractereON = false;
            
                for(int i = 0; i < expressao.length(); i++){
                    
                    if( expressao.charAt(i) == '\'' | expressao.charAt(i) == '\"'){
                        if( caractereON ) caractereON = false;
                        else caractereON = true;
                    }                        
                    else if( expressao.charAt(i) == ' ' && !caractereON ){
                        if(i == 0)
                            expressao = expressao.substring(1);
                        else
                            expressao = expressao.substring(0, i) + expressao.substring(i+1, expressao.length());
                        i--;
                    }
                }
            }    
            
        }
        
        public static void processa(){
            
            String elemento = null;
            int iniCadeia = 0;
        
            for(int i = 0; i < expressao.length(); ){
                switch(expressao.charAt(i)){
                    case '(': elemento = "("; i++; break;
                    case '^': elemento = "^"; i++; break;
                    case '*': elemento = "*"; i++; break;
                    case '/': elemento = "/"; i++; break;
                    case '+': elemento = "+"; i++; break;
                    case '-': elemento = "-"; i++; break;
                    case ')': elemento = ")"; i++; break;
                    case '=': elemento = "="; i++; break;
                    case '>':
                        if( expressao.charAt(i+1) == '='){
                             elemento = ">=";
                            i+=2;
                        } else{ 
                            elemento = ">";
                            i++;       
                        }
                        break;
                    case '<': 
                        if( expressao.charAt(i+1) == '>'){
                            elemento = "<>";
                            i+=2;
                        }
                        else if( expressao.charAt(i+1) == '='){
                            elemento = "<=";
                            i+=2;
                        } else{ 
                            elemento = "<";
                            i++;       
                        }
                        break;
                    case '&': elemento = "&"; i++; break;
                    case '|': elemento = "|"; i++; break;
                    case '!': elemento = "!"; i++; break;
                    default:
                        iniCadeia = i;
                        while(expressao.charAt(i) != '(' && expressao.charAt(i) != '^' && expressao.charAt(i) != '*' && expressao.charAt(i) != '/' && expressao.charAt(i) != '+' && expressao.charAt(i) != '-' && expressao.charAt(i) != ')' && expressao.charAt(i) != '=' && expressao.charAt(i) != '<' && expressao.charAt(i) != '>' && expressao.charAt(i) != '&' && expressao.charAt(i) != '|'){
                            if( i+1 == expressao.length()){
                                i++; break;
                            }
                            else if( i+1 < expressao.length() ) i++;
                        }    
                        elemento = expressao.substring(iniCadeia, i);
                }  
                
                if( elemento.compareTo("(") == 0){
                    operadores.add("(");
                }  
                else if( elemento.compareTo("^") == 0){
                    if( verificaPrecedencia(ordemOperador("^")) == 1){
                       operadores.add("^");  
                    }
                    else if( verificaPrecedencia(ordemOperador("^")) == 0){
                       while( verificaPrecedencia(ordemOperador("^")) != 1) resolveUltimaOperacao();
                       operadores.add("^");
                    }              
                }
                else if( elemento.compareTo("sqrt") == 0) {
                   operadores.add("sqrt");
                }
                else if( elemento.compareTo("*") == 0){
                    if( verificaPrecedencia(ordemOperador("*")) == 1){
                        operadores.add("*");  
                    }
                    else if( verificaPrecedencia(ordemOperador("*")) == 0){
                        while( verificaPrecedencia(ordemOperador("*")) != 1) resolveUltimaOperacao();
                        operadores.add("*");
                    }
                }
                else if( elemento.compareTo("/") == 0){
                    if( verificaPrecedencia(ordemOperador("/")) == 1){
                       operadores.add("/");  
                    }
                    else if( verificaPrecedencia(ordemOperador("/")) == 0){
                       while( verificaPrecedencia(ordemOperador("/")) != 1) resolveUltimaOperacao();
                       operadores.add("/");
                    }
                }
                else if( elemento.compareTo("+") == 0){
                    if( verificaPrecedencia(ordemOperador("+")) == 1){
                        operadores.add("+");  
                    }
                    else if( verificaPrecedencia(ordemOperador("+")) == 0){
                        while( verificaPrecedencia(ordemOperador("+")) != 1) resolveUltimaOperacao();
                        operadores.add("+");
                    }
                }
                else if( elemento.compareTo("-") == 0){
                    if( verificaPrecedencia(ordemOperador("-")) == 1){
                        operadores.add("-");  
                    }
                    else if( verificaPrecedencia(ordemOperador("-")) == 0){
                        while( verificaPrecedencia(ordemOperador("-")) != 1) resolveUltimaOperacao();
                        operadores.add("-");
                    }
                }
                else if( elemento.compareTo("=") == 0){
                    if( verificaPrecedencia(ordemOperador("=")) == 1){
                        operadores.add("=");  
                    }
                    else if( verificaPrecedencia(ordemOperador("=")) == 0){
                        while( verificaPrecedencia(ordemOperador("=")) != 1) resolveUltimaOperacao();
                        operadores.add("=");
                    }
                }
                else if( elemento.compareTo("<>") == 0){
                    if( verificaPrecedencia(ordemOperador("<>")) == 1){
                        operadores.add("<>");  
                    }
                    else if( verificaPrecedencia(ordemOperador("<>")) == 0){
                        while( verificaPrecedencia(ordemOperador("<>")) != 1) resolveUltimaOperacao();
                        operadores.add("<>");
                    }
                }
                else if( elemento.compareTo(">") == 0){
                    if( verificaPrecedencia(ordemOperador(">")) == 1){
                        operadores.add(">");  
                    }
                    else if( verificaPrecedencia(ordemOperador(">")) == 0){
                        while( verificaPrecedencia(ordemOperador(">")) != 1) resolveUltimaOperacao();
                        operadores.add(">");
                    }
                }
                else if( elemento.compareTo(">=") == 0){
                    if( verificaPrecedencia(ordemOperador(">=")) == 1){
                        operadores.add(">=");  
                    }
                    else if( verificaPrecedencia(ordemOperador(">=")) == 0){
                        while( verificaPrecedencia(ordemOperador(">=")) != 1) resolveUltimaOperacao();
                        operadores.add(">=");
                    }
                }
                else if( elemento.compareTo("<") == 0){
                    if( verificaPrecedencia(ordemOperador("<")) == 1){
                        operadores.add("<");  
                    }
                    else if( verificaPrecedencia(ordemOperador("<")) == 0){
                        while( verificaPrecedencia(ordemOperador("<")) != 1) resolveUltimaOperacao();
                        operadores.add("<");
                    }
                }
                else if( elemento.compareTo("<=") == 0){
                    if( verificaPrecedencia(ordemOperador("<=")) == 1){
                        operadores.add("<=");  
                    }
                    else if( verificaPrecedencia(ordemOperador("<=")) == 0){
                        while( verificaPrecedencia(ordemOperador("<=")) != 1) resolveUltimaOperacao();
                        operadores.add("<=");
                    }
                }
                else if( elemento.compareTo("&") == 0){
                    if( verificaPrecedencia(ordemOperador("&")) == 1){
                        operadores.add("&");  
                    }
                    else if( verificaPrecedencia(ordemOperador("&")) == 0){
                        while( verificaPrecedencia(ordemOperador("&")) != 1) resolveUltimaOperacao();
                        operadores.add("&");
                    }
                }
                else if( elemento.compareTo("|") == 0){
                    if( verificaPrecedencia(ordemOperador("|")) == 1){
                        operadores.add("|");  
                    }
                    else if( verificaPrecedencia(ordemOperador("|")) == 0){
                        while( verificaPrecedencia(ordemOperador("|")) != 1) resolveUltimaOperacao();
                        operadores.add("|");
                    }
                }
                else if( elemento.compareTo("!") == 0) operadores.add("!");
                    else if( elemento.compareTo(")") == 0){
                    if( operadores.get( operadores.size()-2 ).compareTo("sqrt") == 0 ){
                        resolveSqrt();
                        operadores.remove( operadores.size()-1 );
                        operadores.remove( operadores.size()-1 );
                    } else{
                        while( operadores.get( operadores.size()-1 ).compareTo("(") != 0 ){
                            resolveUltimaOperacao();
                        } 
                        operadores.remove( operadores.size()-1 ); 
                        if ( operadores.get( operadores.size()-1 ).compareTo("!") == 0){
                            negaUltimaOperacao();
                            operadores.remove( operadores.size()-1 );                   
                        }    
                    }                               
                }
                
                else operandos.add(elemento); 
            } 
            while( !operadores.isEmpty() ){
                resolveUltimaOperacao();
            }       
        }
    
        public static int ordemOperador(String operador){
            int ordem = 0;
   
            if(operador.compareTo("^") == 0) ordem = 5;
            else if(operador.compareTo("*") == 0) ordem = 4; 
            else if(operador.compareTo("/") == 0) ordem = 4; 
            else if(operador.compareTo("+") == 0) ordem = 3; 
            else if(operador.compareTo("-") == 0) ordem = 3; 
            else if(operador.compareTo("=") == 0) ordem = 2;
            else if(operador.compareTo("<>") == 0) ordem = 2;
            else if(operador.compareTo(">") == 0) ordem = 2;
            else if(operador.compareTo(">=") == 0) ordem = 2;
            else if(operador.compareTo("<") == 0) ordem = 2;
            else if(operador.compareTo("<=") == 0) ordem = 2;
            else if(operador.compareTo("&") == 0) ordem = 1;
            else if(operador.compareTo("|") == 0) ordem = 0;
       
            return ordem;
        }
        
        public static int verificaPrecedencia(int ordemOperador){  
            // Maior ou igual = 1, Menor = 0
            if( operadores.isEmpty() || ordemOperador >= ordemOperador( operadores.get( operadores.size()-1 ) ) )
                return 1;
            return 0;
        }
    
        public static void resolveUltimaOperacao(){   
        
            double a = 0, b = 0; 
            String stringA = null, stringB = null;
            boolean termoLogicoA, termoLogicoB;
        
            boolean contaTipoDouble = false;
            
            if( somaDeStrings() ){         
                stringB = operandos.get( operandos.size()-1 );
                operandos.remove( operandos.size()-1 );
            
                stringA = operandos.get( operandos.size()-1 );
                operandos.remove( operandos.size()-1 );
            }
            else{
                if( operandos.get( operandos.size()-1 ).contains(".") || operandos.get( operandos.size()-2 ).contains(".") ){
                    contaTipoDouble = true;
                }
                b = Double.parseDouble( operandos.get( operandos.size()-1 ) );
                operandos.remove( operandos.size()-1 );
                a = Double.parseDouble( operandos.get( operandos.size()-1 ) );
                operandos.remove( operandos.size()-1 );               
            }
       
            String operador = operadores.get(operadores.size()-1);
            operadores.remove( operadores.size()-1 );
    
            if( contaTipoDouble ){
    
                if(operador.compareTo("^") == 0) operandos.add( String.valueOf( Math.pow(a,b) ) );
                else if(operador.compareTo("*") == 0) operandos.add( String.valueOf(a*b) ); 
                else if(operador.compareTo("/") == 0) operandos.add( String.valueOf(a/b) ); 
                else if(operador.compareTo("+") == 0) operandos.add( String.valueOf(a+b) );
                else if(operador.compareTo("-") == 0) {
                    if( operadores.isEmpty() ) operandos.add( String.valueOf(a-b) );
                    else if( operadores.get( operadores.size()-1 ).compareTo("-") == 0 )
                        operandos.add( String.valueOf(a+b) );
                    else operandos.add( String.valueOf(a-b) );
                }
            
            } else{
                if(operador.compareTo("^") == 0) operandos.add( String.valueOf( (int)Math.pow(a,b) ) );
                else if(operador.compareTo("*") == 0) operandos.add( String.valueOf( (int)(a*b) ) ); 
                else if(operador.compareTo("/") == 0) operandos.add( String.valueOf( (int)(a/b) ) ); 
                else if(operador.compareTo("+") == 0) {
                    if( somaDeStrings() ){
                        String aux = stringA + stringB;
                        operandos.add(aux);
                    }else operandos.add( String.valueOf( (int)(a+b) ) );
                } 
                else if(operador.compareTo("-") == 0) {
                    if( operadores.isEmpty() ) operandos.add( String.valueOf( (int)(a-b) ) );
                    else if( operadores.get( operadores.size()-1 ).compareTo("-") == 0 )
                        operandos.add( String.valueOf( (int)(a+b) ) );
                    else operandos.add( String.valueOf( (int)(a-b) ) );
                }
            }
        
            if(operador.compareTo("=") == 0){
                if(a == b) operandos.add("1");
                else operandos.add("0");
            } 
            else if(operador.compareTo("<>") == 0){
                if(a != b) operandos.add("1");
                else operandos.add("0");
            } 
            else if(operador.compareTo(">") == 0){
                if(a > b) operandos.add("1");
                else operandos.add("0");
            } 
            else if(operador.compareTo(">=") == 0){
                if(a >= b) operandos.add("1");
                else operandos.add("0");
            } 
            else if(operador.compareTo("<") == 0){
                if(a < b) operandos.add("1");
                else operandos.add("0");
            } 
            else if(operador.compareTo("<=") == 0){
                if(a <= b) operandos.add("1");
                else operandos.add("0");
            } 
            else if(operador.compareTo("&") == 0){
                if(a == 1) termoLogicoA = true;
                else termoLogicoA = false;
                if(b == 1) termoLogicoB = true;
                else termoLogicoB = false;
           
                if(termoLogicoA && termoLogicoB) operandos.add("1");
                else operandos.add("0");
            } 
            else if(operador.compareTo("|") == 0){
                if(a == 1) termoLogicoA = true;
                else termoLogicoA = false;
                if(b == 1) termoLogicoB = true;
                else termoLogicoB = false;
           
                if(termoLogicoA || termoLogicoB) operandos.add("1");
                else operandos.add("0");
            }        
        }
    
        public static void resolveSqrt(){
            double a = Double.parseDouble( operandos.get( operandos.size()-1 ) );
            operandos.remove( operandos.get( operandos.size()-1 ) );
            a = (double) Math.sqrt(a);
            operandos.add( String.valueOf( a ));
        }
    
        public static void negaUltimaOperacao(){
            if( operandos.get( operandos.size()-1 ).compareTo("0") == 0 ){
                operandos.remove( operandos.size()-1 );
                operandos.add("1");
            } else{
                operandos.remove( operandos.size()-1 );
                operandos.add("0");
            }
        }
        
        public static boolean somaDeStrings(){
            if(expressao.contains("\'")) return true;
            return expressao.contains("\"");
        }
        
        public static boolean nomeDeVariavel(String elemento){
            return elemento.charAt(0) != '0' && elemento.charAt(0) != '1' && elemento.charAt(0) != '2' && elemento.charAt(0) != '3' && elemento.charAt(0) != '4' && elemento.charAt(0) != '5' && elemento.charAt(0) != '6' && elemento.charAt(0) != '7' && elemento.charAt(0) != '8' && elemento.charAt(0) != '9';
        }
        
        public static String resultado(){ 
            String aux = operandos.get(operandos.size()-1);
                    
            if( somaDeStrings() ){
                aux = aux.replace("\'", "");
                aux = aux.replace("\"", "");    
            }                
            else if( expressao.contains("&") || expressao.contains("|") || expressao.contains("!") || expressao.contains(">") || expressao.contains("<") || expressao.contains("=") ){                
                if(aux.compareTo("1") == 0) return "true";
                else return "false";
            }  
            
            return aux;       
        } 
    }

}