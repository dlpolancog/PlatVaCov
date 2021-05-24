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
@Path("/personas")
@Produces(MediaType.APPLICATION_JSON)
public class PersonaService {
    
    @PersistenceContext(unitName = "VacunaPU")
    EntityManager entityManager;
    
    @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Persona u order by u.id ASC");
        List<Persona> personas = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(personas).build();

    }
    
    @POST
    @Path("/registro")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompetitor(PersonaDTO persona) {
        JSONObject rta = new JSONObject();
        Persona personaTmp = new Persona();
        personaTmp.setCorreo(persona.getCorreo());
        personaTmp.setApellido(persona.getApellido());
        personaTmp.setNombre(persona.getNombre());
        personaTmp.setDireccion(persona.getDireccion());
        personaTmp.setLocalidad(persona.getLocalidad());
        personaTmp.setId(persona.getId());
        personaTmp.setTelefono(persona.getTelefono());
        personaTmp.setContrasena(persona.getContrasena());
        personaTmp.setTipo_documento(persona.getTipo_documento());
        personaTmp.setFecha(persona.getFecha());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(personaTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(personaTmp);
            rta.put("persona_id", personaTmp.getId());
        } 
        catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            personaTmp = null;
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
    public Response login(PersonaDTO persona) throws JSONException {
        JSONObject rta = new JSONObject();
        /*
        Competitor citaTmp = new Competitor();
        citaTmp.setAddress(competitor.getAddress());
        citaTmp.setContrasena(competitor.getContrasena());
        */
        
        Query q = entityManager.createQuery("select u from Competitor u order by u.id ASC");
        List<Persona> personas = q.getResultList();
        for (int i = 0; i < personas.size(); i++) {
            //System.out.println("******"+personas.get(i).getAddress() + " " + personas.get(i).getContrasena());
            //System.out.println("------"+citaTmp.getAddress() + " " + citaTmp.getContrasena());
            if(personas.get(i).getCorreo().equals(persona.getCorreo())&&personas.get(i).getContrasena().equals(persona.getContrasena())){
                rta.put("Ingresó correctamente", personas.get(i).getId());
                break;
            }
            else{
                if((i)==(personas.size()-1)){
                    throw new NotAuthorizedException("NotAuthorizedException");
                }
            }
        }
        
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();

    }
    
    public class NotAuthorizedException extends WebApplicationException {
        public NotAuthorizedException(String message) {
            super(Response.status(Response.Status.UNAUTHORIZED).entity(message).type(MediaType.TEXT_PLAIN).build());
        }
    }
    
}
