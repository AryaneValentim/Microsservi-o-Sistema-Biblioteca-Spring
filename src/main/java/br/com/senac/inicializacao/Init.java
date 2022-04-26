package br.com.senac.inicializacao;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.senac.entity.Aluno;
import br.com.senac.entity.AlunoDisciplina;
import br.com.senac.entity.Avaliacao;
import br.com.senac.entity.Disciplina;
import br.com.senac.entity.Turma;
import br.com.senac.repository.AlunoRepository;
import br.com.senac.service.AvaliacaoService;
import br.com.senac.service.DisciplinaService;
import br.com.senac.service.TurmaService;

@Component //Mostra que essa é uma classe de inicialização
//ContextRefreshedEvent sempre que inicializar o programar é para ocorrer algum evento da class Inicializacao
public class Init implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	AlunoRepository alunoRepository;
	@Autowired
	TurmaService turmaService;
	@Autowired
	DisciplinaService disciplinaService;
	@Autowired
	AvaliacaoService avaliacaoService;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//_________________________________________ Criando ALUNOS  _________________________________________
		Aluno aluno1 = new Aluno();
		aluno1.setNome("Lucas");
		
		Aluno aluno2 = new Aluno();
		aluno2.setNome("Arthur");
		
		Aluno aluno3 = new Aluno();
		aluno3.setNome("Joao");
		
		Aluno aluno4 = new Aluno();
		aluno4.setNome("Francisco");
		//_______________________________teste: Criando as DISCIPLINAS
		Disciplina java = new Disciplina();
		java.setNome("Java");
		disciplinaService.salvar(java);
		
		Disciplina compilador = new Disciplina();
		compilador.setNome("compilador");
		disciplinaService.salvar(compilador);
		
		Disciplina arquitetura = new Disciplina();
		arquitetura.setNome("Arquitetura");
		disciplinaService.salvar(arquitetura);
		//_________________________________________ Criação da TURMAS  _________________________________________________
		Turma ads = new Turma();
		ads.setNome("ADS");
		turmaService.salvar(ads);
		
		Turma rede = new Turma();
		rede.setNome("Rede");
		turmaService.salvar(rede);
		//_________________________________________ Incluindo os ALUNOS nas TURMAS _____________________________________
		aluno1.setTurma(ads);
		aluno2.setTurma(ads);
		aluno3.setTurma(rede);
		aluno4.setTurma(rede);
		//_________________________________________ Incluindo os ALUNOS nas DISCIPLINAS ________________________________
		aluno1.setDisciplina(Arrays.asList(java,compilador,arquitetura));
		aluno2.setDisciplina(Arrays.asList(java,compilador));
		aluno3.setDisciplina(Arrays.asList(arquitetura,compilador));
		aluno4.setDisciplina(Arrays.asList(arquitetura,java));
		//_________________________________________ Salvando os ALUNOS  _________________________________________
		alunoRepository.save(aluno1);  //No BD == INSERT INTO ALUNO (NOME) VALUE ('Samila')
		alunoRepository.save(aluno2);  //No BD == INSERT INTO ALUNO (NOME) VALUE ('Lucas')
		alunoRepository.save(aluno3);  //No BD == INSERT INTO ALUNO (NOME) VALUE ('Vitoria')
		alunoRepository.save(aluno4);  //No BD == INSERT INTO ALUNO (NOME) VALUE ('Francisco'
//		Mas poderia ser feito assim:  		
//		alunoRepository.saveAll(Arrays.asList(aluno1, aluno2,aluno3)); // == INSERT INTO ALUNO (NOME) VALUE ('Samila')	
		//_________________________________________ Salvando a Avaliacao do Aluno  _________________________________________
		//Av 01
		Avaliacao avaliacaoAluno01 = new Avaliacao();
		AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
		alunoDisciplina.setAluno(aluno1);
		alunoDisciplina.setDisciplina(arquitetura);
		avaliacaoAluno01.setAlunoDisciplina(alunoDisciplina);
		avaliacaoAluno01.setConceito("A");
		avaliacaoService.save(avaliacaoAluno01);
		//Av 02
		Avaliacao avaliacaoAluno02 = new Avaliacao();
		AlunoDisciplina alunoDisciplina2 = new AlunoDisciplina();
		alunoDisciplina2.setAluno(aluno2);
		alunoDisciplina2.setDisciplina(compilador);
		avaliacaoAluno02.setAlunoDisciplina(alunoDisciplina2);
		avaliacaoAluno02.setConceito("B");
		avaliacaoService.save(avaliacaoAluno02);
		//_______________________________Teste:para funcionamento das Avaliações _________________________________
		Avaliacao TesteAvaliacao = avaliacaoService.buscarNotaAlunoDisciplina(alunoDisciplina);
		System.out.println("Aluno:" + TesteAvaliacao.getAlunoDisciplina().getAluno().getNome());
		System.out.println("Disciplina:" + TesteAvaliacao.getAlunoDisciplina().getDisciplina().getNome());
		System.out.println("Avaliação:" + TesteAvaliacao.getConceito());
//		//_______________________________Teste:para funcionamento da criação das turmas_________________________________
//		
//		List<Turma> listaTurma = turmaService.listaTodasTurma();
//		for (Turma turma : listaTurma) {
//			System.out.println("nome da turma:"  + turma.getNome());
//		}
//		
//		//_______________________________Teste:  para fazer uma busca TURMA por ID______________________________________
//		Turma turma1 = turmaService.buscaPorId(1);
//		System.out.println(turma1.getNome());
//		
//		//_______________________________teste: para Update/Alterar pelo ID_____________________________________________
//		Turma turmaAlterar = new Turma();
//		turmaAlterar.setId(1);
//		turmaAlterar.setNome("Redes");
//		turmaService.alterar(turmaAlterar);
//		
//		List<Turma> listaTurmaAlterar= turmaService.listaTodasTurma();
//		for (Turma turma : listaTurmaAlterar) {
//			System.out.println("Alterando nome da turma:"  + turma.getNome());
//		}
//		
//		//_______________________________teste: para excluir TURMA pelo ID______________________________________________
//		turmaService.excluir(1);
//		List<Turma> listaTurmaExcluir = turmaService.listaTodasTurma();
//		for (Turma turma : listaTurmaExcluir) {
//			System.out.println("nome da turma:"  + turma.getNome());
//		}
	}
}

	
