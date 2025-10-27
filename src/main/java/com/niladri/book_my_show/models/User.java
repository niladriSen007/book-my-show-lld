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
public class User extends BaseModel {

    private String name;

    private String email;
    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;
}
