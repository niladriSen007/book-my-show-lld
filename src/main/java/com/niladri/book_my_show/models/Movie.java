package com.niladri.book_my_show.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseModel {

    private String name;

    @OneToMany(mappedBy = "movie")
    private List<Show> shows;
}
