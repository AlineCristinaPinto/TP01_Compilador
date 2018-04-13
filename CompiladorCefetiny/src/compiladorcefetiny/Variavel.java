package compiladorcefetiny;
/**
 *
 * @author Aline, Eduardo Cotta, Luiz, Pedro Lucas e Ruan
 */
public class Variavel {
    private String name;
    private String type;
    private Object value;

    public Variavel(){}
    
    public Variavel(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

}
