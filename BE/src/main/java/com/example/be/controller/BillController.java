package com.example.be.controller;

import ch.qos.logback.core.model.Model;
import com.example.be.model.Bill;

import com.example.be.model.BillDetails;
import com.example.be.model.Book;
import com.example.be.notfound.NotFoundException;
import com.example.be.service.BillService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/getall")
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{billId}")
    public Bill getBillById(@PathVariable("billId") Long billId) throws ChangeSetPersister.NotFoundException {
        return billService.getBillById(billId);
    }

    @GetMapping("/{billId}/details")
    public ResponseEntity<List<BillDetails>> getBillDetailsByBill(@PathVariable("billId") Long billId) {
        try {
            Bill bill = billService.getBillById(billId);
            List<BillDetails> billDetailsList = billService.getBillDetailsByBill(bill);
            return ResponseEntity.ok(billDetailsList);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{billId}/books")
    public ResponseEntity<List<Book>> getBooksByBill(@PathVariable("billId") Long billId) {
        try {
            Bill bill = billService.getBillById(billId);
            List<Book> bookList = billService.getBooksByBill(bill);
            return ResponseEntity.ok(bookList);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{userId}/books/return/{bookId}")
    public ResponseEntity<String> returnBook(
            @PathVariable("userId") Long userId,
            @PathVariable("bookId") Long bookId) {
        try {
            billService.returnBook(userId, bookId);
            return ResponseEntity.ok("Book returned successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}


