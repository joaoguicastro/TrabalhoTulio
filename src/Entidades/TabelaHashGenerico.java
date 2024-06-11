package Entidades;

public class TabelaHashGenerico<T> {
    public T vetor[];
    public int nElementos;

    @SuppressWarnings("unchecked")
    public TabelaHashGenerico(int capacidade) {
        this.vetor = (T[]) new Object[capacidade];
        this.nElementos = 0;
    }

    public int tamanho() {
        return this.nElementos;
    }

    public void imprime() {
        System.out.println("Chave\tValor");
        for (int i = 0; i < vetor.length; i++) {
            System.out.println(i + " -->\t[ " + vetor[i] + " ]");
        }
    }

    private int funcaoHashDiv(Object elemento) {
        int hashCode = elemento.hashCode();
        int chave = Math.abs(hashCode) % this.vetor.length;
        return chave;
    }

    public void insere(Object chave, T elemento) {
        int chaveHash = funcaoHashDiv(chave);
        this.vetor[chaveHash] = elemento;
        this.nElementos++;
    }

    public T remove(Object chave) {
        int chaveHash = funcaoHashDiv(chave);

        T removido = this.vetor[chaveHash];

        this.vetor[chaveHash] = null;
        this.nElementos--;

        return removido;
    }

    public T busca(Object chave) {
        int chaveHash = funcaoHashDiv(chave);
        return this.vetor[chaveHash];
    }

    public boolean contem(Object chave) {
        int chaveHash = funcaoHashDiv(chave);
        return this.vetor[chaveHash] != null;
    }
}
