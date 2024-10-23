package aed;
import java.util.Stack;
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo raiz;
    private int tamaño;

    private class Nodo {
        private T valor;
        private Nodo izq;
        private Nodo der;
        private Nodo padre;
    
        public Nodo (T valor, Nodo padre){
            this.valor = valor;
            this.izq = null;
            this.der =null;
            this.padre =padre;

        }
    }
    public ABB() {
        this.raiz = null;
        this.tamaño = 0;
    }

    public int cardinal() {
        return this.tamaño;
    }

    public T minimo(){
        if (this.raiz == null){
            return null;
        }
        Nodo actual = raiz;
        while (actual.izq != null){
            actual = actual.izq;
        }
        return actual.valor;
    }

    public T maximo(){
        if(this.raiz == null){
            return null;
        }
        Nodo actual = raiz;
        while(actual.der != null){
            actual = actual.der;
        }
        return actual.valor;
    }

    public void insertar(T elem){
        if(this.raiz == null) {
            raiz = new Nodo(elem, null);
            tamaño ++;
        } else {
            insertarRec(raiz, elem);
        }
    }
    
    private void insertarRec(Nodo actual, T elem){
        if(elem.compareTo(actual.valor) < 0) {
            if(actual.izq ==null){
                actual.izq = new Nodo(elem, actual);
                tamaño++;
            } else {
                insertarRec(actual.izq, elem);
            }
        } else if (elem.compareTo(actual.valor) > 0) {
            if(actual.der == null){
                actual.der = new Nodo(elem, actual);
                tamaño++;
            } else{
                insertarRec(actual.der, elem);
            }
        }
    }

    public boolean pertenece(T elem){
        return perteneceRec(raiz, elem);
    }

    private boolean perteneceRec(Nodo actual, T elem){
        if(actual == null){
            return false;

        }
        if(elem.compareTo(actual.valor)== 0){
            return true;
        } else if (elem.compareTo(actual.valor)> 0){
            return perteneceRec(actual.der, elem);
        } else{
            return perteneceRec(actual.izq, elem);
        }
    }

    public void eliminar(T elem){
        Nodo actual = this.raiz;
        while(actual != null && !actual.valor.equals(elem)){
            if(elem.compareTo(actual.valor)<0){
                actual =actual.izq;

            } else{
                actual = actual.der;
            }
        }
            if(actual == null) return;

            if(actual.izq == null){
                intercambiar(actual,actual.der);

            } else if(actual.der == null){
                intercambiar(actual,actual.izq);
            } else{
                Nodo sucesor = minimoRecursivo(actual.der);
                if(sucesor != actual.der){
                    intercambiar(sucesor, sucesor.der);
                    sucesor.der = actual.der;
                    sucesor.der.padre = sucesor;
                }
                intercambiar(actual,sucesor);
                sucesor.izq = actual.izq;
                sucesor.izq.padre =sucesor;
            }
        
        tamaño--;
    }

    private Nodo minimoRecursivo(Nodo nodo) {
        while (nodo.izq != null) {
            nodo = nodo.izq;
        }
        return nodo;
    }
    
    private void intercambiar(Nodo u, Nodo v) {
        if (u.padre == null) {
            raiz = v;
        } else if (u == u.padre.izq) {
            u.padre.izq = v;
        } else {
            u.padre.der = v;
        }
        if (v != null) {
            v.padre = u.padre;
        }
    }
    

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        toStringRec(raiz, sb);
        if (sb.length() > 1){
            sb.setLength(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
    private void toStringRec(Nodo actual, StringBuilder sb){
        if(actual!= null){
            toStringRec(actual.izq, sb);
            sb.append(actual.valor).append(",");
            toStringRec(actual.der, sb);
        }

    }

    private class ABB_Iterador implements Iterador<T> {
        private Stack<Nodo> pila;

        public ABB_Iterador(){
            pila = new Stack<>();
            Nodo actual = raiz;
            while(actual!= null){
                pila.push(actual);
                actual =actual.izq;

            }
        }

        public boolean haySiguiente() {
            return !pila.isEmpty();
        }
    
        public T siguiente() {
            if(!haySiguiente()){
                return null;
            }
            Nodo nodoActual = pila.pop();
            T resultado = nodoActual.valor;

            if (nodoActual.der != null){
                Nodo temp = nodoActual.der;
                while(temp!= null){
                    pila.push(temp);
                    temp = temp.izq;
                }
            }
            return resultado;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
