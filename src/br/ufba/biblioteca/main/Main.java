package br.ufba.biblioteca.main;

import br.ufba.biblioteca.command.Command;
import br.ufba.biblioteca.command.ConsultarLivroCommand;
import br.ufba.biblioteca.command.ConsultarNotificacoesCommand;
import br.ufba.biblioteca.command.ConsultarUsuarioCommand;
import br.ufba.biblioteca.command.DevolucaoCommand;
import br.ufba.biblioteca.command.EmprestimoCommand;
import br.ufba.biblioteca.command.ObservarLivroCommand;
import br.ufba.biblioteca.command.ReservaCommand;
import br.ufba.biblioteca.command.SairCommand;
import br.ufba.biblioteca.model.AlunoGraduacao;
import br.ufba.biblioteca.model.AlunoPosgraduacao;
import br.ufba.biblioteca.model.Exemplar;
import br.ufba.biblioteca.model.Livro;
import br.ufba.biblioteca.model.Professor;
import br.ufba.biblioteca.model.Usuario;
import br.ufba.biblioteca.repository.Repositorio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Repositorio repositorio;
    private static Map<String, Command> comandos;
    private static Scanner scanner;

    public static void main(String[] args) {
        repositorio = Repositorio.getInstance();
        comandos = new HashMap<>();
        
        // objeto para leitura de linhas completas do console
        scanner = new Scanner(System.in);

        inicializarDadosDeTeste();
        inicializarComandos();

        System.out.println("Sistema de Gerenciamento de Biblioteca Acadêmica");
        System.out.println("Comandos disponíveis: emp, dev, res, obs, liv, usu, ntf, sai");

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String linha = scanner.nextLine().trim();

            if (linha.isEmpty()) {
                continue;
            }

            String[] partes = linha.split(" ");
            String comandoInput = partes[0].toLowerCase();
            String[] argumentosComando = Arrays.copyOfRange(partes, 1, partes.length);

            Command comando = comandos.get(comandoInput);
            if (comando != null) {
            	comando.execute(argumentosComando);
                if ("sai".equals(comandoInput)) {
                    running = false;
                }
            } else {
                System.out.println("Comando desconhecido: " + comandoInput);
            }
        }

        scanner.close();
        System.out.println("Aplicação encerrada.");
    }

    private static void inicializarDadosDeTeste() {
        Usuario joao = new AlunoGraduacao(123, "João da Silva");
        Usuario luiz = new AlunoPosgraduacao(456, "Luiz Fernando Rodrigues");
        Usuario pedro = new AlunoGraduacao(789, "Pedro Paulo");
        Usuario carlos = new Professor(100, "Carlos Lucena");
        
        repositorio.addUsuario(joao);
        repositorio.addUsuario(luiz);
        repositorio.addUsuario(pedro);
        repositorio.addUsuario(carlos);

        Livro livro100 = new Livro(100, "Engenharia de Software", "Addison Wesley", Arrays.asList("Ian Sommervile"), "6ª", 2000);
        Livro livro101 = new Livro(101, "UML - Guia do Usuário", "Campus", Arrays.asList("Grady Booch", "James Rumbaugh", "Ivar Jacobson"), "7ª", 2000);
        Livro livro200 = new Livro(200, "Code Complete", "Microsoft Press", Arrays.asList("Steve McConnell"), "2ª", 2014);
        Livro livro201 = new Livro(201, "Agile Software Development, Principles, Patterns and Practices", "Prentice Hall", Arrays.asList("Robert Martin"), "1ª", 2002);
        Livro livro300 = new Livro(300, "Refactoring: Improving the Design of Existing Code", "Addison Wesley Professional", Arrays.asList("Martin Fowler"), "1ª", 1999);
        Livro livro301 = new Livro(301, "Software Metrics: A rigorous and Practical Approach", "CRC Press", Arrays.asList("Norman Fenton", "James Bieman"), "3ª", 2014);
        Livro livro400 = new Livro(400, "Design Patterns: Element of Reusable Object-Oriented Software", "Addison Wesley Professional", Arrays.asList("Erich Gamma", "Richard Helm", "Ralph Johnson", "John Vlissides"), "1ª", 1994);
        Livro livro401 = new Livro(401, "UML Distilled: A Brief Guide to the Standard Object Modeling Language", "Addison Wesley Professional", Arrays.asList("Martin Fowler"), "3ª", 2003);

        repositorio.addLivro(livro100);
        repositorio.addLivro(livro101);
        repositorio.addLivro(livro200);
        repositorio.addLivro(livro201);
        repositorio.addLivro(livro300);
        repositorio.addLivro(livro301);
        repositorio.addLivro(livro400);
        repositorio.addLivro(livro401);

        livro100.adicionarExemplar(new Exemplar(1, livro100));
        livro100.adicionarExemplar(new Exemplar(2, livro100));
        livro101.adicionarExemplar(new Exemplar(3, livro101));
        livro200.adicionarExemplar(new Exemplar(4, livro200));
        livro201.adicionarExemplar(new Exemplar(5, livro201));
        livro300.adicionarExemplar(new Exemplar(6, livro300));
        livro300.adicionarExemplar(new Exemplar(7, livro300));
        livro400.adicionarExemplar(new Exemplar(8, livro400));
        livro400.adicionarExemplar(new Exemplar(9, livro400));
    }

    private static void inicializarComandos() {
    	comandos.put("emp", new EmprestimoCommand(repositorio));
    	comandos.put("dev", new DevolucaoCommand(repositorio));
    	comandos.put("res", new ReservaCommand(repositorio));
    	comandos.put("obs", new ObservarLivroCommand(repositorio));
    	comandos.put("liv", new ConsultarLivroCommand(repositorio));
    	comandos.put("usu", new ConsultarUsuarioCommand(repositorio));
    	comandos.put("ntf", new ConsultarNotificacoesCommand(repositorio));
    	comandos.put("sai", new SairCommand());
    }
}