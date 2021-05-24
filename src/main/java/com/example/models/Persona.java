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
public class Persona implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    
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
    private String localidad;
    private String direccion;
    private String contrasena;
    private String tipo_documento;
    
    @OneToMany(cascade=ALL, mappedBy="Persona")
    private Set<Cita> citas;

    public Persona() {
    }

    public Persona(Long id, String nombre, String apellido, String fecha, String telefono, String correo, String localidad, String direccion, String contrasena, String tipo_documento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.telefono = telefono;
        this.correo = correo;
        this.localidad = localidad;
        this.direccion = direccion;
        this.contrasena = contrasena;
        this.tipo_documento = tipo_documento;
    }
    
    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = Calendar.getInstance();
    }
 
    @PrePersist
    private void creationTimestamp() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }
    
    public Set<Cita> getCitas() {
        return citas;
    }

      
}

