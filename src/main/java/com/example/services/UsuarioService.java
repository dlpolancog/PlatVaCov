/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/**
 *
 * @author dlpol
 */
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioService {
    
    @PersistenceContext(unitName = "VacunaPU")
    EntityManager entityManager;
    
    private Long idUsuario;
    
    @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
    
    @POST
    @Path("/registrarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompetitor(UsuarioDTO usuario) {
        JSONObject rta = new JSONObject();
        Usuario usuarioTmp = new Usuario();
        usuarioTmp.setCorreo(usuario.getCorreo());
        usuarioTmp.setApellido(usuario.getApellido());
        usuarioTmp.setNombre(usuario.getNombre());
        usuarioTmp.setResidencia(usuario.getResidencia());
        usuarioTmp.setIdUsuario(usuario.getIdUsuario());
        usuarioTmp.setTelefono(usuario.getTelefono());
        usuarioTmp.setContrasena(usuario.getContrasena());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuarioTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(usuarioTmp);
            rta.put("persona_id", usuarioTmp.getIdUsuario());
        } 
        catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            usuarioTmp = null;
        } 
        finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();
    }
    
    @POST
    @Path("/log-in")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UsuarioDTO persona) throws JSONException {
        JSONObject rta = new JSONObject();
        Boolean login = false;
        
        Query q = entityManager.createQuery("select u from Usuario u order by u.idUsuario ASC");
        List<Usuario> usuarios = q.getResultList();
        Usuario user = new Usuario();
        if(!usuarios.isEmpty()){
            for (int i = 0; i < usuarios.size(); i++) {
                if(usuarios.get(i).getCorreo().equals(persona.getCorreo())&&usuarios.get(i).getContrasena().equals(persona.getContrasena())){
                    
                    user = entityManager.find(Usuario.class, usuarios.get(i).getIdUsuario());
                    user.setLogin(true);
                    try {
                        entityManager.getTransaction().begin();
                        entityManager.persist(user);
                        entityManager.getTransaction().commit();
                        entityManager.refresh(user);
                        rta.put("Ingresó correctamente", usuarios.get(i).getIdUsuario());
                    }catch (Throwable t) {
                        t.printStackTrace();
                        if (entityManager.getTransaction().isActive()) {
                            entityManager.getTransaction().rollback();
                        }
                    }finally {
                        entityManager.clear();
                        entityManager.close();
                    }
                    break;
                }
                else{
                    if((i)==(usuarios.size()-1)){
                        throw new NotAuthorizedException("NotAuthorizedException");
                    }
                }
            }  
        }
        else{
            throw new NotAuthorizedException("NotAuthorizedException");
        }
        
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();

    }
    
    public class NotAuthorizedException extends WebApplicationException {
        public NotAuthorizedException(String message) {
            super(Response.status(Response.Status.UNAUTHORIZED).entity(message).type(MediaType.TEXT_PLAIN).build());
        }
    }

    @POST
    @Path("/agendar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCita(Cita cita) throws JSONException {
        
        Query q = entityManager.createQuery("select u from Usuario u order by u.idUsuario ASC");
        List<Usuario> usuarios = q.getResultList();
        Usuario usuario = new Usuario();
        for (int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getLogin().equals(true)){
                usuario = usuarios.get(i);
            }
        }
        
        JSONObject rta = new JSONObject();
        Cita citaTmp = new Cita();
        citaTmp.setFecha(cita.getFecha());
        citaTmp.setSitio(cita.getSitio());
        citaTmp.setHora(cita.getHora());
        citaTmp.setUsuario(usuario);
        
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(citaTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(citaTmp);
            rta.put("cita_id", citaTmp.getIdCita());
        }catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            citaTmp = null;
        }finally {
            entityManager.clear();
            entityManager.close();
        }
        
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();
    }
      
    @GET
    @Path("/consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Usuario u order by u.idUsuario ASC");
        List<Usuario> usuarios = q.getResultList();
        Usuario user = new Usuario();
        for (int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getLogin().equals(true)){
                user = usuarios.get(i);
            }
        }
        List<Cita> citas = user.getCitas();
        System.out.println(citas.size());
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(citas).build();

    }
    
    @POST
    @Path("/cancelar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelarCita(String cadenaJson) throws JSONException{
        JSONObject objetoJson = new JSONObject(cadenaJson);
        JSONObject rta = new JSONObject();
        
        Long idCita = objetoJson.getLong("idCita");
        Cita cita = entityManager.find(Cita.class, idCita);
        Usuario user = cita.getUsuario();
        user.cancelatCita(cita);
        
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(cita));
            entityManager.getTransaction().commit();
            rta.put("Cita cancelada", idCita);
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.clear();
            entityManager.close();
        }
           
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();
    }
    
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios() {
        Query q = entityManager.createQuery("select u from Usuario u order by u.idUsuario ASC");
        List<Usuario> competitors = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(competitors).build();

    }
    
    @POST
    @Path("/reportarSintomas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reporteSintomas(ReporteSintomas reporte){
        JSONObject rta = new JSONObject();
        //rta.put("Cita cancelada", idCita);
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();
    }
}
