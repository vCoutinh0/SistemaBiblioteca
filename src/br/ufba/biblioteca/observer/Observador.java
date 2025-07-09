package br.ufba.biblioteca.observer;

public interface Observador {
    void atualizar(Observavel observavel);
    int getNumeroNotificacoes();
}