package service;

import dao.FilmeDAO;
import model.Filme;

import java.util.*;
import java.io.*;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import spark.*;
import okhttp3.*;

public class FilmeService {

	private FilmeDAO filmeDAO = new FilmeDAO();
	private final int FORM_ORDERBY_ID_FILME = 1;
	private final int FORM_ORDERBY_TITULO = 2;
	private final int FORM_ORDERBY_DATA_LANCAMENTO = 3;
	
	public FilmeService() {
        fetchMoviesFromTMDB();
	}

	private List<Filme> fetchMoviesFromTMDB() {
	    List<Filme> filmes = new ArrayList<>();

	    try {
	        OkHttpClient client = new OkHttpClient();
	        okhttp3.Request request = new okhttp3.Request.Builder().url("https://api.themoviedb.org/3/discover/movie?api_key=d18b2a1e9dc30363c1cb5fbefdd23412&language=en-US&sort_by=release_date.desc&include_adult=false&include_video=false&page=1").build(); // Note the sort_by parameter

	        okhttp3.Response response = client.newCall(request).execute();
	        String jsonData = response.body().string();

	        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
	        JsonArray results = jsonObject.getAsJsonArray("results");
	        
	        Map<Integer, String> genreIdToGenre = new HashMap<>();
	        genreIdToGenre.put(28, "Ação");
	        genreIdToGenre.put(12, "Aventura");
	        genreIdToGenre.put(16, "Animação");
	        genreIdToGenre.put(35, "Comédia");
	        genreIdToGenre.put(18, "Drama");
	        genreIdToGenre.put(99, "Documentário");
	        genreIdToGenre.put(10751, "Família");
	        genreIdToGenre.put(14, "Fantasia");
	        genreIdToGenre.put(37, "Faroeste");
	        genreIdToGenre.put(10752, "Guerra");
	        genreIdToGenre.put(36, "História");
	        genreIdToGenre.put(10402, "Musical");
	        genreIdToGenre.put(9648, "Mistério");
	        genreIdToGenre.put(80, "Policial");
	        genreIdToGenre.put(10749, "Romance");
	        genreIdToGenre.put(878, "Scifi");
	        genreIdToGenre.put(10770, "Televisivo");
	        genreIdToGenre.put(27, "Terror");
	        genreIdToGenre.put(53, "Thriller");

	        for (JsonElement element : results) {
	            JsonObject movieObject = element.getAsJsonObject();

	            String title = movieObject.get("title").getAsString();
	            String overview = movieObject.get("overview").getAsString();
	            String releaseDate = movieObject.get("release_date").getAsString();
	            String originalLanguage = movieObject.get("original_language").getAsString();
	            boolean adult = movieObject.get("adult").getAsBoolean();

	            JsonArray genreIdsArray = movieObject.getAsJsonArray("genre_ids");
	            List<Integer> genreIds = new ArrayList<>();

	            for (JsonElement genreIdElement : genreIdsArray) {
	                int genreId = genreIdElement.getAsInt();
	                genreIds.add(genreId);
	            }
	            
	            String[] genres;
	            if (genreIds.isEmpty()) {
	                genres = new String[]{"Gênero Desconhecido"};
	            } else {
	                genres = new String[genreIds.size()];
	                for (int i = 0; i < genreIds.size(); i++) {
	                    int genreId = genreIds.get(i);
	                    genres[i] = genreIdToGenre.getOrDefault(genreId, "Gênero Desconhecido");
	                }
	            }

	            Filme filme = new Filme(-1, genres, originalLanguage, title, adult, overview, releaseDate);

	            filmes.add(filme);
	        }

	        for (Filme filme : filmes) {
	            filmeDAO.insert(filme);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return filmes;
	}
	
	public Object insert(spark.Request request, spark.Response response) {
		String[] Generos = request.queryParamsValues("Generos");
		String Titulo = request.queryParams("Titulo");
		String Linguagem = request.queryParams("Linguagem");
		String Descricao = request.queryParams("Descricao");
		String ClassificacaoString = request.queryParams("Classificacao_Indicativa");
		Boolean Classificacao_Indicativa = "true".equalsIgnoreCase(ClassificacaoString);
		String Data_Lancamento = request.queryParams("Data_Lancamento");

	    String resp = "";

	    Filme filme = new Filme(-1, Generos, Linguagem, Titulo, Classificacao_Indicativa, Descricao, Data_Lancamento);

	    if (filmeDAO.insert(filme) == true) {
	        resp = "Filme inserido!";
	        response.status(201);
	    } else {
	        resp = "Filme não inserido!";
	        response.status(404);
	    }

	    return resp;
	}

	
	public Object get(spark.Request request, spark.Response response) {
	    int ID_Filme = Integer.parseInt(request.params(":ID_Filme"));
	    Filme filme = (Filme) filmeDAO.get(ID_Filme);

	    if (filme != null) {
	        response.status(200);
	        return "Filme encontrado.";
	    } else {
	        response.status(404);
	        return "Filme não encontrado.";
	    }
	}

	public Object getToUpdate(spark.Request request, spark.Response response) {
	    int ID_Filme = Integer.parseInt(request.params(":ID_Filme"));
	    Filme filme = (Filme) filmeDAO.get(ID_Filme);

	    if (filme != null) {
	        response.status(200);
	        return "Detalhes do Filme para Atualização: " + filme.getTitulo();
	    } else {
	        response.status(404);
	        return "Filme não encontrado.";
	    }
	}
	
	private String generateMovieList(List<Filme> filmes, int orderBy) {
        String list = "<div class=\"relacao-filmes\">";
        list += "<div class=\"titulo-relacao\">Relação de Filmes</div>";
        list += "<div class=\"header-relacao\">";
        list += "<div class=\"header-item\"><a href=\"/filme/list/" + FORM_ORDERBY_ID_FILME + "\">ID_Filme</a></div>";
        list += "<div class=\"header-item\"><a href=\"/filme/list/" + FORM_ORDERBY_TITULO + "\">Título</a></div>";
        list += "<div class=\"header-item\"><a href=\"/filme/list/" + FORM_ORDERBY_DATA_LANCAMENTO + "\">Data de Lançamento</a></div>";
        list += "<div class=\"header-item\">Detalhar</div>";
        list += "</div>";

        if (orderBy == FORM_ORDERBY_DATA_LANCAMENTO) {
            filmes.sort(Comparator.comparing(Filme::getData_Lancamento));
        }

        for (Filme filme : filmes) {
            String bgcolor = "#fff5dd";
            list += "<div class=\"filme-item\" style=\"background-color: " + bgcolor + ";\">";
            list += "<div class=\"item\">" + filme.getID_Filme() + "</div>";
            list += "<div class=\"item\">" + filme.getTitulo() + "</div>";
            list += "<div class=\"item\">" + filme.getData_Lancamento() + "</div>";
            list += "<div class=\"item\"><a href=\"/filme/" + filme.getID_Filme() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></div>";
            list += "</div>";
        }

        list += "</div>";
        return list;
    }
	
	public Object getAll(spark.Request request, spark.Response response) {
	    int orderBy = Integer.parseInt(request.params(":orderby"));

	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");

	    List<Filme> filmes = fetchMoviesFromTMDB();
	    String list = generateMovieList(filmes, orderBy);

	    return list;
	}
		
	
	public Object update(spark.Request request, spark.Response response) {
        int ID_Filme = Integer.parseInt(request.params(":ID_Filme"));
		Filme filme = filmeDAO.get(ID_Filme);
        String resp = "";       

        if (filme != null) {
        	filme.setGeneros(request.queryParamsValues("Generos"));
        	filme.setLinguagem(request.queryParams("Linguagem"));
        	filme.setTitulo(request.queryParams("Titulo"));
        	String ClassificacaoString = request.queryParams("Classificacao_Indicativa");
        	filme.setClassificacao_Indicativa("true".equalsIgnoreCase(ClassificacaoString));
        	filme.setDescricao(request.queryParams("Descricao"));
        	filme.setData_Lancamento(request.queryParams("Data_Lancamento"));
        	filmeDAO.update(filme);
        	response.status(200);
            resp = "Filme atualizado!";
        } else {
            response.status(404);
            resp = "Filme não encontrado!";
        }
		
		return resp;
	}

	
	public Object delete(spark.Request request, spark.Response response) {
        int ID_Filme = Integer.parseInt(request.params(":ID_Filme"));
        Filme filme = filmeDAO.get(ID_Filme);
        String resp = "";       

        if (filme != null) {
            filmeDAO.delete(ID_Filme);
            response.status(200);
            resp = "Filme excluído!";
        } else {
            response.status(404);
            resp = "Filme não encontrado!";
        }
		
		return resp;
	}
}
