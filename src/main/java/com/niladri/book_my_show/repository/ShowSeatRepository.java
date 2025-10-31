package com.niladri.book_my_show.repository;

import com.niladri.book_my_show.models.ShowSeat;
import com.niladri.book_my_show.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findAllByShowIdAndSeatIdIn(long showId, List<Long> seatIds);

    @Modifying
    @Transactional
    @Query(value = "UPDATE show_seat SET ticket_id=:ticketId, seat_status=1 WHERE id IN :ids",nativeQuery = true)
    void bookShowSeats(@Param("ids") List<Long> seatIds, @Param("ticketId") Long ticketId);
}
