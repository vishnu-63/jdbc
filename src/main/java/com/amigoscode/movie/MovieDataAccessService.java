package com.amigoscode.movie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDataAccessService implements MovieDao {

    private final JdbcTemplate jdbcTemplate;
    public MovieDataAccessService(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public List<Movie> selectMovies() {
        var sql= """ 
                   SELECT id,name,release_date 
                   FROM movie
                   limit 100; 
                    """;
        return jdbcTemplate.query(sql,new MovieRoleMapper());
    }

    @Override
    public int insertMovie(Movie movie) {
        String sql= """ 
                    INSERT INTO movie(name,release_date) 
                    VALUES (?,?);
                    """;
        return jdbcTemplate.update(
                sql,
                movie.name(),movie.releaseDate()
        );

    }

    @Override
    public int deleteMovie(int id) {
        var sql= """ 
                   DELETE FROM movie
                   WHERE id=?; 
                    """;
        return jdbcTemplate.update(sql,id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id)
    {
        var sql= """ 
                   SELECT id,name,release_date 
                   FROM movie
                   WHERE id=?; 
                    """;
        return jdbcTemplate.
                query(sql,new MovieRoleMapper(),id)
                .stream()
                .findFirst();
    }
    
}
