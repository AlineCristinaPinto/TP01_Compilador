package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ValidaSemantica {
    //construtor privado para que a classe nao seja instanciada
    private ValidaSemantica(){
        
    }
    
    //expressaoTipos armazena a expressao porem alterando os valores por um
    //identificador de seu tipo correspondente. b = booleano, n = numerico, s = string,
    //r( = sqrt(, i( = not(. Os operadores nao sao alterados.
    
    public static boolean validaExpressao(String expressao){
        expressao = expressao.trim();
        String expressaoTipos = "";
        
        if(expressao.length() > 0){
            //tipoEsperado e' utilizada para alternar entre a leitura de operandos ou operadores.
            //fimUltimoTermo guarda a ultima posicao do ultimo termo lido.
            //contaFechaParenteses conta para verificar se cada parenteses aberto
            //esta' sendo devidamente fechado
            int tipoEsperado = 0, fimUltimoTermo = 0, contaFechaParenteses = 0;

            for(int i = 0; i <= expressao.length(); i++){
                //se tipoEsperado == 0, verifica se o proximo termo lido sera' um operando
                if(tipoEsperado == 0){
                    if(encontraEspaco(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraEspaco(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 0;

                    }else if(encontraSqrt(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraSqrt(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 0;
                        contaFechaParenteses++;
                        expressaoTipos += "r(";

                    }else if(encontraNot(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraNot(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 0;
                        contaFechaParenteses++;
                        expressaoTipos += "i(";

                    }else if(encontraConstanteNumerica(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraConstanteNumerica(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 1;
                        expressaoTipos += "n";

                    }else if(encontraBooleano(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraBooleano(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 1;
                        expressaoTipos += "b";

                    }else if(encontraVariavel(expressao.substring(fimUltimoTermo)) > 0){
                        int auxFimUltimoTermo = fimUltimoTermo;
                        fimUltimoTermo = fimUltimoTermo + encontraVariavel(expressao.substring(fimUltimoTermo));
                        String variavel = expressao.substring(auxFimUltimoTermo, fimUltimoTermo);

                        //checa se a variavel esta' na memoria e o tipo dela
                        if(Memoria.searchVariableExists(variavel)){
                            Variavel var = Memoria.searchVariable(variavel);
                            tipoEsperado = 1;
                            switch(var.getType()){
                                case "boolean":
                                    expressaoTipos += "b";
                                    break;
                                case "string":
                                    expressaoTipos += "s";
                                    break;
                                case "int":
                                case "double":
                                    expressaoTipos += "n";
                                    break;
                                case "expressao":
                                    //se for do tipo expressao, remove a variavel da expressao
                                    //e substitui pelo valor correspondente
                                    String auxExpressao = expressao.substring(fimUltimoTermo);
                                    expressao = expressao.substring(0, auxFimUltimoTermo) 
                                            +"(" +var.getValue() +")";
                                    expressao += auxExpressao;
                                    tipoEsperado = 0;
                                    fimUltimoTermo = auxFimUltimoTermo;
                                    break;
                            }
                        }else {
                            //throws exception variavel nao encontrada
                            //return "ERRO: variavel nao encontrada";
                            return false;
                        }

                    }else if(encontraString(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraString(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 1;
                        expressaoTipos += "s";

                    }else if(encontraAbreParenteses(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraAbreParenteses(expressao.substring(fimUltimoTermo));
                        contaFechaParenteses++;
                        tipoEsperado = 0;
                        expressaoTipos += "(";
                    }else{
                        //throws exception
                        //return "ERRO: expressao invalida";
                        return false;
                    }
                //se tipoEsperado == 1 verifica se o proximo termo lido sera' um operador
                }else if(tipoEsperado == 1){
                    if(encontraEspaco(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraEspaco(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 1;
                    }else if(encontraOperadoresNumericos(expressao.substring(fimUltimoTermo)) > 0){
                        int auxFimUltimoTermo = fimUltimoTermo;
                        fimUltimoTermo = fimUltimoTermo + encontraOperadoresNumericos(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 0;
                        expressaoTipos += expressao.substring(auxFimUltimoTermo, fimUltimoTermo).trim();

                    }else if(encontraOperadoresRelacionais(expressao.substring(fimUltimoTermo)) > 0){
                        int auxFimUltimoTermo = fimUltimoTermo;
                        fimUltimoTermo = fimUltimoTermo + encontraOperadoresRelacionais(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 0;
                        expressaoTipos += expressao.substring(auxFimUltimoTermo, fimUltimoTermo);

                    }else if(encontraOperadoresLogicos(expressao.substring(fimUltimoTermo)) > 0){
                        int auxFimUltimoTermo = fimUltimoTermo;
                        fimUltimoTermo = fimUltimoTermo + encontraOperadoresLogicos(expressao.substring(fimUltimoTermo));
                        tipoEsperado = 0;
                        expressaoTipos += expressao.substring(auxFimUltimoTermo, fimUltimoTermo);

                    }else if(encontraFechaParenteses(expressao.substring(fimUltimoTermo)) > 0){
                        fimUltimoTermo = fimUltimoTermo + encontraFechaParenteses(expressao.substring(fimUltimoTermo));
                        contaFechaParenteses--;
                        if(contaFechaParenteses < 0){
                            //throws exception fecha parenteses a mais
                            //return "ERRO: fecha parenteses a mais";
                            return false;
                        }
                        tipoEsperado = 1;
                        expressaoTipos += ")";

                    }else{
                        //throws exception
                        //return "ERRO: expressao invalida";
                        return false;
                    }
                }
                i = fimUltimoTermo;

            }
            if(contaFechaParenteses > 0){
                //throws exception
                //return "ERRO: parenteses nao foi fechado";
                return false;
            }

            return resolveExpTipos(expressaoTipos);
            
        }else{
            return false;
        }
    }
    
    private static boolean resolveExpTipos(String expressao){
        
        expressao = substituiOp(expressao);
        
        return expressao.equals("b") || expressao.equals("n") || expressao.equals("s");
    }
    
    private static String substituiOp(String expressao){
        while(expressao.contains("n+n") || expressao.contains("n-n") ||
                expressao.contains("n*n") || expressao.contains("n/n") ||
                expressao.contains("n^n") || expressao.contains("nmodn") || 
                expressao.contains("ndivn") || expressao.contains("r(n)") || 
                expressao.contains("s+b") || expressao.contains("s+n") || 
                expressao.contains("s+s") || expressao.contains("n+s") || 
                expressao.contains("b+s") || expressao.contains("n+b") || 
                expressao.contains("b+b") || expressao.contains("n=n") || 
                expressao.contains("n<>n") || expressao.contains("n>n") || 
                expressao.contains("n<n") || expressao.contains("b=b") || 
                expressao.contains("b<>b") || expressao.contains("i(b)") || 
                expressao.contains("borb") || expressao.contains("bandb") || 
                expressao.contains("s=s") ||expressao.contains("s<>s") || 
                expressao.contains("n=b") || expressao.contains("n<>b") || 
                expressao.contains("n=s") || expressao.contains("n<>s") || 
                expressao.contains("b=n") || expressao.contains("b<>n") || 
                expressao.contains("b=s") || expressao.contains("b<>s") || 
                expressao.contains("(n)") || expressao.contains("(b)") || 
                expressao.contains("(s)")){
            
            if(expressao.contains("n+n")){
                expressao = expressao.replace("n+n", "n");
            }else if(expressao.contains("n-n")){
                expressao = expressao.replace("n-n", "n");
            }else if(expressao.contains("n*n")){
                expressao = expressao.replace("n*n", "n");
            }else if(expressao.contains("n/n")){
                expressao = expressao.replace("n/n", "n");
            }else if(expressao.contains("n^n")){
                expressao = expressao.replace("n^n", "n");
            }else if(expressao.contains("nmodn")){
                expressao = expressao.replace("nmodn", "n");
            }else if(expressao.contains("ndivn")){
                expressao = expressao.replace("ndivn", "n");
            }else if(expressao.contains("r(n)")){
                expressao = expressao.replace("r(n)", "n");
            }else if(expressao.contains("i(b)")){
                expressao = expressao.replace("i(b)", "b");
            }else if(expressao.contains("s+b")){
                expressao = expressao.replace("s+b", "s");
            }else if(expressao.contains("s+n")){
                expressao = expressao.replace("s+n", "s");
            }else if(expressao.contains("n+b")){
                //throws exception
                return "ERRO: expressao invalida - soma de numerico com boolean";
            }else if(expressao.contains("b+b")){
                //throws exception
                return "ERRO: expressao invalida - soma de boolean com boolean";
            }else if(expressao.contains("s+s")){
                expressao = expressao.replace("s+s", "s");
            }else if(expressao.contains("n+s")){
                expressao = expressao.replace("n+s", "s");
            }else if(expressao.contains("b+s")){
                expressao = expressao.replace("b+s", "s");
            }else if(expressao.contains("(n)")){
                expressao = expressao.replace("(n)", "n");
            }else if(expressao.contains("(b)")){
                expressao = expressao.replace("(b)", "b");
            }else if(expressao.contains("(s)")){
                expressao = expressao.replace("(s)", "s");
            }else if(expressao.contains("n=n")){
                expressao = expressao.replace("n=n", "b");
            }else if(expressao.contains("n<>n")){
                expressao = expressao.replace("n<>n", "b");
            }else if(expressao.contains("n<n")){
                expressao = expressao.replace("n<n", "b");
            }else if(expressao.contains("n>n")){
                expressao = expressao.replace("n>n", "b");
            }else if(expressao.contains("b=b")){
                expressao = expressao.replace("b=b", "b");
            }else if(expressao.contains("b<>b")){
                expressao = expressao.replace("b<>b", "b");
            }else if(expressao.contains("i(b)")){
                expressao = expressao.replace("i(b)", "b");
            }else if(expressao.contains("borb")){
                expressao = expressao.replace("borb", "b");
            }else if(expressao.contains("bandb")){
                expressao = expressao.replace("bandb", "b");
            }else if(expressao.contains("s=s")){
                expressao = expressao.replace("s=s", "b");
            }else if(expressao.contains("s<>s")){
                expressao = expressao.replace("s<>s", "b");
            }else if(expressao.contains("n=b")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre numerico e boolean";
            }else if(expressao.contains("n<>b")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre numerico e boolean";
            }else if(expressao.contains("n=s")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre numerico e string";
            }else if(expressao.contains("n<>s")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre numerico e string";
            }else if(expressao.contains("b=n")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre boolean e numerico";
            }else if(expressao.contains("b<>n")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre boolean e numerico";
            }else if(expressao.contains("b=s")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre boolean e string";
            }else if(expressao.contains("b<>s")){
                //throws exception
                return "ERRO: expressao invalida: comparacao entre boolean e string";
            }else if(expressao.contains("(n)")){
                expressao = expressao.replace("(n)", "n");
            }else if(expressao.contains("(b)")){
                expressao = expressao.replace("(b)", "b");
            }else if(expressao.contains("(s)")){
                expressao = expressao.replace("(s)", "s");
            }
        
        }
        return expressao;
    }
    
    //Todos os metodos encontraxxx procuram por um termo e retornam a ultima 
    //posicao do termo encontrado. Caso nao encontre o que procura, retorna -1, 
    //pois sempre que achar ira' retornar um valor positivo.
    private static int encontraSqrt(String expressao){
        //regexSqrt: (sqrt\()
        
        if(expressao.length() >= 5){
            if(expressao.substring(0, 4).equals("sqrt")){
                int i = 4;
                if(expressao.charAt(i) == '('){
                    return i + 1;
                }else{
                    return -1;
                }
            }
        }else{
            return -1;
        }
        
        return -1;
    }
    
    private static int encontraNot(String expressao){
        //regexNot: (not)\(
        
        if(expressao.length() >= 4){
            if(expressao.substring(0, 3).equals("not")){
                int i = 3;
                if(expressao.charAt(i) == '('){
                    return i + 1;
                }else{
                    return -1;
                }
            }
        }else{
            return -1;
        }
        
        return -1;
    }
    
    private static int encontraConstanteNumerica(String expressao){
        //regexConstanteNumerica: (regexDigito)+|((regexDigito)*\.(regexDigito)+)
        //System.out.println(expressao);
        boolean encontrouPonto = false;
        
        for(int i = 0; i < expressao.length(); i++){
            if(!encontrouPonto){
                if(!encontraDigito(expressao.charAt(i))){
                    if(expressao.charAt(i) == '.'){
                        encontrouPonto = true;
                    }else if(i == 0){
                        return -1;
                    }else{
                        return i;
                    }
                }else{
                    if(i == expressao.length() - 1){
                        return i + 1;
                    }
                }
            }else{
                if(!encontraDigito(expressao.charAt(i))){
                    if(i > 1){
                        return i;
                    }else{
                        return -1;
                    }
                }else{
                    if(i == expressao.length() - 1){
                        return i + 1;
                    }
                }
            }
            
            
        }
        
        return -1;
    }
    
    private static int encontraVariavel(String expressao){
        //regexVariavel: ^regexLetra(regexLetra|regexDigito)*
        //regexMulop: (div|mod|and)
        
        if(encontraLetra(expressao.charAt(0))){
            for(int i = 1; i < expressao.length(); i++){
                if(encontraLetra(expressao.charAt(i)) || encontraDigito(expressao.charAt(i))){
                    if(i == expressao.length() - 1){
                        return i + 1;
                    }
                }else{
                    return i;
                }
            }
            
            return 1;
        }
        
        return -1;
    }
    
    private static int encontraOperadoresNumericos(String expressao){
        //regexOpNumerico: (\+|-|\*|/|mod|div)
        
        if(expressao.charAt(0) == '+' || expressao.charAt(0) == '-' ||
                expressao.charAt(0) == '*' || expressao.charAt(0) == '/' ||
                expressao.charAt(0) == '^'){
            return 1;
        }
        
        if(expressao.length() >= 3){
            if(expressao.substring(0, 3).equals("mod") || expressao.substring(0, 3).equals("div")){
                if(expressao.length() >= 4 && encontraLetra(expressao.charAt(3))){
                    return -1;
                }else{
                    return 3;
                }
            }
        }
        
        return -1;
    }
    
    private static int encontraOperadoresLogicos(String expressao){
        
        if(expressao.length() > 1){
            if(expressao.length() >= 3 && expressao.substring(0, 3).equals("and")){
                if(expressao.length() >= 4 && encontraLetra(expressao.charAt(3))){
                    return -1;
                }else{
                    return 3;
                }
            }else if(expressao.substring(0, 2).equals("or")){
                if(expressao.length() >= 3 && encontraLetra(expressao.charAt(2))){
                    return -1;
                }else{
                    return 2;
                }
            }else{
                return -1;
            }
        }
        
        return -1;
    }
    
    private static int encontraOperadoresRelacionais(String expressao){
        //regexOpRelacional: (<|>|<=|>=|<>|=)
        
        if(expressao.length() > 0){
            if(expressao.length() >= 2 && (expressao.substring(0, 2).equals("<=") || expressao.substring(0, 2).equals(">=") ||
                    expressao.substring(0, 2).equals("<>"))){
                return 2;
            }else if(expressao.charAt(0) == '<' || expressao.charAt(0) == '>' ||
                    expressao.charAt(0) == '='){
                return 1;
            }else{
                return -1;
            }

        }
        
        return -1;
    }
    
    private static int encontraString(String expressao){
        //regexString: ^\"(regexLetra|regexDigito)*\"$
        
        if(expressao.charAt(0) == '"'){
            for(int i = 1; i < expressao.length(); i++){
                if(!(encontraLetra(expressao.charAt(i)) || encontraDigito(expressao.charAt(i)))){
                    if(expressao.charAt(i) == '"'){
                        return i + 1;
                    }else{
                        return -1;
                    }
                }
            }
        }
        
        return -1;
    }
    
    private static int encontraBooleano(String expressao){
        //regexBooleano: (true|false);
        
        if(expressao.length() >= 5){
            if(expressao.substring(0, 5).equals("false")){
                return 5;
            }else if(expressao.substring(0, 4).equals("true")){
                return 4;
            }
        }else if(expressao.length() == 4){
            if(expressao.substring(0, 4).equals("true")){
                return 4;
            }
        }
        
        return -1;
    }
    
    private static int encontraAbreParenteses(String expressao){
        //regexAbreParenteses: (\()
        
        if(expressao.charAt(0) == '('){
            return 1;
        }else{
            return -1;
        }
    }
    
    private static int encontraFechaParenteses(String expressao){
        //regexFechaParenteses: (\))
        
        if(expressao.charAt(0) == ')'){
            return 1;
        }else{
            return -1;
        }
    }
    
    private static int encontraEspaco(String expressao){
        //regex: ( )+
        
        if(expressao.charAt(0) != ' '){
            return -1;
        }else{
            for(int i = 1; i < expressao.length(); i++){
                if(!(expressao.charAt(i) == ' ')){
                    return i;
                }
            }
        }
        
        return -1;
    }
    
    private static boolean encontraLetra(char caractere){
        return caractere >= 97 && caractere <= 122;
    }
    
    private static boolean encontraDigito(char caractere){
        return caractere >= 48 && caractere <= 57;
    }
    
    
}
