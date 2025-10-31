package com.niladri.book_my_show.service;

import com.niladri.book_my_show.models.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IBookingService {
    boolean blockSeats(long showId, List<Long> seatIds, Long userId);

    Optional<Ticket> confirmAllBookings(long showId, List<Long> seatIds, long userId);
}
