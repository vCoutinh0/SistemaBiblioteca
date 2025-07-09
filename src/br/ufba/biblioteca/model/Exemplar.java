package br.ufba.biblioteca.model;

public class Exemplar {
    private int codigoExemplar;
    private Livro livro;
    private StatusExemplar status;
    private Emprestimo emprestimoCorrente;

    public Exemplar(int codigoExemplar, Livro livro) {
        this.codigoExemplar = codigoExemplar;
        this.livro = livro;
        this.status = StatusExemplar.DISPONIVEL;
        this.emprestimoCorrente = null;
    }

    public int getCodigoExemplar() {
        return codigoExemplar;
    }

    public Livro getLivro() {
        return livro;
    }

    public StatusExemplar getStatus() {
        return status;
    }

    public Emprestimo getEmprestimoCorrente() {
        return emprestimoCorrente;
    }

    public void setEmprestimoCorrente(Emprestimo emprestimoCorrente) {
        this.emprestimoCorrente = emprestimoCorrente;
        this.status = (emprestimoCorrente != null) ? StatusExemplar.EMPRESTADO : StatusExemplar.DISPONIVEL;
    }

    public void setDisponivel() {
        this.status = StatusExemplar.DISPONIVEL;
        this.emprestimoCorrente = null;
    }

    public void setEmprestado() {
        this.status = StatusExemplar.EMPRESTADO;
    }
}