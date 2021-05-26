package br.edu.ifrn.crud.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Usuario;

@Controller
@RequestMapping("/usuarios")
public class cadastroUsuarioController {
	
	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Usuario()); //envia uma variavel usuario que corresponde a um novo usuario em branco que sera defenida na pagina.
		return "usuario/cadastro";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/salvar")
	public String salvar(Usuario usuario, RedirectAttributes attr, HttpSession sessao, ModelMap model) {
			//já que nao tem BD o usuario vai ser salvo na memoria(seção do servidorpor isso o httpsession
			//RedirectAttributes serve como um modelMap mas quando retorna redirect em vez de forward
		
			//Pegando valores da sessão:
		
		Integer id = (Integer) sessao.getAttribute("id"); //pesquisar na memoria(sessao) se tem algum id igual o id colocado no form
		List<Usuario> usuariosCadastrados = 
				(List<Usuario>) sessao.getAttribute("usuariosCadastrados"); // ver se tem alguma lista de usuraios existente na memoria
		
			//Chamar o metodo validardados passando o usuario que retornará uma lista de mensagens.
		List<String> msgValidacao = validarDados(usuario);
			//Se a lista de mensagens NÃO tiver vazia coloca as menagens e retorna a pagina d ecadastro
		if(!msgValidacao.isEmpty()) {
			model.addAttribute("msgsErro",msgValidacao);
			return "usuario/cadastro";
		}
		
		if(id == null) { //1 id cadastrado
			id = 1;
		}
		if(usuariosCadastrados == null) //1- se não tiver nenhum usuario cadastrado ainda
			usuariosCadastrados = new ArrayList<>(); //2- criar um array list com os usuarios
		
			//Verificando se é cadastro ou edição
		if(usuario.getId() == 0) { // na pagina de cadastro o id é 0 poqrque nao é setado nenhum ainda no campo (mesmo tendo outros usuarios ja cadastrados)
			
			usuario.setId(id); //adicionar o id ao usuario
			usuariosCadastrados.add(usuario); //adicionar o usuario ao array de usuario
			id++;
			
			sessao.setAttribute("id", id); //salvar na memoria o novo id para ser pesquisado na linha 33(comeco do codigo slavar)
			sessao.setAttribute("usuariosCadastrados", usuariosCadastrados); //salvar na memoria a lista de usuarios
			
			attr.addFlashAttribute("msgSucesso", "Cadastro realizado com sucesso!");
			//Tem que ser Flash pois é um atributo temporario.
			
		}else {
				//Edição
			usuariosCadastrados.remove(usuario); 
			usuariosCadastrados.add(usuario);
				//TODAS as informações vao ser setadas pela lista, não precis averificar um por um parametro
			attr.addFlashAttribute("msgSucesso", "Usuario Editado com sucesso!");
		}
		
		return "redirect:/usuarios/cadastro"; //usar redirect na url usuarios, para "apagar"(atualizar) os contatos salvos do form (tem que colocar /)
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/editar/{id}") //na url vai ta o parametro que deseja editar informado no html, o path-variable pega essa variavel de mesmo nome 
	public String iniciarEdição(
			@PathVariable("id") Integer idUsuario, ModelMap model,
			HttpSession sessao
			) {
		
		List<Usuario> usuariosCadastrados =  
				(List<Usuario>) sessao.getAttribute("usuariosCadastrados");
		
		Usuario u = new Usuario(); // criar um objeto pra buscar o id entre os usuarios cadastrados
		u.setId(idUsuario); //setar o id da url no usuario criado 
		
		int pos = usuariosCadastrados.indexOf(u); // indexof pega a posição de um objeto na lista
		u = usuariosCadastrados.get(pos); // u recebe o usuario que tem o mesmo id na mesma posição
		
		model.addAttribute("usuario", u);  // enviar o usuario para pagina html que no form de cadastro vai ser setado esse u como usuario
		
		
		return "/usuario/cadastro";
	}
	
	@ModelAttribute("profissoes")
	public List<String> getProfissoes(){ //Lista de string com todas as profissões retornadas no html
		return Arrays.asList("Professor","Medico","Advogado","Bombeiro","Policial","Outro");
	}
		
		//Validar os dados pra não ter erros no BD
	private List<String> validarDados(Usuario usuario){ //irá retornar uma lista de Strings 
		
		List<String> msgs = new ArrayList(); //Armazenar a lista de erros
		
		if(usuario.getNome() == null || usuario.getNome().isEmpty()) {
			msgs.add("O campo Nome é Obrigatorio!");
		}
		if(usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			msgs.add("O campo Email é Obrigatorio!");
		}
		if(usuario.getProfissao() == null || usuario.getProfissao().isEmpty()) {
			msgs.add("O campo Profissão é Obrigatorio!");
		}
		if(usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			msgs.add("O campo Senha é Obrigatorio!");
		}
		if(usuario.getSexo() == null || usuario.getSexo().isEmpty()) {
			msgs.add("O campo Sexo é Obrigatorio!");
		}
		
		return msgs;
		
	}
	
}
