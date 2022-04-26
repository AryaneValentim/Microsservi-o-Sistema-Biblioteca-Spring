package br.com.senac.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.senac.entity.Turma;
import br.com.senac.repository.TurmaRepository;

@Service
public class TurmaService {
	
	@Autowired
	TurmaRepository turmaRepo;
	//listar
	public List<Turma> listaTodosTurmas(){
		return turmaRepo.findAll();
	}
	
	//paginação
	public Page<Turma> buscaPorPaginacao(int pagina, int linhasPorPagina, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direction), orderBy);
		return new PageImpl<>(turmaRepo.findAll(), pageRequest, linhasPorPagina);
	}
	
	//trazer por id
	public Turma buscaPorID(Integer id) throws ObjectNotFoundException{
		Optional<Turma> turma = turmaRepo.findById(id);
		return turma.orElseThrow(() -> new ObjectNotFoundException(null,"Turma não encontrado"));
	}
	//salvar
	public Turma salvar(Turma turma) {
		return turmaRepo.save(turma);
	}
	//delete
	public void excluir(Integer id) {
		turmaRepo.deleteById(id);
	}
	//update
	public Turma alterar(Turma objTurma) {
		Turma turma = buscaPorID(objTurma.getId());
		turma.setNome(objTurma.getNome());
		return salvar(turma);
	}
}
