package analisadorexpressoes;

public class OperacoesMatematica {

    public static int calculaSoma(String operando1, String operando2) {
        return Integer.parseInt(operando1) + Integer.parseInt(operando2);
    }

    public static int calculaSubtracao(String operando1, String operando2) {
        return Integer.parseInt(operando1) - Integer.parseInt(operando2);
    }

    public static int calculaMultiplicacao(String operando1, String operando2) {
        return Integer.parseInt(operando1) * Integer.parseInt(operando2);
    }

    public static int calculaDivisao(String operando1, String operando2) {
        return Integer.parseInt(operando1) / Integer.parseInt(operando2);
    }

    public static int calculaPotenciacao(String operando1, String operando2) {
        int expoente = Integer.parseInt(operando2);
        int base = Integer.parseInt(operando1);
        int base_aux = Integer.parseInt(operando1);
        for (int i = 0; i < expoente - 1; i++) {
            base *= base_aux;
        }

        return base;
    }

    public static double calculaSomaDouble(String operando1, String operando2) {
        return Double.parseDouble(operando1) + Double.parseDouble(operando2);
    }

    public static double calculaSubtracaoDouble(String operando1, String operando2) {
        return Double.parseDouble(operando1) - Double.parseDouble(operando2);
    }

    public static double calculaMultiplicacaoDouble(String operando1, String operando2) {
        return Double.parseDouble(operando1) * Double.parseDouble(operando2);
    }

    public static double calculaDivisaoDouble(String operando1, String operando2) {
        return Double.parseDouble(operando1) / Double.parseDouble(operando2);
    }

    public static double calculaPotenciacaoDouble(String operando1, String operando2) {
        double expoente = Double.parseDouble(operando2);
        double base = Double.parseDouble(operando1);
        double base_aux = Double.parseDouble(operando1);
        for (int i = 0; i < expoente - 1; i++) {
            base *= base_aux;
        }

        return base;
    }
}
