package com.example.be.service.impl;

import com.example.be.model.Bill;
import com.example.be.model.BillDetails;
import com.example.be.model.Book;
import com.example.be.model.User;
import com.example.be.notfound.NotFoundException;
import com.example.be.payload.response.BillResponse;
import com.example.be.payload.response.ListDataResponse;
import com.example.be.repository.BillDetailsRepository;
import com.example.be.repository.BillRepository;
import com.example.be.repository.BookRepository;
import com.example.be.repository.UserRepository;
import com.example.be.service.BillService;
import com.example.be.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    BillDetailsRepository billDetailsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    @Override
    public ResponseEntity<Object> getAllBills() {
        try {
            List<Bill> bills = billRepository.findAll();
            List<BillResponse> billResponses = bills.stream().map(bill -> new BillResponse(
                            bill.getIssue_date(),
                            bill.getExpired_date(),
                            bill.getTotal_price(),
                            bill.getLate_payment_fee(),
                            bill.getIsReturned(),
                            bill.getUser().getId()))
                    .collect(Collectors.toList());
            ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK")
                    .data(billResponses).build();
            return ResponseEntity.ok(listDataResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred from the server with exception = " + e);
        }
    }

    @Override
    public ResponseEntity<Object> getBillById(Long id) {
        try {
            Optional<Bill> billOptional = billRepository.findById(id);
            if (billOptional.isPresent()) {
                Bill bill = billOptional.get();

                BillResponse billResponse = new BillResponse();
                billResponse.setIssue_date(bill.getIssue_date());
                billResponse.setExpired_date(bill.getExpired_date());
                billResponse.setTotal_price(bill.getTotal_price());
                billResponse.setLate_payment_fee(bill.getLate_payment_fee());
                billResponse.setIsReturned(bill.getIsReturned());
                billResponse.setUserId(bill.getUser().getId());

                ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(billResponse).build();
                return ResponseEntity.ok(listDataResponse);
            } else {
                ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("Bill not found!").build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred from server with exception = " + e);
        }
    }

    @Override
    public List<Book> getBooksByBill(ResponseEntity<Object> bill) throws ChangeSetPersister.NotFoundException {
        List<BillDetails> billDetailsList = billDetailsRepository.findByBill(bill);
        return billDetailsList.stream().map(BillDetails::getBook).collect(Collectors.toList());
    }

    @Override
    public List<BillDetails> getBillDetailsByBill(Bill bill) {
        // Lấy danh sách BillDetails dựa trên Bill
        return billDetailsRepository.findByBill(bill);
    }

    public Bill getActiveBillByUser(User user) {
        return billRepository.findActiveBillByUser(user);
    }

    @Override
    public void returnBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));

        Bill activeBill = getActiveBillByUser(user);
        if (activeBill == null) {
            throw new NotFoundException("No active borrow bill found for the user");
        }
        Bill bill = billRepository.getActiveBillByUser(user);
        List<BillDetails> billDetailsList;

        BillDetails billDetailsToRemove;
        if (bill != null) {
            // Lấy danh sách chi tiết sách từ hóa đơn
            billDetailsList = billRepository.getBillDetailsByBill(bill);
            // Kiểm tra chi tiết sách trong hóa đơn
            billDetailsToRemove = null;
            for (BillDetails billDetails : billDetailsList) {
                if (billDetails.getBook().getId().equals(book.getId())) {
                    billDetailsToRemove = billDetails;
                    break;
                }
            }
        } else {
            throw new NotFoundException("bill not found");
        }

        if (billDetailsToRemove == null) {
            throw new NotFoundException("Book is not borrowed by the user");
        }
        // Xóa chi tiết sách khỏi hóa đơn
        removeBillDetails(billDetailsToRemove);
        // Tăng giá trị tồn kho sách
        int newInventory = book.getInventory() + 1;
        book.setInventory(newInventory);
        bookRepository.save(book);
        // Cập nhật tổng giá trị hóa đơn
        Long bookPrice = book.getPrice();
        int totalBorrowedBooks = billDetailsList.size() - 1;
        Long totalPrice = new BigDecimal(bookPrice).multiply(new BigDecimal(0.2)).multiply(new BigDecimal(totalBorrowedBooks)).longValue();
        activeBill.setTotal_price(totalPrice);
        saveBill(activeBill);
    }

    private void saveBill(Bill activeBill) {
    }

    private void removeBillDetails(BillDetails billDetailsToRemove) {
    }
}



