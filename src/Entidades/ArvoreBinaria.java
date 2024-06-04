package Entidades;

import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinaria<T extends Comparable<T>> {
    class Nodo {
        public T elemento;
        public Nodo esquerdo;
        public Nodo direito;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.esquerdo = null;
            this.direito = null;
        }
    }

    public Nodo raiz;
    public int nElementos;

    public ArvoreBinaria() {
        this.raiz = null;
        this.nElementos = 0;
    }

    public boolean estaVazia() {
        return this.raiz == null;
    }

    public void imprimePreOrdem() {
        this.preOrdem(this.raiz);
        System.out.println();
    }

    public void imprimePosOrdem() {
        this.posOrdem(this.raiz);
        System.out.println();
    }

    public void imprimeEmOrdem() {
        this.emOrdem(this.raiz);
        System.out.println();
    }

    public void imprimeEmLargura() {
        if (this.estaVazia()) {
            return;
        }

        Queue<Nodo> fila = new LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            Nodo atual = fila.poll();
            System.out.print(atual.elemento + " ");

            if (atual.esquerdo != null) {
                fila.add(atual.esquerdo);
            }

            if (atual.direito != null) {
                fila.add(atual.direito);
            }
        }
        System.out.println();
    }

    public void preOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        System.out.print(nodo.elemento + " ");
        this.preOrdem(nodo.esquerdo);
        this.preOrdem(nodo.direito);
    }

    public void posOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        this.posOrdem(nodo.esquerdo);
        this.posOrdem(nodo.direito);
        System.out.print(nodo.elemento + " ");
    }

    public void emOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        this.emOrdem(nodo.esquerdo);
        System.out.print(nodo.elemento + " ");
        this.emOrdem(nodo.direito);
    }

    public Nodo busca(T elemento) {
        return buscaRecursivo(this.raiz, elemento);
    }

    private Nodo buscaRecursivo(Nodo nodo, T elemento) {
        if (nodo == null || nodo.elemento.equals(elemento)) {
            return nodo;
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            return buscaRecursivo(nodo.esquerdo, elemento);
        } else {
            return buscaRecursivo(nodo.direito, elemento);
        }
    }

    public boolean insereEsquerda(T elemento, T pai) {
        Nodo novo = new Nodo(elemento);

        if (this.estaVazia()) {
            this.raiz = novo;
            this.nElementos++;
            return true;
        }

        Nodo nodoPai = this.busca(pai);
        if (nodoPai != null) {
            if (nodoPai.esquerdo == null) {
                nodoPai.esquerdo = novo;
                this.nElementos++;
                return true;
            } else {
                System.out.println("Elemento ja tem filho esquerdo!");
                return false;
            }
        } else {
            System.out.println("Elemento nao existe na arvore!");
            return false;
        }
    }

    public boolean insereDireita(T elemento, T pai) {
        Nodo novo = new Nodo(elemento);

        if (this.estaVazia()) {
            this.raiz = novo;
            this.nElementos++;
            return true;
        }

        Nodo nodoPai = null;
        if (pai != null) {
            nodoPai = this.busca(pai);
        }

        if (nodoPai != null) {
            if (nodoPai.direito == null) {
                nodoPai.direito = novo;
                this.nElementos++;
                return true;
            } else {
                System.out.println("Elemento ja tem filho direito!");
                return false;
            }
        } else if (pai == null) { // Inserção inicial na árvore
            this.raiz = novo;
            this.nElementos++;
            return true;
        } else {
            System.out.println("Elemento nao existe na arvore!");
            return false;
        }
    }

    public static void main(String[] args) {
        ArvoreBinaria<Integer> arvore = new ArvoreBinaria<>();

        // raiz
        arvore.insereDireita(5, null);

        // nivel 1
        arvore.insereEsquerda(3, 5);
        arvore.insereDireita(8, 5);

        // nivel 2
        arvore.insereEsquerda(1, 3);
        arvore.insereDireita(4, 3);
        arvore.insereEsquerda(7, 8);
        arvore.insereDireita(9, 8);

        // nivel 3
        arvore.insereDireita(2, 1);
        arvore.insereEsquerda(6, 7);

        System.out.print("Em largura:\t");
        arvore.imprimeEmLargura();
        System.out.print("Pre-ordem:\t");
        arvore.imprimePreOrdem();
        System.out.print("Pos-ordem:\t");
        arvore.imprimePosOrdem();
        System.out.print("Em ordem:\t");
        arvore.imprimeEmOrdem();
    }
}
