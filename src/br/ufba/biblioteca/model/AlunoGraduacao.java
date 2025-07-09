package br.ufba.biblioteca.model;

import br.ufba.biblioteca.strategy.emprestimo.RegraEmprestimoAluno;
import br.ufba.biblioteca.strategy.emprestimo.RegraEmprestimoStrategy;

public class AlunoGraduacao extends Usuario {

    public AlunoGraduacao(int codigo, String nome) {
        super(codigo, nome);
    }

    @Override
    public int getTempoEmprestimoDias() {
        return 4;
    }

    @Override
    public int getLimiteEmprestimosAbertos() {
        return 2;
    }

    @Override
    public RegraEmprestimoStrategy getRegraEmprestimo() {
        return new RegraEmprestimoAluno();
    }
}