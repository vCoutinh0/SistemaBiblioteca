package br.ufba.biblioteca.strategy.emprestimo;

import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;

public class RegraEmprestimoProfessor implements RegraEmprestimoStrategy {

    @Override
    public String podeRealizarEmprestimo(Usuario professor, Livro livro, Repositorio repositorio) {
        if (livro.getQuantidadeExemplaresDisponiveis() == 0) {
            return "Não foi possível realizar o empréstimo, pois não há exemplares disponíveis deste livro.";
        }

        if (professor.isDevedor()) {
            return "Não foi possível realizar o empréstimo, pois o usuário está com livros em atraso.";
        }
        
        return null;
    }
}