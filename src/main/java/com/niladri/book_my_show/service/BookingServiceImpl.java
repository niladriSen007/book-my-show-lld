package com.niladri.book_my_show.service;

import com.niladri.book_my_show.models.ShowSeat;
import com.niladri.book_my_show.models.ShowSeatStatus;
import com.niladri.book_my_show.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements IBookingService {

    private final ShowSeatRepository showSeatRepository;
    private final CacheServiceImpl cacheService;

    @Override
    public boolean blockSeats(long showId, List<Long> seatIds, Long userId) {

        log.info("Current Cache state before blocking seats:");
        cacheService.getAllKeyAndValues();

        log.info("Blocking seats: {} for showId: {} by userId: {}", seatIds, showId, userId);
        // 1. We will first of all check if the seats are available or not
        // a. Check if the seats are not booked already
        log.info("Checking availability for seats: {} for showId: {}", seatIds, showId);
        List<ShowSeat> seats = showSeatRepository.findAllByShowIdAndSeatIdIn(showId, seatIds);

        seats.forEach(seat->{
            log.info("Seat: {} has status: {}", seat.getSeat().getSeatNumber(), seat.getSeatStatus());
        });

        for (ShowSeat seat : seats) {
            if (seat.getSeatStatus().equals(ShowSeatStatus.BOOKED)) {
                log.info("Seat: {} is already booked for showId: {}", seat.getSeat().getSeatNumber(), showId);
                return false;
            }
        }
        // b. Check if the seats are not locked already
        for (ShowSeat seat : seats) {
            Object value = cacheService.getKey("seatId-" + seat.getId() + "userId-" + userId);
            log.info("Cache value for seatId-{}userId-{} is: {}", seat.getId(), userId, value);
            if (value != null) {
                log.info("Seat: {} is already locked for showId: {} by another user", seat.getSeat().getSeatNumber(), showId);
                return false;
            }
        }

        // 2. If all the seats are available then we will block the seats in redis - seatId - userId
        for (ShowSeat seat : seats) {
            cacheService.setKey("seatId-" + seat.getId() + "-userId-" + userId, ShowSeatStatus.LOCKED);
            log.info("Seat: {} is locked for showId: {} by userId: {}", seat.getSeat().getSeatNumber(), showId, userId);
        }

        log.info("Provided seats are successfully locked for showId: {} by userId: {}", showId, userId);
        cacheService.getAllKeyAndValues();
        return true;
    }
}
