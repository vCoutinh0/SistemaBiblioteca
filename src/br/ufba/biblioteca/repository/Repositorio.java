package br.ufba.biblioteca.repository;

import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private static Repositorio instance;
    private List<Usuario> usuarios;
    private List<Livro> livros;

    private Repositorio() {
        usuarios = new ArrayList<>();
        livros = new ArrayList<>();
    }

    public static Repositorio getInstance() {
        if (instance == null) {
            instance = new Repositorio();
        }
        return instance;
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorCodigo(int codigo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCodigo() == codigo) {
                return usuario;
            }
        }
        return null;
    }

    public void addLivro(Livro livro) {
        this.livros.add(livro);
    }

    public Livro buscarLivroPorCodigo(int codigo) {
        for (Livro l : livros) {
            if (l.getCodigo() == codigo) {
                return l;
            }
        }
        return null;
    }

}