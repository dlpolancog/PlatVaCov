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
import javax.ws.rs.PathParam;
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

@Path("/cita")
@Produces(MediaType.APPLICATION_JSON)
public class CitaService {
    
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
    @Path("/consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Cita u order by u.id ASC");
        List<Cita> citas = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(citas).build();
    }
    
    @POST
    @Path("/agendar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCita(Cita cita) {
        JSONObject rta = new JSONObject();
        Cita citaTmp = new Cita();
        citaTmp.setFecha(cita.getFecha());
        citaTmp.setSitio(cita.getSitio());
        citaTmp.setHora(cita.getHora());
        citaTmp.setFase(cita.getFase());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(citaTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(citaTmp);
            rta.put("cita_id", citaTmp.getId());
        } 
        catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            citaTmp = null;
        } 
        finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();
    }
    
    @DELETE
    @Path("/cancelar/{citaId}")
    public void cancelarCita(@PathParam("citaId")int id){
        Query q = entityManager.createQuery("select u from Cita u order by u.id ASC");
        
        entityManager.remove();
    }
}
