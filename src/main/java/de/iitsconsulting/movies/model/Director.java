package de.iitsconsulting.movies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "director")
@AllArgsConstructor
@NoArgsConstructor
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "director_id_generator")
    @SequenceGenerator(name = "director_id_generator", sequenceName = "director_id_seq", allocationSize = 20)
    @Getter
    private Long id;

    @Getter
    @Setter
    public String firstName;

    @Getter
    @Setter
    public String lastName;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "director",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<Movie> movies;

    @Override
    public String toString() {
        return "Director{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
