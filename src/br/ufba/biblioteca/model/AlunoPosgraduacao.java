package br.ufba.biblioteca.model;

import br.ufba.biblioteca.strategy.emprestimo.RegraEmprestimoAluno;
import br.ufba.biblioteca.strategy.emprestimo.RegraEmprestimoStrategy;

public class AlunoPosgraduacao extends Usuario {

    public AlunoPosgraduacao(int codigo, String nome) {
        super(codigo, nome);
    }

    @Override
    public int getTempoEmprestimoDias() {
        return 5;
    }

    @Override
    public int getLimiteEmprestimosAbertos() {
        return 3;
    }

    @Override
    public RegraEmprestimoStrategy getRegraEmprestimo() {
        return new RegraEmprestimoAluno();
    }
}