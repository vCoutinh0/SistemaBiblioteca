package br.ufba.biblioteca.model;

import java.time.LocalDate;

public class Emprestimo {
    private Usuario usuario;
    private Exemplar exemplar;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucaoRealizada;
    private StatusEmprestimo status;

    public Emprestimo(Usuario usuario, Exemplar exemplar, LocalDate dataEmprestimo, LocalDate dataPrevistaDevolucao) {
        this.usuario = usuario;
        this.exemplar = exemplar;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucaoRealizada = null;
        this.status = StatusEmprestimo.EM_CURSO;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public Livro getLivro() {
        return exemplar.getLivro();
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucaoRealizada() {
        return dataDevolucaoRealizada;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void finalizarEmprestimo(LocalDate dataDevolucao) {
        this.dataDevolucaoRealizada = dataDevolucao;
        this.status = StatusEmprestimo.FINALIZADO;
        this.exemplar.setDisponivel();
    }

    public boolean estaEmAtraso() {
        return status == StatusEmprestimo.EM_CURSO && LocalDate.now().isAfter(dataPrevistaDevolucao);
    }
}