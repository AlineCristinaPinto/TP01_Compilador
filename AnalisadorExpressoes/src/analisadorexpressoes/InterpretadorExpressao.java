package analisadorexpressoes;

/**
 *
 * @author Aline Cristina e Eduardo Cotta INF3A
 */
public class InterpretadorExpressao {

    private Pilha expressaoPilha;

    public InterpretadorExpressao() {
        expressaoPilha = new Pilha();
    }

    public Pilha getExpressaoPilha() {
        return expressaoPilha;
    }

    public boolean verificaOperadores(String caractere) {
        if (caractere.equals("+") || caractere.equals("-") || caractere.equals("/") || caractere.equals("*") || caractere.equals("^") || caractere.equals("(") || caractere.equals(")")) {
            return true;
        } else {
            return false;
        }
    }

    public void separaExpressoes(String expressao) {
        String aux = "";

        String arr[] = expressao.split("");

        for (int i = 0; i < arr.length; i++) {
            if (verificaOperadores(arr[i])) {
                if (!(arr[i].equals("(")) && !(")".equals(arr[i - 1]))) {
                    expressaoPilha.insereFim(aux);
                    aux = "";
                }
                expressaoPilha.insereFim(arr[i]);

            } else {
                aux += arr[i];
            }

            if (i == arr.length - 1) {
                expressaoPilha.insereFim(aux);
            }
        }
    }

    public String analisaCalculo() {
        int contaTamanho = 0;
        Nodo aux = expressaoPilha.getPrimNodo();
        while (aux != null) {
            contaTamanho++;
            aux = aux.proxNodo;
        }

        while (contaTamanho > 1) {

            efetuaCalculo();
            contaTamanho = 0;
            aux = expressaoPilha.getPrimNodo();
            while (aux.proxNodo != null) {
                contaTamanho++;
                aux = aux.proxNodo;
            }
        }

        return expressaoPilha.getPrimNodo().getDado();
    }

    public void efetuaCalculo() {

        Nodo aux = expressaoPilha.getPrimNodo();
        int guardaPosicaoParenteses = -1;
        int guardaPosicaoParentesesAux = -1;
        int contaPosicao = 0;
        guardaPosicaoParenteses = achaPosicaoParenteses("(");
        int posicaoOperadorMax = -1;
        int valorOperador = 0;

        if (guardaPosicaoParenteses == -1) {
            contaPosicao = 0;
            valorOperador = 0;
            posicaoOperadorMax = -1;
            while (aux != null) {
                if (verificaOperadores(aux.getDado())) {
                    if (verificaOperador(aux.getDado()) > valorOperador) {
                        valorOperador = verificaOperador(aux.getDado());
                        posicaoOperadorMax = contaPosicao;
                    }
                }
                aux = aux.proxNodo;
                contaPosicao++;
            }
        } else {
            aux = expressaoPilha.getPrimNodo();

            while (aux != null) {
                if (contaPosicao == guardaPosicaoParenteses) {
                    guardaPosicaoParentesesAux = achaPosicaoFechaParenteses(")", guardaPosicaoParenteses);
                    aux = aux.proxNodo;
                    valorOperador = 0;
                    posicaoOperadorMax = -1;
                    for (int i = contaPosicao + 1; i < guardaPosicaoParentesesAux; i++) {
                        if (verificaOperadores(aux.getDado())) {
                            if (verificaOperador(aux.getDado()) > valorOperador) {
                                valorOperador = verificaOperador(aux.getDado());
                                posicaoOperadorMax = i;
                            }
                        }
                        aux = aux.proxNodo;
                    }
                }
                aux = aux.proxNodo;
                contaPosicao++;
            }
        }
        int contaPosicaoOperador = 0;
        String operando1 = "";
        String operando2 = "";
        String operador = "";
        aux = expressaoPilha.getPrimNodo();
        if (posicaoOperadorMax != -1) {
            while (aux != null) {
                if (contaPosicaoOperador == posicaoOperadorMax - 1) {
                    operando1 = aux.getDado();
                }
                if (contaPosicaoOperador == posicaoOperadorMax) {
                    operador = aux.getDado();
                }
                if (contaPosicaoOperador == posicaoOperadorMax + 1) {
                    operando2 = aux.getDado();
                }
                aux = aux.proxNodo;
                contaPosicaoOperador++;
            }

            if (confereTipoNumero(operando1, operando2)) {
                int resultado = escolheOperacao(operador, operando1, operando2);
                Integer result = resultado;
                desempilhaOperacaoRealizada(result, posicaoOperadorMax);
            } else {
                double resultado = escolheOperacaoDouble(operador, operando1, operando2);
                Double result = resultado;
                desempilhaOperacaoRealizada(result, posicaoOperadorMax);
            }
        } else if (guardaPosicaoParenteses != -1 && guardaPosicaoParentesesAux != -1) {
            desempilhaParentesesSemOperador(guardaPosicaoParenteses, guardaPosicaoParentesesAux);
        }
    }

