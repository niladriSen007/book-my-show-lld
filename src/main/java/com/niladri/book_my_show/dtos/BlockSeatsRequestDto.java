package com.niladri.book_my_show.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BlockSeatsRequestDto {
    private long showId;
    private long userId;
    private List<Long> seatId;
}
