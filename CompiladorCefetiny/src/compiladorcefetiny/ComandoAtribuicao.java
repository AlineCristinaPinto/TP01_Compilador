package compiladorcefetiny;
/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ComandoAtribuicao implements Comando {

    private String variavel;
    private String conteudo;

    public ComandoAtribuicao(String variavel, String conteudo) {
        this.variavel = variavel;
        this.conteudo = conteudo;
    }

    @Override
    public void execute() {
        String tipo = "";
        // Variavel já existe        
        if (Memoria.searchVariableExists(variavel)) {
            Variavel variavelTemp;
            variavelTemp = Memoria.searchVariable(variavel);
            tipo = descobreTipoVariavel(conteudo);
            
            switch (tipo) {
                case "int":
                    variavelTemp.setType(tipo);
                    variavelTemp.setValue(Integer.parseInt(conteudo));
                    break;
                case "double":
                    variavelTemp.setType(tipo);
                    variavelTemp.setValue(Double.parseDouble(conteudo));
                    break;
                case "string":
                    variavelTemp.setType(tipo);
                    variavelTemp.setValue(conteudo);
                    break;
                case "boolean":
                    variavelTemp.setType(tipo);
                    variavelTemp.setValue( Boolean.parseBoolean(conteudo));
                    break;
                case "expressao":
                    //conteudo = recebeExpressao(conteudo);
                    tipo = descobreTipoVariavel(conteudo);
                    variavelTemp.setType(tipo);
                    if(tipo.equals("int")){
                        variavelTemp.setValue(Integer.parseInt(conteudo));
                    } else if (tipo.equals("double")){
                        variavelTemp.setValue(Double.parseDouble(conteudo));
                    } else if (tipo.equals("string")){
                        variavelTemp.setValue(conteudo);
                    } else if (tipo.equals("boolean")){
                        variavelTemp.setValue( Boolean.parseBoolean(conteudo));
                    }                    
                    break;
                default:
                    break;
            }
        } else {
            // Criando Variável
            Variavel novaVariavelTemp;
            tipo = descobreTipoVariavel(conteudo);

            switch (tipo) {
                case "int":
                    novaVariavelTemp = new Variavel(variavel, tipo, Integer.parseInt(conteudo));
                    break;
                case "double":
                    novaVariavelTemp = new Variavel(variavel, tipo, Double.parseDouble(conteudo));
                    break;
                case "string":
                    novaVariavelTemp = new Variavel(variavel, tipo, conteudo);
                    break;
                case "boolean":
                    novaVariavelTemp = new Variavel(variavel, tipo, Boolean.parseBoolean(conteudo));
                    break;
                case "expressao":
                    //conteudo = recebeExpressao(conteudo);
                    tipo = descobreTipoVariavel(conteudo);
                    if(tipo.equals("int")){
                        novaVariavelTemp = new Variavel(variavel, tipo, Integer.parseInt(conteudo));
                    } else if (tipo.equals("double")){
                        novaVariavelTemp = new Variavel(variavel, tipo, Double.parseDouble(conteudo));
                    } else if (tipo.equals("string")){
                        novaVariavelTemp = new Variavel(variavel, tipo, conteudo);
                    } else if (tipo.equals("boolean")){
                        novaVariavelTemp = new Variavel(variavel, tipo, Boolean.parseBoolean(conteudo));
                    }   
                    break;
                default:
                    break;
            }
        }
    }

    public String descobreTipoVariavel(String conteudo) {
        String tipo = "";        
        /*
        Responsavel por identificar e retornar:
        int, double - um numero;
        String - uma string;
        Expressao - lógica, aritmética, concatenação de string com string / string com numero;
         */
        if (conteudo.matches("[0-9]+")) {
            tipo = "int";
        } else if (conteudo.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)")) {
            tipo = "double";
        } else if (conteudo.substring(0, 1).equals("\"") && conteudo.substring(conteudo.length() - 1, conteudo.length()).equals("\"")) {
            tipo = "string";
        } else if (conteudo.equals("true") || conteudo.equals("false")) {
            tipo = "boolean";
        } else {
            tipo = "expressao";
        }

        return tipo;
    }
    
    /*
        Ruan, a função que eu te falei
    
        String elementosAtrib = "";
        char vetorExpressao[] = conteudo.toCharArray();
    
            for (int i = 0; i < vetorExpressao.length; i++) {
                if ((vetorExpressao[i] > 41 && vetorExpressao[i] < 48 && vetorExpressao[i] != 44 && vetorExpressao[i] != 46)) {
                    tipo = "aritmetica";
                    elementosAtrib += " ";
                } else if (vetorExpressao[i] > 59 && vetorExpressao[i] < 63) {
                    tipo = "logica";
                    elementosAtrib += " ";
                } else if (vetorExpressao[i] == 60) {
                    if (i != vetorExpressao.length - 1 && vetorExpressao[i + 1] == 62) {
                        tipo = "logica";
                    }
                    elementosAtrib += " ";
                } else {
                    elementosAtrib += vetorExpressao[i];
                }
            }
    */
}
