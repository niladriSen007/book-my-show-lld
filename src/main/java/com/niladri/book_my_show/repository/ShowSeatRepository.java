package com.niladri.book_my_show.repository;

import com.niladri.book_my_show.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    List<ShowSeat> findAllByShowIdAndSeatIdIn(long showId, List<Long> seatIds);
}
