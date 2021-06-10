package de.iitsconsulting.movies.repo;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.iitsconsulting.movies.model.Movie;
import de.iitsconsulting.movies.repo.jpa.MovieMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyMovieRepositoryImplementationImpl {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Movie> findMoviesByTitle(String title) {
        Query query = entityManager.createQuery("SELECT m FROM Movie m where m.title = :title");
        query.setParameter("title", title);
        return query.getResultList();
    }

    public List<Movie> findMovieByYear(int startYear, int endYear) {
        if (startYear > endYear ) {
            return entityManager.createQuery("SELECT m FROM Movie m WHERE m.year >=" + startYear).getResultList();
        }
        return entityManager.createNativeQuery("SELECT * FROM movie", Movie.class).getResultList();
    }

    public <S extends Movie> S save(final S s) {return repository.save(s);}

    public void delete(final Movie movie) {repository.delete(movie);}

    private MovieMovieRepository repository;

    public MyMovieRepositoryImplementationImpl(final MovieMovieRepository repository) {this.repository = repository;}

    public Optional<Movie> findById(long id) { return repository.findById(id);}

    public List<Movie> findAll() { return repository.findAll();}

}
