package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import static javax.persistence.CascadeType.ALL;import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
*
* @author Sanamayaa
*/

@Entity
public class Cita implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;
    
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;
    
    
    
    private String fecha;
    private String sitio; 
    private String hora;
    private String fase;
    
    @ManyToOne(cascade=ALL)
    private Persona persona;
    
    public Cita() {
        
    }

    public Cita(String fecha, String hora, String sitio, String fase) {
        this.fecha = fecha;
        this.hora = hora;
        this.sitio = sitio;
        this.fase = fase;
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

    public String getFecha() {
        return fecha;
    }

    public String getSitio() {
        return sitio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }
    
    


}