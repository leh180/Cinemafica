package service;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.*;
import java.io.*;
import spark.Request;
import spark.Response;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID_USUARIO = 1;
	private final int FORM_ORDERBY_EMAIL = 2;
	private final int FORM_ORDERBY_SENHA = 3;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_ID_USUARIO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		form = "";
	    try {
	    	InputStream inputStream = getClass().getResourceAsStream("/public/cadastro.html");
	    	Scanner entrada = new Scanner(inputStream);
	        while (entrada.hasNext()) {
	            form += (entrada.nextLine() + "\n");
	        }
	        entrada.close();
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
		
		String umUsuario = "";
		if(tipo != FORM_INSERT) {
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/list/1\">Novo Usuario</a></b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String buttonLabel;
			int ID_Usuario = usuario.getID_Usuario();
			if (tipo == FORM_INSERT){
				action += "insert";
				buttonLabel = "Cadastrar-se";
			} else {
				action += "update/" + usuario.getID_Usuario();
				ID_Usuario = usuario.getID_Usuario();
				buttonLabel = "Atualizar";
			}
			
			String[] generosArray = usuario.getGeneros();
			
			umUsuario += "\t<form class=\"form--register\" action=\"" + action + "\"method=\"post\" id=\"form-add\">";
			umUsuario += "\t\t<input class=\"input--register\" type=\"text\" name=\"ID_Usuario\" value=\"" + ID_Usuario + "\">";
		    umUsuario += "\t\t<label for=\"nome\">Nome:</label>";
		    umUsuario += "\t\t<input type=\"text\" class=\"input--register\" name=\"nome\" value=\"" + usuario.getNome() + "\" autofocus required>";
		    umUsuario += "\t\t<label for=\"email\">Email:</label>";
		    umUsuario += "\t\t<input type=\"email\" class=\"input--register\" name=\"email\" value=\"" + usuario.getEmail() + "\" required>";
		    umUsuario += "\t\t<label for=\"senha\">Senha:</label>";
		    umUsuario += "\t\t<input type=\"password\" class=\"input--register\" name=\"senha\" value=\"" + usuario.getSenha() + "\" required>";
		    umUsuario += "\t\t<label for=\"generos\">Gêneros favoritos:</label>";
		    umUsuario += "\t\t<div class=\"checkbox-container\">";
		    umUsuario += "\t\t\t<div class=\"checkbox-column\">";
		    
		    int colunas = 0;

		    for (String genero : generosArray) {
		        umUsuario += "\t\t\t\t<label class=\"checkbox-label\">";
		        umUsuario += "<input class=\"input--register\" type=\"checkbox\" name=\"generos\" value=\"" + genero + "\">";
		        umUsuario += "<span class=\"custom-checkbox\"></span>" + genero;
		        umUsuario += "</label>";
		        
		        colunas++;
		        
		        if (colunas >= 6) {
		            umUsuario += "</div><div class=\"checkbox-column\">";
		            colunas = 0;
		        }
		    }	
		    umUsuario += "\t\t\t</div>";
		    umUsuario += "\t\t</div>";
		    umUsuario += "\t\t<label for=\"data\">Data de nascimento:</label>";
		    umUsuario += "\t\t<input type=\"date\" class=\"input--register\" name=\"data\" value=\"" + usuario.getData_Nascimento() + "\" required></br>";
		    umUsuario += "\t\t<input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\">";
		    umUsuario += "\t</form>";
		} else if (tipo == FORM_DETAIL){
		    int ID_Usuario = usuario.getID_Usuario();

		    umUsuario += "\t<div class=\"area-login\">";
		    umUsuario += "\t\t<div class=\"login\">";
		    umUsuario += "\t\t\t<div>";
		    umUsuario += "\t\t\t\t<img src=\"./img/CINEMAFICA.png\">";
		    umUsuario += "\t\t\t</div>";
		    umUsuario += "\t\t\t<div class=\"form--register\">";
		    umUsuario += "\t\t\t\t<h2>Detalhar Usuário (ID_Usuario " + ID_Usuario + ")</h2>";
		    umUsuario += "\t\t\t\t<p>Email: "+ usuario.getEmail() +"</p>";
		    umUsuario += "\t\t\t\t<p>Senha: "+ usuario.getSenha() +"</p>";
		    umUsuario += "\t\t\t</div>";
		    umUsuario += "\t\t</div>";
		    umUsuario += "\t</div>";	
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-USUARIO>", umUsuario);
		
		String list = "<div class=\"relacao-usuarios\">";
		list += "<div class=\"titulo-relacao\">Relação de Usuários</div>";
		list += "<div class=\"header-relacao\">";
		list += "<div class=\"header-item\"><a href=\"/usuario/list/" + FORM_ORDERBY_ID_USUARIO + "\">ID_Usuario</a></div>";
		list += "<div class=\"header-item\"><a href=\"/usuario/list/" + FORM_ORDERBY_EMAIL + "\">Email</a></div>";
		list += "<div class=\"header-item\"><a href=\"/usuario/list/" + FORM_ORDERBY_SENHA + "\">Senha</a></div>";
		list += "<div class=\"header-item\">Detalhar</div>";
		list += "<div class=\"header-item\">Atualizar</div>";
		list += "<div class=\"header-item\">Excluir</div>";
		list += "</div>";

		List<Usuario> usuarios;
		if (orderBy == FORM_ORDERBY_ID_USUARIO) {
		    usuarios = usuarioDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_EMAIL) {
		    usuarios = usuarioDAO.getOrderByEmail();
		} else if (orderBy == FORM_ORDERBY_SENHA) {
		    usuarios = usuarioDAO.getOrderBySenha();
		} else {
		    usuarios = usuarioDAO.get();
		}

		int i = 0;
		for (Usuario p : usuarios) {
		    String bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
		    list += "<div class=\"usuario-item\" style=\"background-color: " + bgcolor + ";\">";
		    list += "<div class=\"item\">" + p.getID_Usuario() + "</div>";
		    list += "<div class=\"item\">" + p.getEmail() + "</div>";
		    list += "<div class=\"item\">" + p.getSenha() + "</div>";
		    list += "<div class=\"item\"><a href=\"/usuario/" + p.getID_Usuario() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></div>";
		    list += "<div class=\"item\"><a href=\"/usuario/update/" + p.getID_Usuario() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></div>";
		    list += "<div class=\"item\"><a href=\"javascript:confirmarDeleteUsuario('" + p.getID_Usuario() + "', '" + p.getEmail() + "', '" + p.getSenha() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></div>";
		    list += "</div>";
		}
		list += "</div>";
		form = form.replaceFirst("<LISTAR-USUARIO>", list);
	}
	
	public Object login(Request request, Response response) {
	    String Email = request.queryParams("Email");
	    String Senha = request.queryParams("Senha");
	    Usuario usuario = new Usuario();

	    String resp = "";

	    if ((usuario = usuarioDAO.getEmail(Email)) != null) {
	    	if (BCrypt.checkpw(Senha, usuario.getSenha())) {
	            response.redirect("/index.html");
	        } else {
	            resp = "Email ou senha incorretos.";
	        }
	    }

	    return resp;
	}
	
	public Object insert(Request request, Response response) {
		String Nome = request.queryParams("Nome");
	    String Email = request.queryParams("Email");
	    String Senha = request.queryParams("Senha");
	    String[] Generos = request.queryParamsValues("Generos");
	    String Data_Nascimento = request.queryParams("Data_Nascimento");
	    String Data_Ingresso = request.queryParams("Data_Ingresso");

	    String resp = "";

	    Usuario usuario = new Usuario(Nome, Email, Senha, Generos, Data_Nascimento, Data_Ingresso, 0);

	    if (usuarioDAO.insert(usuario) == true) {
	        resp = "<script>window.location.href = '../login.html';</script>";
	        response.status(201);
	    } else {
	        resp = "Usuario não inserido!";
	        response.status(404);
	    }

	    return resp;
	}

	
	public Object get(Request request, Response response) {
		int ID_Usuario = Integer.parseInt(request.params(":ID_Usuario"));
		Usuario usuario = (Usuario) usuarioDAO.get(ID_Usuario);
		
		if (usuario != null) {
			response.status(200);
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_EMAIL);
        } else {
            response.status(404);
            String resp = "Usuario " + ID_Usuario + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\""+ resp +"\">");
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int ID_Usuario = Integer.parseInt(request.params(":ID_Usuario"));		
		Usuario usuario = (Usuario) usuarioDAO.get(ID_Usuario);
		
		if (usuario != null) {
			response.status(200);
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_EMAIL);
        } else {
            response.status(404);
            String resp = "Usuario " + ID_Usuario + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\""+ resp +"\">");
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int ID_Usuario = Integer.parseInt(request.params(":ID_Usuario"));
		Usuario usuario = usuarioDAO.get(ID_Usuario);
        String resp = "";       

        if (usuario != null) {
        	usuario.setNome(request.queryParams("Nome"));
        	usuario.setEmail(request.queryParams("Email"));
        	usuario.setSenha(request.queryParams("Senha"));
        	usuario.setGeneros(request.queryParamsValues("Generos"));
        	usuario.setData_Nascimento(request.queryParams("Data_Nascimento"));
        	usuario.setData_Ingresso(request.queryParams("Data_Nascimento"));
        	usuario.setQuantidade_Criticas(Integer.parseInt(request.queryParams("Quantidade_Criticas")));
        	usuarioDAO.update(usuario);
        	response.status(200);
            resp = "Usuario (ID_Usuario " + usuario.getID_Usuario() + ") atualizado!";
        } else {
            response.status(404);
            resp = "Usuario (ID_Usuario \" + usuario.getID_Usuario() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int ID_Usuario = Integer.parseInt(request.params(":ID_Usuario"));
        Usuario usuario = usuarioDAO.get(ID_Usuario);
        String resp = "";       

        if (usuario != null) {
            usuarioDAO.delete(ID_Usuario);
            response.status(200);
            resp = "Usuario (" + ID_Usuario + ") excluído!";
        } else {
            response.status(404);
            resp = "Usuario (" + ID_Usuario + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" nome=\"msg\" email=\"msg\" generos=\"msg\" nascimento=\"msg\" ingresso=\"msg\" criticas=\"msg\" value=\""+ resp +"\">");
	}
}
