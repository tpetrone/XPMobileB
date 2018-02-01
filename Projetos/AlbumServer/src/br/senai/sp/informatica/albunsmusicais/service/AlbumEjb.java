package br.senai.sp.informatica.albunsmusicais.service;

import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.senai.sp.informatica.albunsmusicais.model.Album;

@Path("/")
@Stateless
@LocalBean
public class AlbumEjb {
	@PersistenceContext(name = "AlbumServer")
	private EntityManager manager;
	private final String url = "http://172.16.2.250:8080/AlbumServer/";
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// Versão
	@GET
	@Path("version")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response version() {
		return Response.ok("[{\"version\":\"Album Service 1.0 - Test\"}]").status(200).build();
	}

	// Lista Albums em ordem
	@GET
	@Path("lista/{ordem}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response listaIds(@PathParam("ordem") String ordem) {
		String queryString = "select t.id from Album t order by t.";
		if(ordem.equals("banda")) {
			queryString += "banda";
		} else if(ordem.equals("album")) {
			queryString += "nome";
		} else {
			queryString += "lancamento";
		}
		
		return Response.ok(manager.createQuery(queryString, Long.class)
				.getResultList()).status(200).build();
	}

	// Quantidade marcado para apagar
	@GET
	@Path("marcados")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response feito() {
		return Response.ok("[{\"count\":"
				+ ((Long) manager.createQuery("select count(t) from Album t where t.del = true")
						.getSingleResult()).intValue()
				+ "}]").status(200).build();
	}

	// Remove Albuns marcados para apagar
	@DELETE
	@Path("marcados")
	public Response removeMarcados() {
		try {
			manager.createQuery("delete from Album t where t.del = true").executeUpdate();
			return Response.noContent().build();
		} catch(PersistenceException ex) {
			return Response.notModified().build();
		}
	}

	// Localizar Por ID
	@GET
	@Path("album/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getById(@PathParam("id") long id) {
		Album album = manager.find(Album.class, id);
		return Response.ok(album).status(200).build();
	}
	
	// Inclui Album
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAlbum(Album obj) {
		try {
			Album Album = manager.merge(obj);
			URI uri = new URL(url + "album/" + Album.getId()).toURI();
			return Response.ok(Album).status(201).location(uri).build();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Capa length: " + obj.getCapa().length);
			logger.log(Level.SEVERE, ex.getMessage());
			return Response.status(500).build();
		}
	}
		
	// Remover
	@DELETE
	@Path("album/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delAlbum(@PathParam("id") long id) {
		try {
			Album album = manager.find(Album.class, id);
			manager.remove(album);
			manager.merge(album);
			return Response.noContent().build();
		} catch(PersistenceException ex) {
			return Response.notModified().build();
		}
	}

	// Altera Album
	@PUT
	@Path("album/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAlbum(Album obj) {
		Album album = obj;
		try {
			album = manager.merge(album);
			
			URI uri = new URL(url + "album/" + album.getId()).toURI();

			return Response.ok().status(200).location(uri).build();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			return Response.status(500).build();
		}
	}

	// Limpar Albuns marcados
	@DELETE
	@Path("limpar")
	public Response limpaMarcados() {
		try {
			manager.createQuery("update Album t set t.del = false where t.del = true").executeUpdate();
			return Response.noContent().build();
		} catch(PersistenceException ex) {
			return Response.notModified().build();
		}
	}
}
