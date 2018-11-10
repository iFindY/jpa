package de.arkadi.persistence.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = CD.FIND_ALL, query = "select c from CD c"),
})
public class CD extends Item implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(name = "total_duration")
    private Float totalDuration;

    @Column(name = "music_company")
    private String musicCompany;

    private String genre;

    /**
     * 0. There are two ways off referencing relationships.
     * You can reference a 'foreign key' of the relationship in the table or create a join table of  two table keys
     * referencing to each other.
     * There can be uni or bi-directional relationships as well.
     * OneToMany/ManyToMany are joined on extra table. OneToOne/ManyToOne are joined on column
     * <p>
     * 1. one-to-one relation ship default.
     * when a 'cd' is inside musician and a 'musician' object is inside cd.
     * with @oneToOne annotation we can manipulate the default mapping
     * <p>
     * 2. Default it will do join column on musician_id.
     * Can be manipulated with @JoinColumns("musician_fk")
     * <p>
     * 3. @OneToOne(fetch=fetchType.EAGER) is default and fetch all data at query.
     * fetchType.LAZY will load data only if asked for the data
     * <p>
     * 4. If you want to join in extra table you can specify this table with @JoinTable (name="cd_musician")
     * You can also name the join column names of the entities [input is always the entity key]
     * <p>
     * 5. If there is a collection you can use @OneToMany [in 'cd' there is a 'musician' collection]
     * and @ManyToOne [in 'musician' class is a 'cd' field]
     * <p>
     * 6. If there is a @ManyToMany relationship. In 'cd' class is a 'musician' collection
     * and in 'musician' class is a 'cd' collection.
     * JPA will see this relationship and do default configuration mapping.
     * <p>
     * 7. You can overwrite defaults of @OneToMany [in 'cd' many 'musicians']
     * and do join on column strategy with the @JoinOnColumn annotation.
     * This will create a foreign key in every 'musician' to the 'cd'.
     * With te extra @JoinOnColumn(name="cd_fk") you can specify key name in 'musician'.
     */
    @ManyToMany
    @JoinTable(name = "cd_musician",
            joinColumns = @JoinColumn(name = "cd_fk"),
            inverseJoinColumns = @JoinColumn(name = "musician_fk"))
    private Set<Musician> musicians = new HashSet<>();

    // ======================================
    // =              Constant              =
    // ======================================

    public static final String FIND_ALL = "CD.findAll";

    // ======================================
    // =            Constructors            =
    // ======================================

    public CD() {
    }

    public CD(String title, String description, Float unitCost, Float totalDuration, String genre) {
        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
        this.totalDuration = totalDuration;
        this.genre = genre;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public Float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Float totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getMusicCompany() {
        return musicCompany;
    }

    public void setMusicCompany(String musicCompany) {
        this.musicCompany = musicCompany;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<Musician> getMusicians() {
        return musicians;
    }

    public void setMusicians(Set<Musician> musicians) {
        this.musicians = musicians;
    }

    // ======================================
    // =    hashcode, equals & toString     =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CD cd = (CD) o;

        if (genre != null ? !genre.equals(cd.genre) : cd.genre != null) return false;
        if (musicCompany != null ? !musicCompany.equals(cd.musicCompany) : cd.musicCompany != null) return false;
        if (totalDuration != null ? !totalDuration.equals(cd.totalDuration) : cd.totalDuration != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (totalDuration != null ? totalDuration.hashCode() : 0);
        result = 31 * result + (musicCompany != null ? musicCompany.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(title);
        return sb.toString();
    }
}