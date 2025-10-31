package com.niladri.book_my_show.service;

import com.niladri.book_my_show.models.*;
import com.niladri.book_my_show.repository.ShowRepository;
import com.niladri.book_my_show.repository.ShowSeatRepository;
import com.niladri.book_my_show.repository.TicketRepository;
import com.niladri.book_my_show.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements IBookingService {

    private final ShowSeatRepository showSeatRepository;
    private final CacheServiceImpl cacheService;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;

    @Override
    public boolean blockSeats(long showId, List<Long> seatIds, Long userId) {


        log.info("Blocking seats: {} for showId: {} by userId: {}", seatIds, showId, userId);
        // 1. We will first of all check if the seats are available or not
        // a. Check if the seats are not booked already
        log.info("Checking availability for seats: {} for showId: {}", seatIds, showId);
        List<ShowSeat> seats = showSeatRepository.findAllByShowIdAndSeatIdIn(showId, seatIds);

        seats.forEach(seat -> {
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
            Object value = cacheService.getKey("seatId-" + seat.getId() + "-showId-" + showId);
            log.info("Cache value for seatId-{}-showId-{} is: {}", seat.getId(), showId, value);
            if (value != null) {
                log.info("Seat: {} is already locked for showId: {} by another user", seat.getSeat().getSeatNumber(), showId);
                return false;
            }
        }

        // 2. If all the seats are available then we will block the seats in redis - seatId - userId
        for (ShowSeat seat : seats) {
            cacheService.setKey("seatId-" + seat.getSeat().getId() + "-showId-" + showId, ShowSeatStatus.LOCKED);
            log.info("Seat: {} is locked for showId: {} by userId: {}", seat.getSeat().getSeatNumber(), showId, userId);
        }

        log.info("Provided seats are successfully locked for showId: {} by userId: {}", showId, userId);
        cacheService.getAllKeyAndValues();
        return true;
    }

    @Override
    public Optional<Ticket> confirmAllBookings(long showId, List<Long> seatIds, long userId) {
        for (Long seatId : seatIds) {
            Object status = cacheService.getKey("seatId-" + seatId + "-showId-" + showId);
            log.info("Cache value before booking confirmation for seatId-{}-showId-{} is: {}", seatId, showId, status);
            if (status == null) {
                log.info("SeatId-{} for showId-{} is not locked, cannot confirm booking", seatId, showId);
                return Optional.empty();
            }
        }

        log.info("All seats are locked, proceeding to confirm booking for showId: {} by userId: {}", showId, userId);

        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            log.info("User with userId: {} not found, cannot confirm booking", userId);
            return Optional.empty();
        }
        User user = byId.get();
        Show show = showRepository.findById(showId).get();

        log.info("Creating ticket and booking seats: {} for showId: {} by userId: {}", seatIds, showId, userId);
        Ticket ticket = createTicketAndBookSeat(user, show, seatIds);

        log.info("Booking confirmed, created ticket with id: {} for showId: {} by userId: {}", ticket.getId(), showId, userId);
        // 3. Remove the keys from redis as the booking is confirmed
        for (Long seatId : seatIds) {
            cacheService.deleteKey("seatId-" + seatId + "-showId-" + showId);
            log.info("Removed lock for seatId-{}-showId-{} from cache", seatId, showId);
        }
        return Optional.of(ticket);

    }


    @Transactional(isolation = Isolation.SERIALIZABLE )
    public Ticket createTicketAndBookSeat(User user, Show show, List<Long> seatIds) {
        Ticket ticket = Ticket.builder().user(user).show(show).ticketStatus(TicketStatus.BOOKED).build();
        Ticket bookedTicket = ticketRepository.save(ticket);
        log.info("Created ticket with id: {} for userId: {} and showId: {}", bookedTicket.getId(), user.getId(), show.getId());
        log.info("Booking seats: {} for ticketId: {}", seatIds, bookedTicket.getId().longValue());
        showSeatRepository.bookShowSeats(seatIds, bookedTicket.getId().longValue());
        return bookedTicket;
    }
}
