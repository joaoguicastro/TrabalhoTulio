package Entidades;

public class ListaSimplesmenteEncadeada<T> {
    public class Nodo {
        public T valor;
        public Nodo proximo;

        public Nodo(T valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    public Nodo primeiro;
    public Nodo ultimo;
    public int tamanho;

    public ListaSimplesmenteEncadeada() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    public void insereInicio(T valor) {
        Nodo novoNodo = new Nodo(valor);
        if (primeiro == null) {
            primeiro = novoNodo;
            ultimo = novoNodo;
        } else {
            novoNodo.proximo = primeiro;
            primeiro = novoNodo;
        }
        tamanho++;
    }

    public void removeInicio() {
        if (primeiro == null) {
            throw new ExcecaoElementoNaoEncontrado("A lista esta vazia");
        } else {
            primeiro = primeiro.proximo;
            tamanho--;
            if (tamanho == 0) {
                ultimo = null;
            }
        }
    }

    public void insereFinal(T valor) {
        Nodo novoNodo = new Nodo(valor);
        if (ultimo == null) {
            primeiro = novoNodo;
            ultimo = novoNodo;
        } else {
            ultimo.proximo = novoNodo;
            ultimo = novoNodo;
        }
        tamanho++;
    }

    public void removeFinal() {
        if (ultimo == null) {
            throw new ExcecaoElementoNaoEncontrado("A lista esta vazia");
        } else {
            if (primeiro == ultimo) {
                primeiro = null;
                ultimo = null;
            } else {
                Nodo atual = primeiro;
                while (atual.proximo != ultimo) {
                    atual = atual.proximo;
                }
                atual.proximo = null;
                ultimo = atual;
            }
            tamanho--;
        }
    }

    public void inserePosicao(T valor, int posicao) {
        if (posicao < 0 || posicao > tamanho) {
            throw new ExcecaoPosicaoInvalida("Posicao invalida");
        }

        if (posicao == 0) {
            insereInicio(valor);
        } else if (posicao == tamanho) {
            insereFinal(valor);
        } else {
            Nodo novoNodo = new Nodo(valor);
            Nodo atual = primeiro;
            for (int i = 0; i < posicao - 1; i++) {
                atual = atual.proximo;
            }
            novoNodo.proximo = atual.proximo;
            atual.proximo = novoNodo;
            tamanho++;
        }
    }

    public void removePosicao(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new ExcecaoPosicaoInvalida("Posicao invalida");
        }

        if (posicao == 0) {
            removeInicio();
        } else if (posicao == tamanho - 1) {
            removeFinal();
        } else {
            Nodo atual = primeiro;
            for (int i = 0; i < posicao - 1; i++) {
                atual = atual.proximo;
            }
            atual.proximo = atual.proximo.proximo;
            tamanho--;
        }
    }

    public boolean contem(T valor) {
        Nodo atual = primeiro;
        while (atual != null) {
            if (atual.valor.equals(valor)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public void imprimirLista() {
        Nodo atual = primeiro;
        System.out.print("Lista: ");
        while (atual != null) {
            System.out.print(atual.valor + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListaSimplesmenteEncadeada<Integer> lista = new ListaSimplesmenteEncadeada<>();
        lista.insereInicio(3);
        lista.insereInicio(2);
        lista.insereInicio(1);
        lista.imprimirLista(); // Saida: 1 2 3

        lista.insereFinal(4);
        lista.insereFinal(5);
        lista.imprimirLista(); // Saida: 1 2 3 4 5

        lista.inserePosicao(10, 2);
        lista.imprimirLista(); // Saida: 1 2 10 3 4 5

        lista.removePosicao(3);
        lista.imprimirLista(); // Saida: 1 2 10 4 5

        lista.removeInicio();
        lista.imprimirLista(); // Saida: 2 10 4 5

        lista.removeFinal();
        lista.imprimirLista(); // Saida: 2 10 4
    }
}

class ExcecaoElementoNaoEncontrado extends RuntimeException {
    public ExcecaoElementoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}

class ExcecaoPosicaoInvalida extends RuntimeException {
    public ExcecaoPosicaoInvalida(String mensagem) {
        super(mensagem);
    }
}
