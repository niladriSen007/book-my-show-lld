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
public class City extends BaseModel{

    private String name;

    @OneToMany(mappedBy = "city")
    private List<Theater> theaters;
}
