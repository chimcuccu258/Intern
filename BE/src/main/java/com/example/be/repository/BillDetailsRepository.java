package com.example.be.repository;

import com.example.be.model.Bill;
import com.example.be.model.BillDetails;
import com.example.be.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillDetailsRepository extends JpaRepository<BillDetails, Long> {
    List<BillDetails> findByBill(ResponseEntity<Object> bill);

    List<BillDetails> findByBill(Bill bill);

    void deleteByBillAndBook(Bill bill, Book book);
}
