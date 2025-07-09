package br.ufba.biblioteca.model;

import br.ufba.biblioteca.observer.Observavel;
import br.ufba.biblioteca.observer.Observador;

import java.util.ArrayList;
import java.util.List;

public class Livro implements Observavel {
    private int codigo;
    private String titulo;
    private String editora;
    private List<String> autores;
    private String edicao;
    private int anoPublicacao;
    private List<Exemplar> exemplares;
    private List<Reserva> reservas;
    private List<Observador> observadores;

    public Livro(int codigo, String titulo, String editora, List<String> autores, String edicao, int anoPublicacao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.editora = editora;
        this.autores = autores;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.exemplares = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.observadores = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditora() {
        return editora;
    }

    public List<String> getAutores() {
        return autores;
    }

    public String getEdicao() {
        return edicao;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void adicionarExemplar(Exemplar exemplar) {
        this.exemplares.add(exemplar);
    }

    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        if (getQuantidadeReservas() > 2) {
            notificarObservadores();
        }
    }

    public void removerReserva(Reserva reserva) {
        this.reservas.remove(reserva);
    }

    public int getQuantidadeReservas() {
        return reservas.size();
    }

    public int getQuantidadeExemplaresDisponiveis() {
        int count = 0;
        for (Exemplar exemplar : exemplares) {
            if (exemplar.getStatus() == StatusExemplar.DISPONIVEL) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public void adicionarObservador(Observador o) {
        this.observadores.add(o);
    }

    @Override
    public void removerObservador(Observador o) {
        this.observadores.remove(o);
    }

    @Override
    public void notificarObservadores() {
        for (Observador o : observadores) {
            o.atualizar(this);
        }
    }
}