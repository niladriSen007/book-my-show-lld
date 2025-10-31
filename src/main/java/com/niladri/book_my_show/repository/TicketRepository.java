package com.niladri.book_my_show.repository;

import com.niladri.book_my_show.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
