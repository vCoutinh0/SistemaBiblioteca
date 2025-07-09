package br.ufba.biblioteca.model;

import java.time.LocalDate;

public class Reserva {
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataReserva;

    public Reserva(Usuario usuario, Livro livro, LocalDate dataReserva) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataReserva = dataReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }
}