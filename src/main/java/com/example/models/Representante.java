/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dlpol
 */
@Entity
public class Representante implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long idRepresentante;
    
    @NotNull
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;
    
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;
    
    private String nombre;
    private String apellido;
    private String fecha;
    private String telefono;
    private String correo;
    private String contrasena;
    
    @OneToMany(cascade=ALL, mappedBy="representante")
    private Set<Eps> eps;

    public Representante() {
    }

    public Representante(Long id, String nombre, String apellido, String fecha, String telefono, String correo, String contrasena) {
        this.idRepresentante = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = Calendar.getInstance();
    }
 
    @PrePersist
    private void creationTimestamp() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    public Long getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(Long idRepresentante) {
        this.idRepresentante = idRepresentante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public Set<Eps> getEps() {
        return eps;
    }
    
}
