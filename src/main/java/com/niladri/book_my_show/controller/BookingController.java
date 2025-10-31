package com.niladri.book_my_show.controller;

import com.niladri.book_my_show.dtos.BlockSeatsRequestDto;
import com.niladri.book_my_show.dtos.BookSeatsRequestDto;
import com.niladri.book_my_show.models.Ticket;
import com.niladri.book_my_show.service.CacheServiceImpl;
import com.niladri.book_my_show.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService bookingService;
    private final CacheServiceImpl cacheService;

    @PostMapping("/block")
    public boolean blockSeats(@RequestBody BlockSeatsRequestDto requestDto) {
        return bookingService.blockSeats(requestDto.getShowId(), requestDto.getSeatId(), requestDto.getUserId());
    }

    @DeleteMapping("/delete")
    public boolean deleteBooking() {
        cacheService.deleteAll();
        return true;
    }

    @PostMapping("/confirm")
    public boolean confirmBooking(@RequestBody BookSeatsRequestDto requestDto) {
        Optional<Ticket> ticket = bookingService.confirmAllBookings(requestDto.getShowId(), requestDto.getSeatId(), requestDto.getUserId());
        return true;
    }
}

