package com.niladri.book_my_show.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends BaseModel {

    private int amount; // addition sanket

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show; //addition sanket

    @OneToMany(mappedBy = "ticket")
    private List<ShowSeat> bookedSeats; // addition sanket

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private TicketStatus ticketStatus;
}
