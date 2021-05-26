package br.edu.ifrn.crud.controller;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Usuario;

@Controller
@RequestMapping("/usuarios")
public class buscaUsuarioController {
	
	@GetMapping("/busca")
	public String entrarBusca() {
		
		return "usuario/busca";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/buscar")
	public String buscar(
			@RequestParam(name="nome", required = false) String nome, // pegar valor da url cujo o nome é "nome" e atribuir a uma String nome
			@RequestParam(name="mostrarTodosDados", required = false) Boolean mostrarTodosDados,
			HttpSession sessao, //pra poder acessar a memoria
			ModelMap model
				// required = false: para que seja opcional a informação do parametro, se nao tiver isso da erro se nao informar
			) {
		
			//Obtém a lista com TODOS os usuarios cadastrados em memoria:
		
		List<Usuario> usuariosCadastrados = 
				(List<Usuario>) sessao.getAttribute("usuariosCadastrados");
		
			//Lista que armazenará os usuarios buscados 
		List<Usuario> usuariosEncontrados = new ArrayList<>();
		
			//Se o usuario nao digitou nada na busc...
		if(nome == null || nome.isEmpty()) {
			//... mostrará todos os usuarios cadastrados
			usuariosEncontrados = usuariosCadastrados;
			
		}else {
			//Se o usario digitou algo na busca
			
			//Se existirem usuarios cadastrados
			if(usuariosCadastrados != null) {
				
				//Pegando da lista somente os que tem o nome buscado
					//stream . filter: novo recurso do java que permite fazer uma busca mais facil em listas, o filter fiutra os dados que voce quer manter
				usuariosEncontrados =  usuariosCadastrados.stream().filter(
						x -> x.getNome().toLowerCase().contains(nome.toLowerCase()) // -> é tipo um for que so vai manter os "x"(usuarios) que corresponde ao codigo que mandei, tranformar tudo em minuscula pra nao da erro de busca
						).collect(Collectors.toList()); //Collectors,tolist tranforma o que foi coletado numa  lista
				
			}
		}
		
		model.addAttribute("usuariosEncontrados", usuariosEncontrados); //pagina html vai ter acesso a esses dados (no html busca vai precisar no div)
		
		if(mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}
		
		return "usuario/busca";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/remover/{id}") 
	public String iniciarRemoção(	
			@PathVariable("id") Integer idUsuario, 
			HttpSession sessao, RedirectAttributes attr 
			) {
		
		List<Usuario> usuariosCadastrados = 
				(List<Usuario>) sessao.getAttribute("usuariosCadastrados");
		
		Usuario u = new Usuario(); //representa o mesmo usuario que quero remover
		u.setId(idUsuario);
		
			// so consegue remover graças a o hasCode e ao Equals(classe usuario, pois o id de "u" é igual ao que quer remover)
		boolean removeu = usuariosCadastrados.remove(u);
		
		if(removeu) {
			attr.addFlashAttribute("msgSucesso", "Usuário removido com sucesso!");
		}else {
			attr.addFlashAttribute("msgErro", "Não foi possivel remover o usuário");
		}
			
		return "redirect:/usuarios/buscar";
	}
	
}
