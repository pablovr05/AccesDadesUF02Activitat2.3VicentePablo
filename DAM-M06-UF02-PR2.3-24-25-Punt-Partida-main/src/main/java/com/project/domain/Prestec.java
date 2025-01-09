package com.project.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "prestecs")
public class Prestec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long prestecId;

    @ManyToOne
    @JoinColumn(name = "exemplar_id", nullable = false)
    private Exemplar exemplar;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "data_prestec", nullable = false)
    private LocalDate dataPrestec;

    @Column(name = "data_retorna_prevista", nullable = false)
    private LocalDate dataRetornPrevista;

    @Column(name = "data_retorna_real")
    private LocalDate dataRetornReal;

    @Column(name = "actiu", nullable = false)
    private boolean actiu;

    public Prestec() {}

    public Prestec(Exemplar exemplar, Persona persona, LocalDate dataPrestec, LocalDate dataRetornPrevista) {
        this.exemplar = exemplar;
        this.persona = persona;
        this.dataPrestec = dataPrestec;
        this.dataRetornPrevista = dataRetornPrevista;
        this.dataRetornReal = null;
        this.actiu = true;
    }

    public long getPrestecId() {
        return prestecId;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public Persona getPersona() {
        return persona;
    }

    public LocalDate getDataPrestec() {
        return dataPrestec;
    }

    public LocalDate getDataRetornPrevista() {
        return dataRetornPrevista;
    }

    public LocalDate getDataRetornReal() {
        return dataRetornReal;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setPrestecId(long prestecId) {
        this.prestecId = prestecId;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setDataPrestec(LocalDate dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public void setDataRetornPrevista(LocalDate dataRetornPrevista) {
        this.dataRetornPrevista = dataRetornPrevista;
    }

    public void setDataRetornReal(LocalDate dataRetornReal) {
        this.dataRetornReal = dataRetornReal;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public boolean estaRetardat() {
        return actiu && LocalDate.now().isAfter(dataRetornPrevista);
    }

    public long getDiesRetard() {
        if (!estaRetardat()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(dataRetornPrevista, LocalDate.now());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Prestec[id=%d", prestecId));

        if (exemplar != null) {
            sb.append(String.format(", exemplar='%s'", exemplar.getCodiBarres()));
        }

        if (persona != null) {
            sb.append(String.format(", persona='%s'", persona.getNom()));
        }

        sb.append(String.format(", dataPrestec='%s'", dataPrestec));
        sb.append(String.format(", dataRetornPrevista='%s'", dataRetornPrevista));

        if (dataRetornReal != null) {
            sb.append(String.format(", dataRetornReal='%s'", dataRetornReal));
        }

        sb.append(String.format(", actiu=%s", actiu));

        if (estaRetardat()) {
            sb.append(String.format(", diesRetard=%d", getDiesRetard()));
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestec prestec = (Prestec) o;
        return prestecId == prestec.prestecId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(prestecId);
    }
}
