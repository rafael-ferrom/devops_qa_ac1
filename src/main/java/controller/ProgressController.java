package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ProgressoDTO;
import service.ProgressoService;

@RestController
@RequestMapping("/progresso")
public class ProgressController {
	
	private final ProgressoService progressoService;

    public ProgressController(ProgressoService progressoService) {
        this.progressoService = progressoService;
    }

    @GetMapping("/{idAluno}")
    public ProgressoDTO getProgresso(@PathVariable Long idAluno) {
        return progressoService.calcularProgresso(idAluno);
    }

}
