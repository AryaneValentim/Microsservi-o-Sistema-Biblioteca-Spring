package br.com.senac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.entity.AlunoDisciplina;
import br.com.senac.entity.Avaliacao;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, AlunoDisciplina>{
	//select * from avaliacao where idaluno =3 and iddisciplina=6
	Avaliacao findByAlunoDisciplina(AlunoDisciplina alunoDisciplina);
}
