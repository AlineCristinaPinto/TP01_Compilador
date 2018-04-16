package analisadorexpressoes;

class Nodo {

    private String dado;
    public Nodo proxNodo;

    public Nodo(String obj) {
        this(obj, null);
    }

    public Nodo(String obj, Nodo nodo) {
        dado = obj;
        proxNodo = nodo;
    }

    public String getDado() {
        return dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }

    public Nodo getProx() {
        return proxNodo;
    }
}
