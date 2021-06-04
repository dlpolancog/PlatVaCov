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
import javax.persistence.OptimisticLockException;
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

@Path("/representante")
@Produces(MediaType.APPLICATION_JSON)
public class RepresentanteService {
    
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
        Query q = entityManager.createQuery("select u from Representante u order by u.idRepresentante ASC");
        List<Representante> representantes = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(representantes).build();
    }
    
    @POST
    @Path("/registro")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRepresentante(Representante representante) {
        JSONObject rta = new JSONObject();
        Representante representanteTmp = new Representante();
        representanteTmp.setCorreo(representante.getCorreo());
        representanteTmp.setApellido(representante.getApellido());
        representanteTmp.setNombre(representante.getNombre());
        representanteTmp.setIdRepresentante(representante.getIdRepresentante());
        representanteTmp.setTelefono(representante.getTelefono());
        representanteTmp.setContrasena(representante.getContrasena());
        representanteTmp.setFecha(representante.getFecha());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(representanteTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(representanteTmp);
            rta.put("persona_id", representanteTmp.getIdRepresentante());
        } 
        catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            representanteTmp = null;
        } 
        finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin","*").entity(rta).build();
    }
}
