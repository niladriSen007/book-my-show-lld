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
public class Theater extends BaseModel {
    private String name;
    private String address; //addition sanket

    @ManyToOne
    @JoinColumn(name = "theaters")
    private City city;

    @OneToMany(mappedBy = "theater")
    private List<Auditorium> auditoriums;
}
