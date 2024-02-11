package app;

import static spark.Spark.*;
import java.net.*;
import service.*;

public class Aplicacao {
	private static UsuarioService usuarioService = new UsuarioService();
	private static FilmeService filmeService = new FilmeService();
	private static ResenhaService resenhaService = new ResenhaService();
	
	public static void main(String[] args) {
		try {
			port(4567);
		    
		    staticFiles.location("/public");
		    
		    post("/usuario/insert", (request, response) -> usuarioService.insert(request, response));
		    post("/usuario/login", (request, response) -> usuarioService.login(request, response));
		    get("/usuario/:ID_Usuario", (request, response) -> usuarioService.get(request, response));
		    get("/usuario/list/:orderby", (request, response) -> usuarioService.getAll(request, response));
		    get("/usuario/update/:ID_Usuario", (request, response) -> usuarioService.getToUpdate(request, response));
		    post("/usuario/update/:ID_Usuario", (request, response) -> usuarioService.update(request, response));
		    get("/usuario/delete/:ID_Usuario", (request, response) -> usuarioService.delete(request, response));
		    
		    post("/filme/insert", (request, response) -> filmeService.insert(request, response));
		    get("/filme/:ID_Filme", (request, response) -> filmeService.get(request, response));
		    get("/filme/list/:orderby", (request, response) -> filmeService.getAll(request, response));
		    get("/filme/update/:ID_Filme", (request, response) -> filmeService.getToUpdate(request, response));
		    post("/filme/update/:ID_Filme", (request, response) -> filmeService.update(request, response));
		    get("/filme/delete/:ID_Filme", (request, response) -> filmeService.delete(request, response));
		    
		    post("/resenha/insert", (request, response) -> resenhaService.insert(request, response));
		    get("/resenha/:ID_Resenha", (request, response) -> resenhaService.get(request, response));
		    get("/resenha/list/:orderby", (request, response) -> resenhaService.getAll(request, response));
		    get("/resenha/update/:ID_Resenha", (request, response) -> resenhaService.getToUpdate(request, response));
		    post("/resenha/update/:ID_Resenha", (request, response) -> resenhaService.update(request, response));
		    get("/resenha/delete/:ID_Resenha", (request, response) -> resenhaService.delete(request, response));

		}
		catch (Exception e) {
            e.printStackTrace();	
		}
	}
}   
