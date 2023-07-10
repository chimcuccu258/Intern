package com.example.be.service;

import com.example.be.model.Bill;
import com.example.be.model.BillDetails;
import com.example.be.model.Book;
import com.example.be.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillService {

    ResponseEntity<Object> getAllBills();

    ResponseEntity<Object> getBillById(Long id);

    List<Book> getBooksByBill(ResponseEntity<Object> bill) throws ChangeSetPersister.NotFoundException;


    List<BillDetails> getBillDetailsByBill(Bill bill);

    Bill getActiveBillByUser(User user);

    void returnBook(Long userId, Long bookId);
}
