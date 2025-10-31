package com.niladri.book_my_show.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookingService {
    boolean blockSeats(long showId, List<Long> seatIds, Long userId);
}
