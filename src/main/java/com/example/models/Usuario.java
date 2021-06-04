/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author dlpol
 */

@Entity
public class Usuario implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long idUsuario;
    
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
    private String telefono;
    private String correo;
    private String residencia;
    private String contrasena;
    private Long estadoVacuna;
    private Boolean login;
    
    
    @OneToMany(mappedBy="usuario", cascade=CascadeType.ALL)
    private List<Cita> citas;

    public Usuario() {
    }

    public Usuario(Long idUsuario, String nombre, String apellido, String telefono, String correo, String residencia, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.residencia = residencia;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getEstadoVacuna() {
        return estadoVacuna;
    }

    public void setEstadoVacuna(Long estadoVacuna) {
        this.estadoVacuna = estadoVacuna;
    }
    
    public List<Cita> getCitas() {
        return citas;
    }
    
    public void addCitas(Cita cita){
        citas.add(cita);
    }
    
    public void cancelatCita(Cita cita){
        citas.remove(cita);
        cita.setUsuario(null);
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }
    
    
}

