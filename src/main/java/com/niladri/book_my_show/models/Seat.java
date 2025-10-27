package com.niladri.book_my_show.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat extends BaseModel {

    private String seatNumber;

    private int rowValue; //addition sanket
    private int columnValue; //addition sanket

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;
}
