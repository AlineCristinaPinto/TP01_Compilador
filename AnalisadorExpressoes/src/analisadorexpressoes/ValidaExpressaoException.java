package analisadorexpressoes;

public class ValidaExpressaoException extends Exception {

    public String getMessage() {
        return "Erro::Expressão inserida inválida.";
    }
}
