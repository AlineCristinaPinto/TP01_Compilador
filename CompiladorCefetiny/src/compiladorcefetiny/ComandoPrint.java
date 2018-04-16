package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */

public class ComandoPrint implements Comando {

    private String print;

    public ComandoPrint(String print) {
        this.print = print;
    }

    @Override
    public void execute() {
        String tipo = "";
        String vetPrintUni[] = print.split("");
        boolean flag = false;

        for (int i = 0; i < vetPrintUni.length; i++) {
            if (vetPrintUni[i].equals("+")) {
                flag = true;
            }
        }
        
        if (flag) {

            String vetPrint[] = print.split("\\+");
            String mensagem = "";

            for (int i = 0; i < vetPrint.length; i++) {
                if (i == 0) {
                    mensagem += concatenaExpressao(vetPrint[i], vetPrint[i + 1]);
                } else if (i < vetPrint.length - 1) {
                    vetPrint[i] = mensagem;
                    mensagem = "";
                    mensagem += concatenaExpressao(vetPrint[i], vetPrint[i + 1]);
                }
            }

            String vetMensagem[] = mensagem.split("\\|");

            for (int i = 0; i < vetMensagem.length; i++) {
                tipo = descobreTipoTexto(vetMensagem[i]);
        
                if (tipo.equals("Variavel")) {
                    Variavel variavelTemp;
                    variavelTemp = Memoria.searchVariable(vetMensagem[i]);
                    vetMensagem[i] = variavelTemp.getValue().toString();
                } else if (tipo.equals("expressao")) {
                    //vetMensagem[i] = recebeExpressao(vetMensagem[i]);
                } else if (tipo.equals("Texto")) {
                    String text = vetMensagem[i];
                    text = text.replaceAll("\"", "");
                    vetMensagem[i] = text;
                }
            }

            String impressao = "";

            for (int i = 0; i < vetMensagem.length; i++) {
                impressao += vetMensagem[i];
            }

            System.out.println(impressao);
        } else {

            String impressao = print;
            tipo = descobreTipoTexto(impressao);

            if (tipo.equals("Variavel")) {
                Variavel variavelTemp;
                variavelTemp = Memoria.searchVariable(impressao);
                impressao = variavelTemp.getValue().toString();
            } else if (tipo.equals("expressao")) {
                //vetMensagem[i] = recebeExpressao(vetMensagem[i]);
            } else if (tipo.equals("Texto")) {
                String text = impressao;
                text = text.replaceAll("\"", "");
                impressao = text;
            }

            System.out.println(impressao);
        }
    }

    public String concatenaExpressao(String vet1, String vet2) {
        vet1 = vet1.trim();
        vet2 = vet2.trim();

        String vetAux1[] = vet1.split("");
        String vetAux2[] = vet2.split("");

        String concatenado = "";

        if (vetAux1[vetAux1.length - 1].matches("[0-9]") && vetAux2[0].matches("[0-9]")) {
            concatenado = vet1 + "+" + vet2;
        } else if (vetAux1[vetAux1.length - 1].matches("[0-9]") && vetAux2[0].equals("(")) {
            concatenado = vet1 + "+" + vet2;
        } else if (vetAux1[vetAux1.length - 1].equals(")") && vetAux2[0].matches("[0-9]")) {
            concatenado = vet1 + "+" + vet2;
        } else if (vetAux1[vetAux1.length - 1].equals(")") && vetAux2[0].equals("(")) {
            concatenado = vet1 + "+" + vet2;
        } else {
            concatenado = vet1 + "|" + vet2;
        }

        return concatenado;
    }

    public String descobreTipoTexto(String conteudo) {
        String tipo = "";
        /*
        Responsavel por identificar e retornar:
        int, double - um numero;
        Texto - uma string;
        Variavel - uma string sem aspas;
        Expressao - lógica, aritmética;
         */
        if (conteudo.matches("[0-9]+")) {
            tipo = "int";
        } else if (conteudo.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)")) {
            tipo = "double";
        } else if (conteudo.substring(0, 1).equals("\"") && conteudo.substring(conteudo.length() - 1, conteudo.length()).equals("\"")) {
            tipo = "Texto";
        } else if (conteudo.equals("true") || conteudo.equals("false")) {
            tipo = "boolean";
        } else if (conteudo.matches("([a-z\\-]+).*")) {
            tipo = "Variavel";
        } else {
            tipo = "expressao";
        }

        return tipo;
    }

}
