package compiladorcefetiny;

import java.util.Scanner;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */

public class ComandoReadint implements Comando {

    private String nomeVariavel;

    public ComandoReadint(String nomeVariavel) {
        this.nomeVariavel = nomeVariavel;
    }

    @Override
    public void execute() {

        int valor = 0;

        Scanner ent = new Scanner(System.in);
        valor = ent.nextInt();

        // Variável já existe
        if (Memoria.searchVariableExists(nomeVariavel)) {

            Variavel variavelTemp;

            variavelTemp = Memoria.searchVariable(nomeVariavel);

            variavelTemp.setType("int");
            variavelTemp.setValue(valor);

            //Atualizando a Variavel
            Memoria.update(variavelTemp);
            
        } else {
            // Criando Variável
            Variavel novaVariavelTemp;

            novaVariavelTemp = new Variavel(nomeVariavel, "int", valor);
            
            //Adicionando nova Variavel
            Memoria.insert(novaVariavelTemp);
        }
    }
}
