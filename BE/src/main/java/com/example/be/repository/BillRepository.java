package com.example.be.repository;

import com.example.be.model.Bill;
import com.example.be.model.BillDetails;
import com.example.be.model.Book;
import com.example.be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findActiveBillByUser(User user);

    Bill getActiveBillByUser(User user);
    @Query("SELECT bd.bill FROM BillDetails bd WHERE bd.bill.user = :user AND bd.book = :book")
    Bill findActiveBillByUserAndBook(@Param("user") User user, @Param("book") Book book);


    List<BillDetails> getBillDetailsByBill(Bill bill);

    List<Bill> findByUser(User user);

    List<Bill> findActiveBillsByUser(User user);
}