    public boolean confereTipoNumero(String operador1, String operador2) {

        if (operador1.matches("[0-9]*") && operador2.matches("[0-9]*")) {

            return true;
        }
        return false;
    }

    public void desempilhaParentesesSemOperador(int posicaoParentesesAbre, int posicaoParentesesFecha) {
        Nodo aux = expressaoPilha.getPrimNodo();
        Nodo segundoAuxiliar;
        int contaPercorre = 0;
        Pilha auxPilha = new Pilha();
        String numeroUnico = "";

        while (aux != null) {
            if (posicaoParentesesAbre == contaPercorre) {
                aux = aux.proxNodo;
                contaPercorre++;
                numeroUnico = aux.getDado();
            }
            aux = aux.proxNodo;
            contaPercorre++;
        }

        int tamanho = contaPercorre - 1;
        int elementosDepois = 0;

        for (int i = tamanho; i > posicaoParentesesFecha; i--) {
            auxPilha.insereFim(expressaoPilha.removeFim());
            elementosDepois++;
        }

        for (int i = tamanho - elementosDepois; i >= posicaoParentesesAbre; i--) {
            expressaoPilha.removeFim();
        }

        expressaoPilha.insereFim(numeroUnico);

        int tamanhoPilhaAux = 0;
        Nodo percorreAuxiliar = auxPilha.getPrimNodo();
        while (percorreAuxiliar != null) {

            percorreAuxiliar = percorreAuxiliar.proxNodo;
            tamanhoPilhaAux++;
        }

        for (int i = tamanhoPilhaAux; i > 0; i--) {
            expressaoPilha.insereFim(auxPilha.removeFim());
        }

    }

