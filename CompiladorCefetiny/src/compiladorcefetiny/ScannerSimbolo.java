package compiladorcefetiny;

import java.util.List;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ScannerSimbolo {

    private CanalEntrada input;
    private int contaLinhas = 0;
    private String expressaoPreparada;

    public ScannerSimbolo(CanalEntrada input) {
        this.expressaoPreparada = "";
        this.input = input;
    }

    public void analisaCaractere() {
        primeiraAnaliseCaracteres();
        input.zeraContador();
        segundaAnaliseCaracteres();
    }

    private void primeiraAnaliseCaracteres() {
        boolean validaCaracteres = true;
        char caractere = input.get();
        boolean existeAspas = false;
        while (caractere != 0) {
            if (identificaCaractere(caractere)) {
                String tipoCaractere = identificaTipoCaractere(caractere);
                if (tipoCaractere.equals("aspas")) {
                    existeAspas = !existeAspas;
                } else {
                    if (tipoCaractere.equals("quebra-linha")) {
                        contaLinhas++;
                    }
                    if (existeAspas && ((!(tipoCaractere.equals("numero"))) && (!(tipoCaractere.equals("letra"))))) {
                        char a = caractere;
                        System.out.println(tipoCaractere);
                        System.err.println("Caractere inválido dentro de uma constante String" + "    Linha: " + contaLinhas);
                        //Exception Caractere Inválido
                    }
                }
            } else {
                System.err.println("Caractere inválido" + "    Linha: " + contaLinhas);
                //Exception Caractere Inválido
            }
            caractere = input.get();
        }
        System.out.println("Tudo certo!");
    }

    private void segundaAnaliseCaracteres() {
        CaractereLidoeEsperado conjuntoCaractere = new CaractereLidoeEsperado();
        conjuntoCaractere.caractereAtual = input.get();
        boolean existeAspas = false;
        while ((conjuntoCaractere.caractereAtual != 0) || !(conjuntoCaractere.fimArquivo)) {
            String tipoCaractere = identificaTipoCaractere(conjuntoCaractere.caractereAtual);
            if (tipoCaractere.equals("aspas") && !existeAspas) {
                existeAspas = !existeAspas;
            } else if (!(tipoCaractere.equals("espaco"))) {
                conjuntoCaractere.setTipoAtual(tipoCaractere);
                String[] tempArray = geraTiposEsperados(tipoCaractere);
                conjuntoCaractere.setTipos_esperados(tempArray);

                boolean sintaxeProxCerta = checaProximoCaractere(conjuntoCaractere);
                if (!sintaxeProxCerta) {
                    //Exception sintaxe errada
                    System.out.println("Erro! Sintaxe errada");
                    System.out.println(conjuntoCaractere.caractereAtual);
                }
            }
            conjuntoCaractere.caractereAtual = input.get();
        }
    }

    private boolean identificaCaractere(char caractere) {
        return caractere == 32 || caractere == 34 || ((caractere > 39 && caractere < 63) && caractere != 44 && caractere != 59) || caractere == 94 || (caractere > 96 && caractere < 123) || caractere == 10;
    }

    private String identificaTipoCaractere(char caractere) {
        String tipo = "";

        if (caractere > 96 && caractere < 123) {
            tipo = "letra";
        } else if (caractere > 47 && caractere < 58) {
            tipo = "numero";
        } else if (caractere == 32) {
            tipo = "espaco";
        } else if (caractere == 42 || caractere == 43 || caractere == 45 || caractere == 47) {
            tipo = "op-aritmetico";
        } else if (caractere == 60 || caractere == 62) {
            tipo = "op-logico";
        } else if (caractere == 58) {
            tipo = "dois-pontos";
        } else if (caractere == 61) {
            tipo = "igual";
        } else if (caractere == 46) {
            tipo = "ponto";
        } else if (caractere == 34) {
            tipo = "aspas";
        } else if (caractere == 40) {
            tipo = "parenteses-abre";
        } else if (caractere == 41) {
            tipo = "parenteses-fecha";
        } else if (caractere == 10) {
            tipo = "quebra-linha";
        }
        return tipo;
    }

    private String[] geraTiposEsperados(String tipo) {
        String[] strArray = null;
        boolean temEspaco = false;
        char tempChar = input.get();

        if (tempChar != 0) {
            temEspaco = tempChar == 32;
            input.unget();
        }

        if (tipo.equals("letra")) {

            strArray = new String[9];
            strArray[0] = "letra";
            strArray[1] = "numero";
            strArray[2] = "op-aritmetico";
            strArray[3] = "op-logico";
            strArray[4] = "dois-pontos";
            strArray[5] = "igual";
            strArray[6] = "parenteses-abre";
            strArray[7] = "parenteses-fecha";
            strArray[8] = "aspas";
        } else if (tipo.equals("numero")) {

            strArray = new String[10];
            strArray[0] = "letra";
            strArray[1] = "numero";
            strArray[2] = "op-aritmetico";
            strArray[3] = "op-logico";
            strArray[4] = "dois-pontos";
            strArray[5] = "igual";
            strArray[6] = "parenteses-abre";
            strArray[7] = "parenteses-fecha";
            strArray[8] = "ponto";
            strArray[9] = "aspas";

        } else if (tipo.equals("op-aritmetico")) {
            strArray = new String[3];
            strArray[0] = "letra";
            strArray[1] = "numero";
            strArray[2] = "parenteses-abre";
        } else if (tipo.equals("op-logico")) {
            strArray = new String[4];
            strArray[0] = "letra";
            strArray[1] = "numero";
            strArray[2] = "parenteses-abre";
            strArray[3] = "igual";
        } else if (tipo.equals("dois-pontos")) {
            strArray = new String[1];
            strArray[0] = "igual";
        } else if (tipo.equals("igual")) {
            strArray = new String[4];
            strArray[0] = "letra";
            strArray[1] = "numero";
            strArray[2] = "parenteses-abre";
            strArray[3] = "aspas";
        } else if (tipo.equals("ponto")) {
            strArray = new String[1];
            strArray[0] = "numero";
        } else if (tipo.equals("aspas")) {
            strArray = new String[1];
            strArray[0] = "op-aritmetico"; // Se for +
        } else if (tipo.equals("parenteses-abre")) {
            strArray = new String[4];
            strArray[0] = "letra";
            strArray[1] = "numero";
            strArray[2] = "parenteses-abre";
            strArray[3] = "aspas";
        } else if (tipo.equals("parenteses-fecha")) {
            strArray = new String[6];
            strArray[0] = "letra";
            strArray[1] = "op-aritmetico";
            strArray[2] = "parenteses-fecha";
            strArray[3] = "op-logico";
            strArray[4] = "igual";
        }

        return strArray;
    }

    private boolean checaProximoCaractere(CaractereLidoeEsperado caractereAtual) {
        int contaGet = 0;
        boolean tipoEsperadoCorreto = false;
        boolean existeEspaço = false;
        char caractereProx = input.get();

        if (caractereProx == 0) {
            caractereAtual.fimArquivo = true;
            return true;
        }
        contaGet++;
        while (caractereProx == 32) {
            caractereProx = input.get();
            contaGet++;
            existeEspaço = true;
        }

        //System.out.println(caractereAtual.caractereAtual);
        //System.out.println(caractereProx);
        String tipoProxCaractere = identificaTipoCaractere(caractereProx);

        if (existeEspaço && (caractereAtual.tipoAtual.equals("dois-pontos") || caractereAtual.tipoAtual.equals("ponto") || tipoProxCaractere.equals("letra") || tipoProxCaractere.equals("numero"))) {
            if (caractereAtual.tipoAtual.equals("dois-pontos") || caractereAtual.tipoAtual.equals("ponto")) {
                // Exception sintaxe errada
            }else if (caractereAtual.tipoAtual.equals("letra") || caractereAtual.tipoAtual.equals("numero")) {
                if (tipoProxCaractere.equals("letra")) {
                    caractereAtual.existeEspacoDepois = true;
                }
                if (tipoProxCaractere.equals("numero")) {
                    //Exception sintaxe errada
                }
            } else {
                tipoEsperadoCorreto = true;
            }
        } else if (caractereAtual.caractereAtual == '<' && caractereProx == '>') {
            tipoEsperadoCorreto = true;
        } else if (caractereAtual.caractereAtual == '\"' && tipoProxCaractere.equals("op-aritmetico")) {
            tipoEsperadoCorreto = caractereProx == '+';
        } else {
            System.out.println(tipoProxCaractere + "    " + caractereProx);
            for (String tipo_esperado : caractereAtual.tipos_esperados) {
                if (tipoProxCaractere.equals(tipo_esperado)) {
                    tipoEsperadoCorreto = true;
                }
            }
        }

        for (int i = 0; i < contaGet; i++) {
            input.unget();
        }

        return tipoEsperadoCorreto;
    }

    private class CaractereLidoeEsperado {

        private char caractereAtual;
        private String tipoAtual;
        private String[] tipos_esperados;
        private boolean existeEspacoDepois;
        private boolean fimArquivo;

        public void setTipoAtual(String tipoAtual) {
            this.tipoAtual = tipoAtual;
        }

        public String getTipoAtual() {
            return tipoAtual;
        }

        public CaractereLidoeEsperado(char caractereAtual, String tipoAtual, String[] tipos_esperados) {
            this.caractereAtual = caractereAtual;
            this.tipoAtual = tipoAtual;
            this.tipos_esperados = tipos_esperados;
            existeEspacoDepois = false;
        }

        private CaractereLidoeEsperado() {

        }

        public char getCaractereAtual() {
            return caractereAtual;
        }

        public void setCaractereAtual(char caractereAtual) {
            this.caractereAtual = caractereAtual;
        }

        public String[] getTipos_esperados() {
            return tipos_esperados;
        }

        public void setTipos_esperados(String[] tipos_esperados) {
            this.tipos_esperados = tipos_esperados;
        }

    }
}
