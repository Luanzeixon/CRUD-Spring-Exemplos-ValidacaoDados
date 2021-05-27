package br.edu.ifrn.crud.dominio;
import javax.validation.constraints.*; //TEM QUE USAR ESSE IMPORT PRA DÁ CERTO
public class Usuario {
	
	private int id;
	
	@NotBlank(message = "O campo nome é obrigatório.")
	@Size(min = 2, message = "O nome deve ter pelo o menos 2 caracteres." )
	private String nome;
	
	@NotBlank(message = "O campo email é obrigatório.")
	private String email;
	
	@NotBlank(message = "O campo senha é obrigatório.")
	@Size(min = 2, message = "O nome deve ter pelo o menos 2 caracteres." )
	private String senha;
	
	@NotBlank(message = "O campo sexo é obrigatório.")
	private String sexo;
	
	@NotBlank(message = "O profissão é obrigatório.")
	private String profissao;
	
	
	// pra que o java saiba diferenciar um usuario do outro pelo atributo id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissão) {
		this.profissao = profissão;
	}
	
}
