package br.ufba.biblioteca.strategy.emprestimo;

import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Reserva;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;

public class RegraEmprestimoAluno implements RegraEmprestimoStrategy {

    @Override
    public String podeRealizarEmprestimo(Usuario aluno, Livro livro, Repositorio repositorio) {
        if (livro.getQuantidadeExemplaresDisponiveis() == 0) {
            return "Não foi possível realizar o empréstimo, pois não há exemplares disponíveis deste livro.";
        }

        if (aluno.isDevedor()) {
            return "Não foi possível realizar o empréstimo, pois o usuário está com livros em atraso.";
        }

        if (aluno.getEmprestimosCorrentes().size() >= aluno.getLimiteEmprestimosAbertos()) {
            return "Não foi possível realizar o empréstimo, pois o usuário já atingiu o limite máximo de livros emprestados.";
        }

        if (aluno.temExemplarEmprestado(livro)) {
            return "Não foi possível realizar o empréstimo, pois o usuário já tem um exemplar deste mesmo livro em empréstimo no momento.";
        }

        int qtdReservas = livro.getQuantidadeReservas();
        int qtdExemplaresDisponiveis = livro.getQuantidadeExemplaresDisponiveis();
        Reserva reservaDoUsuario = aluno.buscarReservaPorLivro(livro);

        if (qtdReservas >= qtdExemplaresDisponiveis) {
            if (reservaDoUsuario == null) {
                return "Não foi possível realizar o empréstimo, pois a quantidade de reservas é igual ou superior à de exemplares disponíveis, e o usuário não possui reserva para este livro.";
            }
        }

        return null;
    }
}