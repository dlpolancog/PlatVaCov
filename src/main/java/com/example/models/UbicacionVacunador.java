/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;
import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author dlpol
 */
@Entity
public class UbicacionVacunador implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUbicacionVacunador;
    
    @NotNull
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;
    
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;
    
    private String fecha;
    private String lugarActual; 
    private String hora;
    private Long idCita;

    public UbicacionVacunador() {
    }

    public UbicacionVacunador(String fecha, String lugarActual, String hora, Long idCita) {
        this.fecha = fecha;
        this.lugarActual = lugarActual;
        this.hora = hora;
        this.idCita = idCita;
    }
    
    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = Calendar.getInstance();
    }
 
    @PrePersist
    private void creationTimestamp() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    public Long getIdUbicacionVacunador() {
        return idUbicacionVacunador;
    }

    public void setIdUbicacionVacunador(Long idUbicacionVacunador) {
        this.idUbicacionVacunador = idUbicacionVacunador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugarActual() {
        return lugarActual;
    }

    public void setLugarActual(String lugarActual) {
        this.lugarActual = lugarActual;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }
    
    
}
