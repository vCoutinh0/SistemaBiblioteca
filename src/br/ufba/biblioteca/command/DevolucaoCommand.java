package br.ufba.biblioteca.command;

import br.ufba.biblioteca.model.Emprestimo;
import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;
import br.ufba.biblioteca.util.DateUtil;

import java.time.LocalDate;
import java.util.Optional;

public class DevolucaoCommand implements Command {
    private Repositorio repositorio;

    public DevolucaoCommand(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: dev <codigoUsuario> <codigoLivro>");
            return;
        }

        int codigoUsuario = Integer.parseInt(args[0]);
        int codigoLivro = Integer.parseInt(args[1]);

        Usuario usuario = repositorio.buscarUsuarioPorCodigo(codigoUsuario);
        Livro livro = repositorio.buscarLivroPorCodigo(codigoLivro);

        if (usuario == null) {
            System.out.println("Erro: Usuário com código " + codigoUsuario + " não encontrado.");
            return;
        }
        if (livro == null) {
            System.out.println("Erro: Livro com código " + codigoLivro + " não encontrado.");
            return;
        }

        Optional<Emprestimo> emprestimoAtivoOpt = usuario.getEmprestimosCorrentes().stream()
                                                    .filter(e -> e.getLivro().getCodigo() == codigoLivro)
                                                    .findFirst();

        if (!emprestimoAtivoOpt.isPresent()) {
            System.out.println("Erro: O usuário " + usuario.getNome() + " não possui este livro (" + livro.getTitulo() + ") emprestado no momento.");
            return;
        }

        Emprestimo emprestimoAtivo = emprestimoAtivoOpt.get();
        LocalDate dataDevolucao = LocalDate.now();
        
        emprestimoAtivo.finalizarEmprestimo(dataDevolucao); // Finaliza o empréstimo e seta exemplar como disponível
        usuario.removerEmprestimoCorrente(emprestimoAtivo);
        usuario.adicionarHistoricoEmprestimo(emprestimoAtivo);

        System.out.println("Devolução do livro '" + livro.getTitulo() + "' (Exemplar " + emprestimoAtivo.getExemplar().getCodigoExemplar() + ") por " + usuario.getNome() + " realizada com sucesso.");
        System.out.println("Data da Devolução: " + DateUtil.format(dataDevolucao));
    }
}