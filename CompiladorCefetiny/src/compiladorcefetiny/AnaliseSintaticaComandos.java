package compiladorcefetiny;

/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class AnaliseSintaticaComandos {

    private String tipoComandoLido;
    private String expressaoPreparada;
    private CanalEntrada input;
    private int contaLinhas = 0;
    private MontaComandos montaComandos = new MontaComandos();

    public AnaliseSintaticaComandos(CanalEntrada input) {
        this.expressaoPreparada = "";
        this.tipoComandoLido = "";
        this.input = input;

    }

    public void terceiraAnaliseSintatica() {
        identificaComandos(true);
    }

    public PseudoComando identificaComandos(boolean primeiroIdentifica) {
        PseudoComando tempComando = null;
        String palavraLida = "";
        char caractere = input.get();
        boolean esperaComandoAtribuicao = false;
        boolean palavraPreenchida = false;
        while (caractere != 0) {
            String tipoCaractere = identificaTipoCaractere(caractere);
            if ((tipoCaractere.equals("parenteses-abre") || tipoCaractere.equals("espaco")) && !esperaComandoAtribuicao && palavraPreenchida) {
                input.unget();
                int retornoTipo = isComando(identificaPalavrasReservadas(palavraLida));
                switch (retornoTipo) {
                    case 0:
                        if (palavraLida.equals("endif")) {
                            tempComando = new PseudoComando(null,"endif","endif",false);
                            return tempComando;
                        } else if (palavraLida.equals("endwhile")) {
                            tempComando = new PseudoComando(null,"endwhile","endwhile",false);
                            return tempComando;
                        } else if (palavraLida.equals("endfor")) {
                            tempComando = new PseudoComando(null,"endfor","endfor",false);
                            return tempComando;
                        } else {
                            //Exception palavra inserida em uma expressão incorreta!
                            System.out.println("1");
                            break;
                        }
                    case 1:
                        esperaComandoAtribuicao = true;
                        break;
                    case 2:
                        if (palavraLida.equals("if")) {
                            tempComando = analiseComandoIf(palavraLida);
                            if(primeiroIdentifica){
                                //monta Comando
                            }else{
                                return tempComando;
                            }
                            palavraLida = "";
                            palavraPreenchida = false;
                        } else if (palavraLida.equals("for")) {
                            tempComando = analiseComandoFor(palavraLida);
                            if(primeiroIdentifica){
                                //monta Comando
                            }else{
                                return tempComando;
                            }
                            palavraLida = "";
                            palavraPreenchida = false;
                        } else if (palavraLida.equals("while")) {
                            tempComando = analiseComandoWhile(palavraLida);
                            if(primeiroIdentifica){
                                //monta Comando
                            }else{
                                return tempComando;
                            }
                            palavraLida = "";
                            palavraPreenchida = false;
                        } else if (palavraLida.equals("print")) {
                            tempComando = analiseComandoPrint(palavraLida);
                            if(primeiroIdentifica){
                                //monta Comando
                            }else{
                                return tempComando;
                            }
                            palavraLida = "";
                        } else if (palavraLida.equals("println")) {
                            tempComando = analiseComandoPrintln(palavraLida);
                            if(primeiroIdentifica){
                                //monta Comando
                            }else{
                                return tempComando;
                            }
                            palavraLida = "";
                            palavraPreenchida = false;
                        } else if (palavraLida.equals("readint")) {
                            tempComando = analiseComandoReadint(palavraLida);
                            if(primeiroIdentifica){
                                //monta Comando
                            }else{
                                return tempComando;
                            }
                            palavraLida = "";
                            palavraPreenchida = false;
                        } else if (palavraLida.equals("end")) {
                            if(primeiroIdentifica){
                                //fim
                            }else{
                                //Exception
                                System.out.println("Fim de programa em lugar errrado!");
                            }
                        }
                        break;
                }

            } else if (tipoCaractere.equals("dois-pontos") || (tipoCaractere.equals("dois-pontos") && esperaComandoAtribuicao)) {
                esperaComandoAtribuicao = false;
                char caractereTemp = input.get();
                if (caractereTemp == '=') {
                    palavraLida += caractere;
                    palavraLida += caractereTemp;
                    tempComando = analiseComandoAtribuicao(palavraLida);
                    if (primeiroIdentifica) {
                        //montaComandos.montaLista(tempComando, null, false);
                    }else{
                        return tempComando;
                    }
                    palavraLida = "";
                    palavraPreenchida = false;
                } else {
                    //Excecao
                    System.out.println("2");
                }
            } else if ((tipoCaractere.equals("letra") || tipoCaractere.equals("numero")) || (tipoCaractere.equals("espaco") && esperaComandoAtribuicao)) {
                palavraPreenchida = true;
                palavraLida += caractere;
            } else {
                //Exception
                System.out.println("3");
                System.out.println(caractere);
            }
            //System.out.println( palavraLida);
            caractere = input.get();
        }

        if (palavraPreenchida && primeiroIdentifica) {
            int retornoTipo = isComando(identificaPalavrasReservadas(palavraLida));
            if (retornoTipo == 2) {
                if (palavraLida.equals("end")) {
                    //Comando final
                } else {
                    //Erro, programa não tem fim!!
                    System.out.println("4");
                }
            }
        }
        return tempComando;
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

    private String identificaPalavrasReservadas(String palavraObservada) {
        String tipoPalavra;
        String vetorPalavras[] = {"if", "then", "endif", "else", "while", "do", "endwhile", "for", "to", "downto", "endfor", "print", "println", "readint", "sqrt", "not", "true", "false", "or", "and", "mod", "div", "end"};

        for (String palavraChecada : vetorPalavras) {
            if (palavraObservada.equals(palavraChecada)) {
                return palavraChecada;
            }
        }

        tipoPalavra = "variavel";
        int verifica = validaVariavel(palavraObservada);
        if (verifica == 1) {
            //Excecao
            System.out.println("Caracteres inválidos!");
            System.out.println(palavraObservada);
        } else if (verifica == 2) {
            //Exceção
            System.out.println("Caracteres inválidos!");
        }
        return tipoPalavra;
    }

    private int validaVariavel(String palavraObservada) {
        char arrayTemp[] = palavraObservada.toCharArray();
        boolean existeNumeroPrimeiro = false;

        existeNumeroPrimeiro = isNumero(String.valueOf(arrayTemp[0]));

        if (existeNumeroPrimeiro) {
            for (char caractere : arrayTemp) {
                if (identificaTipoCaractere(caractere).equals("letra")) {
                    return 1;
                }
            }
        }
        for (char caractere : arrayTemp) {
            if (!identificaTipoCaractere(caractere).equals("letra") && !identificaTipoCaractere(caractere).equals("numero")) {
                return 2;
            }
        }
        return 0;
    }

    private int isComando(String tipo) {
        switch (tipo) {
            case "end":
            case "if":
            case "for":
            case "while":
            case "print":
            case "println":
            case "readint":
                return 2;
            case "variavel":
                return 1;
            default:
                return 0;
        }
    }

    private String analiseExpressao(String palavraLida, int indexParada, boolean expressaoLimitada) {
        String comandoLido = "";
        String palavraParcial = "";
        String tipoCaractere = "";
        String bufferVariavel = "";
        boolean isComando = true;
        char caractere = input.get();

        boolean variavelAnterior = false;
        boolean operadorBinarioAnterior = false;
        boolean operadorUnarioAnterior = false;
        boolean numeroAnterior = false;
        boolean operadorAnterior = false;
        boolean expressaoAnterior = false;
        boolean constanteBoolAnterior = false;
        boolean existeAspas = false;

        int contaParentesesAbre = 0;
        int contaParentesesFecha = 0;
        int contaAspas = 0;
        int contaIteraçoes = 0;

        tipoCaractere = identificaTipoCaractere(caractere);
        while (((caractere != 0) && isComando)) {
            if (tipoCaractere.equals("parenteses-abre")) {
                if (!(palavraParcial.equals(""))) {
                    String tempTipoPalavra = identificaPalavrasReservadas(palavraParcial);
                    int retornoComando = isComando(tempTipoPalavra);

                    if (retornoComando == 0 && !variavelAnterior) {
                        if (tempTipoPalavra.equals("not") || tempTipoPalavra.equals("sqrt")) {
                            input.unget();
                            String verificada = verificaOperadorUnario();
                            operadorUnarioAnterior = true;
                            operadorBinarioAnterior = false;
                            numeroAnterior = false;
                            operadorAnterior = false;
                            palavraParcial += verificada;
                        } else if (!(comandoLido.equals("")) && !(operadorAnterior) && !(operadorBinarioAnterior)) {
                            isComando = false;
                            System.out.println("nnn");
                            break;
                        }
                    } else if (retornoComando == 2) {
                        if (!expressaoLimitada) {
                            if (palavraParcial.equals("if") || palavraParcial.equals("print") || palavraParcial.equals("println") || palavraParcial.equals("while") || palavraParcial.equals("readint")) {
                                isComando = false;
                                break;
                            }
                        }
                    } else {
                        //Exception
                        System.out.println("5" + "   " + palavraParcial);
                    }

                } else {
                    palavraParcial += caractere;
                    contaParentesesAbre++;
                    expressaoAnterior = true;
                }
            } else if (tipoCaractere.equals("parenteses-fecha")) {
                palavraParcial += caractere;
                contaParentesesFecha++;
                numeroAnterior = false;
            } else if (tipoCaractere.equals("aspas")) {
                if (tipoCaractere.equals("aspas")) {
                    existeAspas = !existeAspas;
                }
                palavraParcial += caractere;
                contaAspas++;
            } else if (tipoCaractere.equals("op-aritmetico") || (tipoCaractere.equals("op-logico") && !operadorAnterior) || (tipoCaractere.equals("igual") && !operadorAnterior)) {

                if (!expressaoAnterior && !variavelAnterior) {
                    String tempTipoPalavra = identificaPalavrasReservadas(palavraParcial);
                    int retornoComando = isComando(tempTipoPalavra);

                    if (retornoComando == 0) {
                        if (tempTipoPalavra.equals("true") || tempTipoPalavra.equals("false")) {
                            if (tipoCaractere.equals("op-aritmetico")) {
                                //Exception
                                System.out.println("6");
                            } else {
                                constanteBoolAnterior = true;
                            }
                        } else {
                            //Exception
                            System.out.println("7");
                        }
                    } else if (retornoComando == 1) {
                        if (variavelAnterior || numeroAnterior || expressaoAnterior || constanteBoolAnterior || operadorUnarioAnterior) {
                            //Exception, variavel fora de contexto
                            System.out.println("Erro, sintaxe incorreta0");
                        } else {
                            variavelAnterior = true;
                        }
                    } else {
                        //Exception
                        System.out.println("8");
                    }
                }
                if (variavelAnterior || numeroAnterior || expressaoAnterior || constanteBoolAnterior || operadorUnarioAnterior) {
                    variavelAnterior = false;
                    numeroAnterior = false;
                    expressaoAnterior = false;
                    constanteBoolAnterior = false;
                    operadorUnarioAnterior = false;
                    operadorAnterior = true;

                    palavraParcial += caractere;
                    comandoLido += palavraParcial;

                    palavraParcial = "";

                } else {
                    //Exception
                    System.out.println("9");
                }
                if (contaParentesesAbre - contaParentesesFecha == 0) {
                    expressaoAnterior = false;
                }
            } else if ((tipoCaractere.equals("espaco") || tipoCaractere.equals("quebra-linha")) && !palavraParcial.equals("") && !existeAspas) {
                String tempTipoPalavra = identificaPalavrasReservadas(palavraParcial);
                int retornoComando = isComando(tempTipoPalavra);
                //System.out.println(palavraParcial + "  "+ retornoComando);
                if (tipoCaractere.equals("quebra-linha")) {
                    contaLinhas++;
                }
                if (numeroAnterior) {
                    comandoLido += palavraParcial;
                    palavraParcial = "";
                } else if (retornoComando == 1) {
                    if (!variavelAnterior && !expressaoAnterior && !constanteBoolAnterior && !operadorUnarioAnterior && !numeroAnterior) {
                        variavelAnterior = true;
                        operadorAnterior = false;
                        operadorBinarioAnterior = false;
                        expressaoAnterior = false;
                        constanteBoolAnterior = false;
                        operadorUnarioAnterior = false;
                        numeroAnterior = false;
                        bufferVariavel = palavraParcial;
                        palavraParcial = "";
                    } else {
                        System.out.println("Erro, sintaxe incorreta");
                    }
                } else if (retornoComando == 0) {
                    if (palavraParcial.equals("and") || palavraParcial.equals("or") || palavraParcial.equals("mod") || palavraParcial.equals("div")) {
                        if ((variavelAnterior || operadorUnarioAnterior || constanteBoolAnterior || numeroAnterior || expressaoAnterior) && !operadorBinarioAnterior) {
                            operadorBinarioAnterior = true;
                            expressaoAnterior = false;
                            constanteBoolAnterior = false;
                            operadorUnarioAnterior = false;
                            numeroAnterior = false;
                            variavelAnterior = false;
                            operadorAnterior = false;

                            String tempStr = " " + palavraParcial + " ";
                            comandoLido += tempStr;
                            palavraParcial = "";
                        } else {
                            //Erro, sintaxe errada
                            System.out.println("10");
                        }
                    }
                } else {
                    if (retornoComando == 2 && !expressaoLimitada) {
                        isComando = false;
                        break;
                    }
                    System.out.println("11");
                }
            } else if (tipoCaractere.equals("numero")) {
                if (comandoLido.equals("") || operadorBinarioAnterior || operadorAnterior || numeroAnterior) {
                    if (palavraParcial.equals("")) {
                        numeroAnterior = true;
                    }
                    palavraParcial += caractere;
                } else {
                    //Exception 
                    System.out.println("Erro, sintaxe incorreta1");
                }
            } else if (tipoCaractere.equals("letra")) {
                palavraParcial += caractere;
            } else if (tipoCaractere.equals("ponto")) {
                if (numeroAnterior) {
                    palavraParcial += caractere;
                } else {
                    //Exception, ponto fora do lugar esperado
                    System.out.println("Erro, sintaxe incorreta2");
                }
            } else if (tipoCaractere.equals("igual")) {
                if (operadorAnterior || variavelAnterior || numeroAnterior || constanteBoolAnterior || expressaoAnterior || operadorUnarioAnterior) {
                    palavraParcial += caractere;
                }
            } else if (tipoCaractere.equals("dois-pontos")) {
                char proxCaractere = input.get();
                if(proxCaractere == '='){
                    //Voltar para última variável
                    isComando = false;
                    break;
                }else{
                    System.out.println("Erro, sintaxe incorreta2.5");
                }
            } else {
                if (!tipoCaractere.equals("espaco") && !tipoCaractere.equals("quebra-linha")) {
                    //Exceção caractere não esperado
                    System.out.println("Erro, sintaxe incorreta3" + "   " + (int) caractere);
                }
            }
            caractere = input.get();
            tipoCaractere = identificaTipoCaractere(caractere);
            contaIteraçoes++;
            if ((contaIteraçoes == indexParada) && expressaoLimitada) {
                break;
            }
        }

        //Fim while
        if (contaParentesesAbre - contaParentesesFecha != 0) {
            //Exception, parenteses errados
            System.out.println("eduardo");
        }
        if (contaAspas % 2 != 0) {
            //Exception, aspas errados
            System.out.println("Aspas erradas");
        }
        int tamanhoPalavra = palavraParcial.length();

        if (tamanhoPalavra == 1 && isComando) {
            char ultimoChar = palavraParcial.charAt(0);
            String tipoUltimoChar = identificaTipoCaractere(ultimoChar);

            if (tipoUltimoChar.equals("op-logico") || tipoUltimoChar.equals("op-aritmetico") || tipoUltimoChar.equals("ponto")
                    || tipoUltimoChar.equals("dois-pontos") || tipoUltimoChar.equals("parenteses-abre") || tipoUltimoChar.equals("igual")) {
                //Exception, sintaxe errada, caractere (inserir) sem função no comando!
                System.out.println("Erro, sintaxe incorreta4");
            } else if (tipoUltimoChar.equals("letra")) {
                if (variavelAnterior || operadorUnarioAnterior || numeroAnterior || expressaoAnterior || constanteBoolAnterior) {
                    //exception, sintaxe da expressão incorreta
                    System.out.println("Erro, sintaxe incorreta5");
                }
            }//Checar no teste

        } else if (tamanhoPalavra > 1 && isComando) {
            String tempTipoPalavra = identificaPalavrasReservadas(palavraParcial);
            int retornoComando = isComando(tempTipoPalavra);

            if (retornoComando != 1) {
                //Exception
                System.out.println("Erro, sintaxe incorreta6");
            } else {
                if (variavelAnterior || expressaoAnterior || constanteBoolAnterior || operadorUnarioAnterior || numeroAnterior) {
                    //Exception, variável inserida fora de contexto
                }
            }
        }
        if (isComando) {
            comandoLido += palavraParcial;
        }

        return palavraLida + comandoLido;
    }

    private boolean isNumero(String possivelNumero) {
        char arrayTemp[] = possivelNumero.toCharArray();

        for (char charTemp : arrayTemp) {
            if (!((charTemp > 47 && charTemp < 58) || charTemp == 46)) {
                return false;
            }
        }
        return true;
    }

    private PseudoComando analiseComandoAtribuicao(String palavraLida) {
        String retornoExpressao = analiseExpressao(palavraLida, 0, false);

        
        System.out.println(retornoExpressao);

        return null;
    }

    private PseudoComando analiseComandoIf(String palavraLida) {
        PseudoComando retornoIf;
        int retornoMaximo=0;
        //int retornoMaximo =  Método que acha parenteses final
        
        String retornoExpressao = analiseExpressao(palavraLida, retornoMaximo, true);
        
        palavraLida +='('+ retornoExpressao + ')';
        
        retornoIf = new PseudoComando(null, "if", palavraLida, false);
        
        PseudoComando tempComando = identificaComandos(false);
        
        while(!tempComando.getTipoComando().equals("endif")){
            retornoIf.preencheComando(tempComando);
            tempComando = identificaComandos(false);
        }
        
        return retornoIf;
    }

    private PseudoComando analiseComandoFor(String palavraLida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private PseudoComando analiseComandoWhile(String palavraLida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private PseudoComando analiseComandoPrint(String palavraLida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private PseudoComando analiseComandoPrintln(String palavraLida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private PseudoComando analiseComandoReadint(String palavraLida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String verificaOperadorUnario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