    public void desempilhaOperacaoRealizada(Object result, int posicaoOperadorMax) {
        Nodo aux = expressaoPilha.getPrimNodo();
        Pilha auxPilha = new Pilha();
        int contaPercorre = 0;

        boolean parentesesAntes = false;
        boolean parentesesDepois = false;

        int posicaoParentesesesAntes = -1;
        int posicaoParentesesesDepois = -1;

        while (aux != null) {
            if (contaPercorre == posicaoOperadorMax - 2) {
                if (aux.getDado().equals("(")) {
                    parentesesAntes = true;
                    posicaoParentesesesAntes = contaPercorre;
                }
            }
            if (contaPercorre == posicaoOperadorMax + 2) {
                if (aux.getDado().equals(")")) {
                    parentesesDepois = true;
                    posicaoParentesesesDepois = contaPercorre;
                }
            }

            aux = aux.proxNodo;
            contaPercorre++;
        }
        int tamanho = contaPercorre - 1;
        contaPercorre = 0;

        if (parentesesDepois && parentesesAntes) {
            int elementosDepois = 0;
            for (int i = tamanho; i > posicaoParentesesesDepois; i--) {

                auxPilha.insereFim(expressaoPilha.removeFim());
                elementosDepois++;
            }
            for (int i = tamanho - elementosDepois; i >= posicaoParentesesesAntes; i--) {
                expressaoPilha.removeFim();
            }
            expressaoPilha.insereFim(result.toString());

        } else {
            int elementosDepois = 0;
            for (int i = tamanho; i > posicaoOperadorMax + 1; i--) {

                auxPilha.insereFim(expressaoPilha.removeFim());
                elementosDepois++;
            }
            for (int i = tamanho - elementosDepois; i >= posicaoOperadorMax - 1; i--) {
                expressaoPilha.removeFim();
            }
            expressaoPilha.insereFim(result.toString());
        }

        Nodo percorreAuxiliar = auxPilha.getPrimNodo();
        int tamanhoPilhaAux = 0;
        while (percorreAuxiliar != null) {

            percorreAuxiliar = percorreAuxiliar.proxNodo;
            tamanhoPilhaAux++;
        }

        for (int i = tamanhoPilhaAux; i > 0; i--) {
            expressaoPilha.insereFim(auxPilha.removeFim());
        }

    }

    public int achaPosicaoParenteses(String str) {
        int guardaPosicaoParenteses = -1;
        int contaPosicao = 0;

        Nodo aux = expressaoPilha.getPrimNodo();
        while (aux != null) {
            if (aux.getDado().equals(str)) {
                guardaPosicaoParenteses = contaPosicao;
            }
            aux = aux.proxNodo;
            contaPosicao++;
        }
        return guardaPosicaoParenteses;
    }

    public int achaPosicaoFechaParenteses(String str, int posicaoAbreParenteses) {
        int guardaPosicaoParenteses = -1;
        int contaPosicao = 0;

        Nodo aux = expressaoPilha.getPrimNodo();
        while (aux != null) {
            if (aux.getDado().equals(str) && posicaoAbreParenteses < contaPosicao) {
                return contaPosicao;
            }
            aux = aux.proxNodo;
            contaPosicao++;
        }
        return guardaPosicaoParenteses;
    }

    public int verificaOperador(String str) {
        switch (str) {
            case "^":
                return 3;
            case "*":
                return 2;
            case "/":
                return 2;
            case "+":
                return 1;
            case "-":
                return 1;
        }

        return -1;
    }

    public int escolheOperacao(String operador, String operando1, String operando2) {
        int resultado = -222222222;
        switch (operador) {
            case "^":
                resultado = OperacoesMatematica.calculaPotenciacao(operando1, operando2);
                break;
            case "*":
                resultado = OperacoesMatematica.calculaMultiplicacao(operando1, operando2);
                break;
            case "/":
                resultado = OperacoesMatematica.calculaDivisao(operando1, operando2);
                break;
            case "+":
                resultado = OperacoesMatematica.calculaSoma(operando1, operando2);
                break;
            case "-":
                resultado = OperacoesMatematica.calculaSubtracao(operando1, operando2);
                break;
        }
        return resultado;
    }

    public double escolheOperacaoDouble(String operador, String operando1, String operando2) {
        double resultado = -222222222;
        switch (operador) {
            case "^":
                resultado = OperacoesMatematica.calculaPotenciacaoDouble(operando1, operando2);
                break;
            case "*":
                resultado = OperacoesMatematica.calculaMultiplicacaoDouble(operando1, operando2);
                break;
            case "/":
                resultado = OperacoesMatematica.calculaDivisaoDouble(operando1, operando2);
                break;
            case "+":
                resultado = OperacoesMatematica.calculaSomaDouble(operando1, operando2);
                break;
            case "-":
                resultado = OperacoesMatematica.calculaSubtracaoDouble(operando1, operando2);
                break;
        }
        return resultado;
    }
}
