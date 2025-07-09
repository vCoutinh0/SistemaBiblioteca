package br.ufba.biblioteca.strategy.emprestimo;

import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;

public interface RegraEmprestimoStrategy {
    String podeRealizarEmprestimo(Usuario usuario, Livro livro, Repositorio repositorio);
}