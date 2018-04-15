package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class ScannerSimbolo {

    private String expressaoPreparada;
    private CanalEntrada input;
    private String tipoComandoLido;
    private String palavraLida;

    public ScannerSimbolo(CanalEntrada input) {
        this.expressaoPreparada = "";
        this.tipoComandoLido = "";
        this.input = input;

    }

    public void analisaCaractere() {
        char caractere = input.get();

        while (caractere != 0) {
            if (identificaCaractere(caractere)) {

                caractere = input.get();
            } else {
                //Exception CaractereInválido 
            }
        }

    }

    private boolean identificaCaractere(char caractere) {
        return caractere == 32 || caractere == 34 || ((caractere > 39 && caractere < 63) && caractere != 44 && caractere != 59) || caractere == 94 || caractere > 96 && caractere < 123;
    }

    private String identificaTipoCaractere(char caractere) {
        String tipo = "";

        if (caractere > 96 && caractere < 123) {
            tipo = "letra";
        } else if (caractere > 47 && caractere < 58) {
            tipo = "numero";
        } else if (caractere == 32) {
            tipo = "espaço";

        }else if(caractere == 42 || caractere ==43 || caractere ==45 || caractere == 47){
            tipo = "op-aritmetico";
        }else if(caractere >59 && caractere <63){
            tipo = "op-logico";
        }

        return tipo;
    }
    
    
    private class caractereLidoeEsperado {
        private char caractereAtual;
        private String [] tipos_esperados;


        public caractereLidoeEsperado(char caractereAtual, String[] tipos_esperados) {
            this.caractereAtual = caractereAtual;
            this.tipos_esperados = tipos_esperados;
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
