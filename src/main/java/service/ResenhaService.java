package service;

import dao.ResenhaDAO;
import model.Resenha;
import java.util.*;
import java.io.*;
import java.time.*;
import spark.Request;
import spark.Response;

public class ResenhaService {

	private ResenhaDAO resenhaDAO = new ResenhaDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID_RESENHA = 1;
	private final int FORM_ORDERBY_DATA = 2;
	
	
	public ResenhaService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Resenha(), FORM_ORDERBY_ID_RESENHA);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Resenha(), orderBy);
	}

	
	public void makeForm(int tipo, Resenha resenha, int orderBy) {
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
		
		String umaResenha = "";
		if(tipo != FORM_INSERT) {
			umaResenha += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaResenha += "\t\t<tr>";
			umaResenha += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/resenha/list/1\">Novo Resenha</a></b></font></td>";
			umaResenha += "\t\t</tr>";
			umaResenha += "\t</table>";
			umaResenha += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/resenha/";
			String buttonLabel;
			int ID_Resenha = resenha.getID_Resenha();
			if (tipo == FORM_INSERT){
				action += "insert";
				buttonLabel = "Publicar";
			} else {
				action += "update/" + resenha.getID_Resenha();
				ID_Resenha = resenha.getID_Resenha();
				buttonLabel = "Atualizar";
			}
			umaResenha += "\t<form class=\"form--register\" action=\"" + action + "\"method=\"post\" id=\"form-add\">";
			umaResenha += "\t\t<label><input class=\"input--register\" type=\"text\" name=\"ID_Resenha\" value=\"" + ID_Resenha + "\">";
		    umaResenha += "\t\t<input type=\"radio\" class=\"input--register\" name=\"opiniao\" value=\"" + resenha.getOpiniao() + "\" required>";
		    umaResenha += "\t\t<input type=\"radio\" class=\"input--register\" name=\"opiniao\" value=\"" + resenha.getOpiniao() + "\" required>";
		    umaResenha += "\t\t<input type=\"conteudo\" class=\"input--register\" name=\"conteudo\" value=\"" + resenha.getConteudo() + "\" required>";
		    umaResenha += "\t\t<input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\">";
		    umaResenha += "\t</form>";
		}
		else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-RESENHA>", umaResenha);
		
		String list = "<div class=\"relacao-resenhas\">";
		list += "<div class=\"titulo-relacao\">Relação de Usuários</div>";
		list += "<div class=\"header-relacao\">";
		list += "<div class=\"header-item\"><a href=\"/resenha/list/" + FORM_ORDERBY_ID_RESENHA + "\">ID_Resenha</a></div>";
		list += "<div class=\"header-item\"><a href=\"/resenha/list/" + FORM_ORDERBY_DATA + "\">Data</a></div>";
		list += "<div class=\"header-item\">Detalhar</div>";
		list += "<div class=\"header-item\">Atualizar</div>";
		list += "<div class=\"header-item\">Excluir</div>";
		list += "</div>";

		List<Resenha> resenhas;
		if (orderBy == FORM_ORDERBY_ID_RESENHA) {
		    resenhas = resenhaDAO.getOrderByID_Resenha();
		} else if (orderBy == FORM_ORDERBY_DATA) {
		    resenhas = resenhaDAO.getOrderByData();
		} else {
		    resenhas = resenhaDAO.get();
		}

		int i = 0;
		for (Resenha p : resenhas) {
		    String bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
		    list += "<div class=\"resenha-item\" style=\"background-color: " + bgcolor + ";\">";
		    list += "<div class=\"item\">" + p.getID_Resenha() + "</div>";
		    list += "<div class=\"item\">" + p.getData() + "</div>";
		    list += "<div class=\"item\"><a href=\"/resenha/" + p.getID_Resenha() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></div>";
		    list += "<div class=\"item\"><a href=\"/resenha/update/" + p.getID_Resenha() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></div>";
		    list += "<div class=\"item\"><a href=\"javascript:confirmarDeleteResenha('" + p.getID_Resenha() + "', '" + p.getData() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></div>";
		    list += "</div>";
		}
		list += "</div>";
		form = form.replaceFirst("<LISTAR-RESENHA>", list);
	}	
	
	public Object insert(Request request, Response response) {
		String Conteudo = request.queryParams("Conteudo");
		String OpiniaoString = request.queryParams("Opiniao");
		Boolean Opiniao = "true".equalsIgnoreCase(OpiniaoString);
		LocalDate currentDate = LocalDate.now();
	    String Data = currentDate.toString();
	
	    String resp = "";

	    Resenha resenha = new Resenha(Conteudo, Opiniao, Data);

	    if (resenhaDAO.insert(resenha) == true) {
	        resp = "<script>window.location.href = '../detalhe.html';</script>";
	        response.status(201);
	    } else {
	        resp = "Resenha não inserido!";
	        response.status(404);
	    }

	    return resp;
	}

	
	public Object get(Request request, Response response) {
		int ID_Resenha = Integer.parseInt(request.params(":ID_Resenha"));
		Resenha resenha = (Resenha) resenhaDAO.get(ID_Resenha);
		
		if (resenha != null) {
			response.status(200);
			makeForm(FORM_DETAIL, resenha, FORM_ORDERBY_DATA);
        } else {
            response.status(404);
            String resp = "Resenha não encontrada.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\""+ resp +"\">");
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int ID_Resenha = Integer.parseInt(request.params(":ID_Resenha"));		
		Resenha resenha = (Resenha) resenhaDAO.get(ID_Resenha);
		
		if (resenha != null) {
			response.status(200);
			makeForm(FORM_UPDATE, resenha, FORM_ORDERBY_DATA);
        } else {
            response.status(404);
            String resp = "Resenha " + ID_Resenha + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\""+ resp +"\">");
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
        int ID_Resenha = Integer.parseInt(request.params(":ID_Resenha"));
		Resenha resenha = resenhaDAO.get(ID_Resenha);
        String resp = "";       

        if (resenha != null) {
        	resenha.setConteudo(request.queryParams("Conteudo"));
        	String OpiniaoString = request.queryParams("Opiniao");
    		Boolean Opiniao = "true".equalsIgnoreCase(OpiniaoString);
        	resenha.setOpiniao(Opiniao);
        	resenha.setData(request.queryParams("Data"));
        	resenhaDAO.update(resenha);
        	response.status(200);
            resp = "Resenha atualizada!";
        } else {
            response.status(404);
            resp = "Resenha não encontrada!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int ID_Resenha = Integer.parseInt(request.params(":ID_Resenha"));
        Resenha resenha = resenhaDAO.get(ID_Resenha);
        String resp = "";       

        if (resenha != null) {
            resenhaDAO.delete(ID_Resenha);
            response.status(200);
            resp = "Resenha excluída!";
        } else {
            response.status(404);
            resp = "Resenha não encontrada!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" conteudo=\"msg\" opiniao=\"msg\" data=\"msg\" usuario=\"msg\" filme=\"msg\" value=\""+ resp +"\">");
	}
}
