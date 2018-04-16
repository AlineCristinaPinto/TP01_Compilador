package analisadorexpressoes;

public class Pilha {

    private Nodo primNodo;
    private Nodo ultNodo;

    public Pilha() {
        primNodo = ultNodo = null;
    }

    public Nodo getPrimNodo() {
        return primNodo;
    }

    public Nodo getUltNodo() {
        return ultNodo;
    }

    public void insereFim(String itemInserido) {
        if (Vazio()) {
            primNodo = ultNodo = new Nodo(itemInserido);
        } else {
            ultNodo = ultNodo.proxNodo = new Nodo(itemInserido);
        }
    }

    public String removeFim() {
        if (Vazio()) {
            return "";
        }
        String itemRemovido = ultNodo.getDado();
        if (primNodo == ultNodo) {
            primNodo = ultNodo = null;
        } else {
            Nodo dadoAtual = primNodo;
            while (dadoAtual.proxNodo != ultNodo) {
                dadoAtual = dadoAtual.proxNodo;
            }
            ultNodo = dadoAtual;
            dadoAtual.proxNodo = null;
        }
        return itemRemovido;
    }

    public boolean Vazio() {
        return primNodo == null;
    }
}
