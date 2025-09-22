package entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private double mediaGeral;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Conclusao> conclusoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getMediaGeral() {
		return mediaGeral;
	}

	public void setMediaGeral(double mediaGeral) {
		this.mediaGeral = mediaGeral;
	}

	public List<Conclusao> getConclusoes() {
		return conclusoes;
	}

	public void setConclusoes(List<Conclusao> conclusoes) {
		this.conclusoes = conclusoes;
	}

}
