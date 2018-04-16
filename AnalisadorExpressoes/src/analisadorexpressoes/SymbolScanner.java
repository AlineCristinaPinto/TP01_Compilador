package analisadorexpressoes;

public class SymbolScanner {

    private InputChannel input;
    private String expressaoLiquida;

    public String getExpressaoLiquida() {
        return expressaoLiquida;
    }

    public SymbolScanner(InputChannel input) {
        this.input = input;
        expressaoLiquida = "";
    }

    public boolean validaOperadores(int caractere1, int caractere2) throws ValidaExpressaoException {
        if ((caractere1 > 41 && caractere1 < 48 || caractere1 == 44) && (caractere2 > 41 && caractere2 < 48 || caractere2 == 44)) {
            throw new ValidaExpressaoException();
        }
        if ((caractere1 == 40) && (caractere2 > 41 && caractere2 < 48 || caractere2 == 44)) {
            throw new ValidaExpressaoException();
        }
        if ((caractere1 > 41 && caractere1 < 48 || caractere1 == 44) && (caractere2 == 41)) {
            throw new ValidaExpressaoException();
        }
        return true;
    }

    public String VerificaExpressaoEntrada() throws ValidaExpressaoException {
        char caractere = 0;
        char caractereDevolvido = 1;

        String auxiliar = "";
        expressaoLiquida = "";

        int contadorParentesesAbre = 0;
        int contadorParentesesFecha = 0;
        boolean pontoExiste = false;
        boolean ultimoNumero = false;

        do {
            if (caractereDevolvido != 1 && caractereDevolvido != 0) {
                boolean operadorComum = false;
                if (caractereDevolvido == 40 && (ultimoNumero)) {
                    throw new ValidaExpressaoException();
                }
                if (caractereDevolvido == 42 || caractereDevolvido == 43 || caractereDevolvido == 45 || caractereDevolvido == 47 || caractereDevolvido == 94) {
                    operadorComum = true;
                    if (expressaoLiquida.equals("")) {
                        throw new ValidaExpressaoException();
                    }
                }
                input.get();
                caractere = input.get();
                if (caractere != 0) {
                    if (ehOperador(caractere)) {
                        if (operadorComum) {
                            if (caractere == 40) {
                                auxiliar += String.valueOf(caractereDevolvido);
                            } else {
                                throw new ValidaExpressaoException();
                            }
                        } else {
                            if (caractereDevolvido == 41) {

                                if (caractere == 40) {
                                    throw new ValidaExpressaoException();
                                } else {
                                    auxiliar += String.valueOf(caractereDevolvido);
                                }
                            } else {
                                if (caractere == 41 || caractere == 42 || caractere == 43 || caractere == 45 || caractere == 47 || caractere == 94) {
                                    throw new ValidaExpressaoException();
                                } else {
                                    auxiliar += String.valueOf(caractereDevolvido);
                                }
                            }
                        }
                    } else {
                        if (caractereDevolvido == 41 && ehNumero(caractere)) {
                            throw new ValidaExpressaoException();
                        }
                        auxiliar += String.valueOf(caractereDevolvido);
                    }
                } else if (operadorComum) {
                    throw new ValidaExpressaoException();
                } else {
                    if (caractereDevolvido == 41) {
                        auxiliar += String.valueOf(caractereDevolvido);
                    } else {
                        throw new ValidaExpressaoException();
                    }
                }
                input.unget();
                expressaoLiquida += auxiliar;
                auxiliar = "";
                caractereDevolvido = 1;
                ultimoNumero = false;
            } else {
                caractere = input.get();
                if (caractere != 0) {
                    if ((caractere < 40 || caractere > 57 || caractere == 44) && caractere != 94) {
                        throw new ValidaExpressaoException();
                    }
                    if (ehNumero(caractere)) {
                        auxiliar += String.valueOf(caractere);
                        ultimoNumero = true;

                    }

                    if (caractere == 46) {
                        if (!pontoExiste) {
                            pontoExiste = true;
                            auxiliar += String.valueOf(caractere);
                            caractere = input.get();
                            if (!(ehNumero(caractere))) {
                                throw new ValidaExpressaoException();
                            } else {
                                auxiliar += String.valueOf(caractere);
                            }
                        } else {
                            throw new ValidaExpressaoException();
                        }
                    }

                    if (ehOperador(caractere)) {
                        pontoExiste = false;
                        caractereDevolvido = caractere;
                        input.unget();
                        expressaoLiquida += auxiliar;
                        auxiliar = "";
                    }
                } else {
                    expressaoLiquida += auxiliar;
                }
            }
        } while (caractere != 0);

        for (int i = 0; i < expressaoLiquida.length(); i++) {
            if (expressaoLiquida.charAt(i) == 40) {
                contadorParentesesAbre++;
            }
            if (expressaoLiquida.charAt(i) == 41) {
                contadorParentesesFecha++;
            }

        }
        if (contadorParentesesAbre - contadorParentesesFecha != 0) {
            throw new ValidaExpressaoException();
        }

        return expressaoLiquida;
    }

    public boolean ehNumero(int caractere) {
        if (caractere > 47 && caractere < 58) {
            return true;
        }
        return false;
    }

    public boolean ehOperador(int caractere) {
        if ((caractere > 39 && caractere < 48 && caractere != 44 && caractere != 46) || caractere == 94) {
            return true;
        }
        return false;
    }
}
