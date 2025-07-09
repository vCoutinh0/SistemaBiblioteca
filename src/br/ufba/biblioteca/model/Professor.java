package br.ufba.biblioteca.model;

import br.ufba.biblioteca.observer.Observavel;
import br.ufba.biblioteca.observer.Observador;
import br.ufba.biblioteca.strategy.emprestimo.RegraEmprestimoProfessor;
import br.ufba.biblioteca.strategy.emprestimo.RegraEmprestimoStrategy;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario implements Observador {
    private int numeroNotificacoes;
    private List<Livro> livrosObservados;

    public Professor(int codigo, String nome) {
        super(codigo, nome);
        this.numeroNotificacoes = 0;
        this.livrosObservados = new ArrayList<>();
    }

    @Override
    public int getNumeroNotificacoes() {
        return numeroNotificacoes;
    }

    @Override
    public int getTempoEmprestimoDias() {
        return 8;
    }

    @Override
    public int getLimiteEmprestimosAbertos() {
        return -1;
    }

    @Override
    public RegraEmprestimoStrategy getRegraEmprestimo() {
        return new RegraEmprestimoProfessor();
    }

    @Override
    public void atualizar(Observavel observavel) {
        if (observavel instanceof Livro) {
            Livro livroObservado = (Livro) observavel;
            System.out.println("Usuário " + getNome() + " (" + getCodigo() + ") notificado: O livro '" + livroObservado.getTitulo() + "' atingiu mais de duas reservas simultâneas.");
            numeroNotificacoes++;
        }
    }

    public void adicionarLivroObservado(Livro livro) {
        this.livrosObservados.add(livro);
    }
}