package com.project.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "llibres")
public class Llibre implements Serializable {

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "id")
    private long llibreId;

    @Column(nullable = false, length = 50)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String titol;

    @Column(nullable = false, length = 50)
    private String editorial;

    @Column(nullable = false)
    private int anyPublicacio;

    @ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "llibre_autor",
        joinColumns = @JoinColumn(name = "llibre_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autors = new HashSet<>();

    @OneToMany(mappedBy = "llibre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Exemplar> exemplars = new HashSet<>();

    public Llibre() {}

    public Llibre(String isbn, String titol, String editorial, int anyPublicacio) {
        this.isbn = isbn;
        this.titol = titol;
        this.editorial = editorial;
        this.anyPublicacio = anyPublicacio;
    }

    public long getLlibreId() {
        return llibreId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitol() {
        return titol;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAnyPublicacio() {
        return anyPublicacio;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public Set<Exemplar> getExemplars() {
        return exemplars;
    }

    public void setLlibreId(long llibreId) {
        this.llibreId = llibreId;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setAnyPublicacio(int anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public void setAutors(Set<Autor> autors) {
        this.autors = autors;
    }

    public void setExemplars(Set<Exemplar> exemplars) {
        this.exemplars = exemplars;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Llibre[id=%d, isbn='%s', titol='%s'", 
            llibreId, isbn, titol));
        
        sb.append(String.format(", editorial='%s', any=%d", editorial, anyPublicacio));

        if (!autors.isEmpty()) {
            sb.append(", autors={");
            boolean first = true;
            for (Autor a : autors) {
                if (!first) sb.append(", ");
                sb.append(a.getNom());
                first = false;
            }
            sb.append("}");
        }

        if (!exemplars.isEmpty()) {
            sb.append(", exemplars={");
            boolean first = true;
            for (Exemplar e : exemplars) {
                if (!first) sb.append(", ");
                sb.append(e.getCodiBarres());
                first = false;
            }
            sb.append("}");
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Llibre llibre = (Llibre) o;
        return llibreId == llibre.llibreId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(llibreId);
    }
}
