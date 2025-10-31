package com.niladri.book_my_show.service;

import com.niladri.book_my_show.models.Movie;
import com.niladri.book_my_show.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }
}
