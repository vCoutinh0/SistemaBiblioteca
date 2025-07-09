package br.ufba.biblioteca.command;

import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.observer.Observador;
import br.ufba.biblioteca.repository.Repositorio;

public class ConsultarNotificacoesCommand implements Command {
    private Repositorio repositorio;

    public ConsultarNotificacoesCommand(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: ntf <codigoUsuario>");
            return;
        }

        int codigoUsuario = Integer.parseInt(args[0]);
        Usuario usuario = repositorio.buscarUsuarioPorCodigo(codigoUsuario);

        if (usuario == null) {
            System.out.println("Erro: Usuário com código " + codigoUsuario + " não encontrado.");
            return;
        }
        
        if (!(usuario instanceof Observador)) {
            System.out.println("Erro: O usuário com código " + codigoUsuario + " não possui notificações para serem consultadas (não é um observador).");
            return;
        }

        Observador observador = (Observador) usuario;
        System.out.println("--- Notificações do Usuário Observador ---");
        System.out.println("Nome do Usuário: " + usuario.getNome() + " (Código: " + usuario.getCodigo() + ")");
        System.out.println("Número Total de Notificações Recebidas: " + observador.getNumeroNotificacoes());
        System.out.println("-----------------------------------------");
    }
}