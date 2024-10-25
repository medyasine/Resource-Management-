package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class    Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                ", sdr=" + sdr +
                ", category=" + category +
                '}';
    }

    private String destination;
    private Double prix;
    private long quantite;
    private Long sdr;
    private Category category;

    public Product() {
        super();
    }

    public Product(String destination, Double prix, long quantite, Long sdr, Category category) {
        this.destination = destination;
        this.prix = prix;
        this.quantite = quantite;
        this.sdr = sdr;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public long getQuantite() {
        return quantite;
    }

    public void setQuantite(long quantite) {
        this.quantite = quantite;
    }

    public Long getSdr() {
        return sdr;
    }

    public void setSdr(Long sdr) {
        this.sdr = sdr;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
