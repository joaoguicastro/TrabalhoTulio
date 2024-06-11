package Entidades;

import java.io.BufferedWriter;
import java.io.IOException;

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

    class FilaDinamica {
        class NodoFila {
            public Nodo nodo;
            public NodoFila proximo;

            public NodoFila(Nodo nodo) {
                this.nodo = nodo;
                this.proximo = null;
            }
        }

        private NodoFila primeiro;
        private NodoFila ultimo;

        public FilaDinamica() {
            this.primeiro = null;
            this.ultimo = null;
        }

        public boolean estaVazia() {
            return this.primeiro == null;
        }

        public void enfileira(Nodo nodo) {
            NodoFila novoNodoFila = new NodoFila(nodo);
            if (this.ultimo != null) {
                this.ultimo.proximo = novoNodoFila;
            }
            this.ultimo = novoNodoFila;
            if (this.primeiro == null) {
                this.primeiro = novoNodoFila;
            }
        }

        public Nodo desenfileira() {
            if (this.estaVazia()) {
                return null;
            }
            Nodo nodo = this.primeiro.nodo;
            this.primeiro = this.primeiro.proximo;
            if (this.primeiro == null) {
                this.ultimo = null;
            }
            return nodo;
        }
    }

    public Nodo raiz;
    public int nElementos;

    public ArvoreBinaria() {
        this.raiz = null;
        this.nElementos = 0;
    }

    public int tamanho() {
        return this.nElementos;
    }

    public boolean estaVazia() {
        return this.raiz == null;
    }

    public void imprimeEmLargura() {
        if (this.estaVazia()) {
            return;
        }

        FilaDinamica fila = new FilaDinamica();
        fila.enfileira(this.raiz);

        while (!fila.estaVazia()) {
            Nodo atual = fila.desenfileira();
            System.out.print(atual.elemento + " ");

            if (atual.esquerdo != null) {
                fila.enfileira(atual.esquerdo);
            }

            if (atual.direito != null) {
                fila.enfileira(atual.direito);
            }
        }
        System.out.println();
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

    private void preOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        System.out.print(nodo.elemento + " ");
        this.preOrdem(nodo.esquerdo);
        this.preOrdem(nodo.direito);
    }

    private void posOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        this.posOrdem(nodo.esquerdo);
        this.posOrdem(nodo.direito);
        System.out.print(nodo.elemento + " ");
    }

    private void emOrdem(Nodo nodo) {
        if (nodo == null)
            return;

        this.emOrdem(nodo.esquerdo);
        System.out.print(nodo.elemento + " ");
        this.emOrdem(nodo.direito);
    }

    public void imprimeEmOrdemComIndices(TabelaHashGenerico<ListaSimplesmenteEncadeada<Integer>> tabelaHash,
            BufferedWriter writer) throws IOException {
        imprimeEmOrdemComIndices(this.raiz, tabelaHash, writer);
    }

    private void imprimeEmOrdemComIndices(Nodo nodo, TabelaHashGenerico<ListaSimplesmenteEncadeada<Integer>> tabelaHash,
            BufferedWriter writer) throws IOException {
        if (nodo == null)
            return;

        imprimeEmOrdemComIndices(nodo.esquerdo, tabelaHash, writer);

        writer.write(nodo.elemento + ": ");
        ListaSimplesmenteEncadeada<Integer> lista = tabelaHash.busca(nodo.elemento);
        if (lista != null) {
            ListaSimplesmenteEncadeada<Integer>.Nodo temp = lista.primeiro;
            while (temp != null) {
                writer.write(temp.valor + " ");
                temp = temp.proximo;
            }
        }
        writer.newLine();

        imprimeEmOrdemComIndices(nodo.direito, tabelaHash, writer);
    }

    public void insere(T elemento) {
        this.raiz = this.insere(elemento, this.raiz);
    }

    private Nodo insere(T elemento, Nodo nodo) {
        if (nodo == null) {
            this.nElementos++;
            return new Nodo(elemento);
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            nodo.esquerdo = this.insere(elemento, nodo.esquerdo);
        } else if (elemento.compareTo(nodo.elemento) > 0) {
            nodo.direito = this.insere(elemento, nodo.direito);
        }

        return nodo;
    }

    private Nodo maiorElemento(Nodo nodo) {
        while (nodo.direito != null) {
            nodo = nodo.direito;
        }
        return nodo;
    }

    private Nodo menorElemento(Nodo nodo) {
        while (nodo.esquerdo != null) {
            nodo = nodo.esquerdo;
        }
        return nodo;
    }

    public boolean remove(T elemento) {
        if (this.raiz == null) {
            System.out.println("Valor não encontrado");
            return false;
        }

        this.raiz = this.remove(elemento, this.raiz);
        return true;
    }

    private Nodo remove(T elemento, Nodo nodo) {
        if (nodo == null) {
            System.out.println("Valor não encontrado");
            return null;
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            nodo.esquerdo = this.remove(elemento, nodo.esquerdo);
        } else if (elemento.compareTo(nodo.elemento) > 0) {
            nodo.direito = this.remove(elemento, nodo.direito);
        } else {
            if (nodo.esquerdo == null) {
                this.nElementos--;
                return nodo.direito;
            } else if (nodo.direito == null) {
                this.nElementos--;
                return nodo.esquerdo;
            } else {
                Nodo substituto = this.menorElemento(nodo.direito);
                nodo.elemento = substituto.elemento;
                nodo.direito = this.remove(substituto.elemento, nodo.direito);
            }
        }

        return nodo;
    }

    public boolean busca(T elemento) {
        return this.busca(elemento, this.raiz);
    }

    private boolean busca(T elemento, Nodo nodo) {
        if (nodo == null) {
            return false;
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            return this.busca(elemento, nodo.esquerdo);
        } else if (elemento.compareTo(nodo.elemento) > 0) {
            return this.busca(elemento, nodo.direito);
        } else {
            return true;
        }
    }

    private int altura(Nodo nodo) {
        if (nodo == null) {
            return -1;
        }

        int alturaEsquerda = this.altura(nodo.esquerdo) + 1;
        int alturaDireita = this.altura(nodo.direito) + 1;

        return Math.max(alturaEsquerda, alturaDireita);
    }

    public int altura() {
        return this.altura(this.raiz);
    }

    public static void main(String[] args) {
        ArvoreBinaria<String> arvore = new ArvoreBinaria<>();

        // Inserindo elementos na árvore
        arvore.insere("mango");
        arvore.insere("banana");
        arvore.insere("apple");
        arvore.insere("peach");
        arvore.insere("grape");
        arvore.insere("cherry");
        arvore.insere("pear");

        System.out.print("Em largura:\t");
        arvore.imprimeEmLargura();
        System.out.print("Pre-ordem:\t");
        arvore.imprimePreOrdem();
        System.out.print("Pos-ordem:\t");
        arvore.imprimePosOrdem();
        System.out.print("Em ordem:\t");
        arvore.imprimeEmOrdem();

        System.out.println("Altura da árvore: " + arvore.altura());

        // Removendo um elemento da árvore
        arvore.remove("banana");
        System.out.print("Em largura após remover banana:\t");
        arvore.imprimeEmLargura();

        // Buscando elementos na árvore
        System.out.println("Busca por apple: " + arvore.busca("apple"));
        System.out.println("Busca por banana: " + arvore.busca("banana"));
    }
}
