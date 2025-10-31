package com.niladri.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private LocalDateTime startTime; //addition sanket
    private LocalDateTime endTime; //addition sanket

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;

    @OneToMany(mappedBy = "show")
    private List<ShowSeat> showSeats; //addition sanket
}
