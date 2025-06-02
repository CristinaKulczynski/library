package com.example.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserEmail(String userEmail);

    Optional<Reservation> findByBookCodeAndUserEmail(String bookCode, String userEmail);

    void deleteByBookCodeAndUserEmail(String bookCode, String userEmail);
}
