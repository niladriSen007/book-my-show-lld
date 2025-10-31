package com.niladri.book_my_show.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeat extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    // ex - Avengers - Seat 1A, Chava - Seat 1A, Kantara - Seat 1A

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
    //ex - Avengers 7PM, Chava 9PM, Kantara 6PM

    @Enumerated(EnumType.ORDINAL)
    private ShowSeatStatus seatStatus;

    @ManyToOne // because one ticket can book multiple show seats
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
