package br.ufba.biblioteca.model;

import br.ufba.biblioteca.strategy.emprestimo.RegraEmprestimoStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    protected int codigo;
    protected String nome;
    protected List<Emprestimo> emprestimosCorrentes;
    protected List<Emprestimo> historicoEmprestimos;
    protected List<Reserva> reservas;

    public Usuario(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.emprestimosCorrentes = new ArrayList<>();
        this.historicoEmprestimos = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public List<Emprestimo> getEmprestimosCorrentes() {
        return emprestimosCorrentes;
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void adicionarEmprestimoCorrente(Emprestimo emprestimo) {
        this.emprestimosCorrentes.add(emprestimo);
    }

    public void removerEmprestimoCorrente(Emprestimo emprestimo) {
        this.emprestimosCorrentes.remove(emprestimo);
    }

    public void adicionarHistoricoEmprestimo(Emprestimo emprestimo) {
        this.historicoEmprestimos.add(emprestimo);
    }

    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    public void removerReserva(Reserva reserva) {
        this.reservas.remove(reserva);
    }
    
    public abstract int getTempoEmprestimoDias();
    public abstract int getLimiteEmprestimosAbertos();
    public abstract RegraEmprestimoStrategy getRegraEmprestimo();

    public boolean isDevedor() {
        for (Emprestimo emprestimo : emprestimosCorrentes) {
            if (emprestimo.getDataPrevistaDevolucao().isBefore(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }

    public Reserva buscarReservaPorLivro(Livro livro) {
        for (Reserva r : reservas) {
            if (r.getLivro().equals(livro)) {
                return r;
            }
        }
        return null;
    }

    public boolean temExemplarEmprestado(Livro livro) {
        for (Emprestimo e : emprestimosCorrentes) {
            if (e.getLivro().equals(livro)) {
                return true;
            }
        }
        return false;
    }
}