package de.iitsconsulting.movies.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "director_id_generator")
    @SequenceGenerator(name = "director_id_generator", sequenceName = "director_id_seq", allocationSize = 20)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "director",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Movie> movies;

    @Override
    public String toString() {
        return "Director{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
