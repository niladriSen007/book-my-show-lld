package com.niladri.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditorium extends BaseModel {

    private String name; //addition sanket
    private int capacity; //addition sanket

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @OneToMany(mappedBy = "auditorium")
    private List<Seat> seats;

    @OneToMany(mappedBy = "auditorium")
    private List<Show> shows;

}
