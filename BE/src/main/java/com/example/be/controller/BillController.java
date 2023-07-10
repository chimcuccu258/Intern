package com.example.be.controller;

import com.example.be.model.Bill;
import com.example.be.model.Book;
import com.example.be.notfound.NotFoundException;
import com.example.be.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backend/bill")
public class BillController {
    @Autowired
    BillService billService;
    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllBills(){
        return billService.getAllBills();
    }
    @GetMapping("/getbill/{id}")
    public ResponseEntity<Object> getBillById(@PathVariable("id") Long id){
        return billService.getBillById(id);
    }
    @GetMapping("/{billId}/books")
    public ResponseEntity<List<Book>> getBooksByBill(@PathVariable("billId") Long billId) {
        try {
            ResponseEntity<Object> bill = billService.getBillById(billId);
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
