package com.example.be.service;
import java.util.stream.Collectors;
import com.example.be.model.Bill;
import com.example.be.model.BillDetails;
import com.example.be.model.Book;
import com.example.be.model.User;
import com.example.be.repository.BillDetailsRepository;
import com.example.be.repository.BillRepository;
import com.example.be.repository.BookRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface BillService {


    Bill getBillById(Long billId)throws ChangeSetPersister.NotFoundException;

    List<Bill> getBillsByUser(User user) ;

    List<Book> getBooksByBill(Bill bill) ;

    Bill getActiveBillByUser(User user);
    List<BillDetails> getBillDetailsByBill(Bill bill);

    Bill getActiveBillByUserAndBook(User user, Book book);

    void saveBill(Bill bill);


    void saveBillDetails(BillDetails billDetails);



    void deleteBillDetailsByBillAndBook(Bill bill, Book book);
    List<Bill> getActiveBillsByUser(User user);
    public void saveAllBills(List<Bill> bills) ;
    public List<Bill> getAllBills();

    void returnBook(Long userId, Long bookId);
}
