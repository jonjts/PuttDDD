package br.com.jonjts.putddd.model;

public class Operadora {
	
	private Long id;
	private String nome;
	private String codigo;
	
	public Operadora() {
		// TODO Auto-generated constructor stub
	}
	
	public Operadora(Long id){
		this.id = id;
	}
	
	public Operadora(Long id, String nome, String codigo){
		this.id =id;
		this.nome = nome;
		this.codigo = codigo;
	}

	public Operadora(String nome, String codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String toString(){
		return getNome()+" - "+getCodigo();
	}

}
