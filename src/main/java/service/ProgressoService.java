package service;

import org.springframework.stereotype.Service;

import dto.ProgressoDTO;
import entity.Aluno;
import repository.AlunoRepository;

@Service
public class ProgressoService {

	private final AlunoRepository alunoRepository;

	public ProgressoService(AlunoRepository alunoRepository) {
		this.alunoRepository = alunoRepository;
	}

	public ProgressoDTO calcularProgresso(Long idAluno) {
		Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

		long concluidos = aluno.getConclusoes().stream().filter(Conclusao -> Conclusao.isConcluido()).count();

		double media = aluno.getMediaGeral();
		long meta = 12;
		long faltam = meta - concluidos;
		String mensagem;

		if (concluidos == 0) {
			mensagem = "Nenhum curso concluído. Faltam " + meta + " cursos para atingir o bônus.";
		} else if (concluidos >= 5 && media > 7) {
			mensagem = "Você concluiu 5 cursos com média acima de 7. Faltam 7 cursos para alcançar o bônus.";
		} else if (concluidos == 3 && media < 7) {
			mensagem = "Você concluiu 3 cursos com média abaixo de 7. Faltam 10 cursos para atingir o bônus.";
		} else {
			mensagem = "Cursos concluídos: " + concluidos + ". Faltam " + faltam + " para o bônus.";
		}

		return new ProgressoDTO(mensagem, concluidos, media, faltam);
	}

}
