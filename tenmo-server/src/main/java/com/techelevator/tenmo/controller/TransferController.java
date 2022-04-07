package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/transfer")

public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, UserDao userDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@Valid @PathVariable int id) {
        return transferDao.getTransferByTransferId(id);
    }

    // for pending bucks
    @RequestMapping(path = "/pending/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllPendingTransfersByUserId(@Valid @PathVariable int id) {
        return transferDao.getPendingTransfersByUserId(id);
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllTransfersByUserId(@Valid @PathVariable int id) {
        return transferDao.getAllTransfersByUserId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void sendBucks(@Valid Principal principal, @RequestBody Transfer transfer) {
        if (transfer.getTransfer_type_id() == 2) {
            transferDao.sendBucks(transfer);
            accountDao.addBalance(transfer);
            accountDao.subtractBalance(transfer);
        }
        if (transfer.getTransfer_type_id() == 1) {
            transferDao.sendBucks(transfer);
        }

    }

    @RequestMapping(path = "/{transferId}", method = RequestMethod.PUT)
    public void updateTransferStatus(@Valid @RequestBody Transfer transfer, @PathVariable int transferId) {
        transferDao.updateTransferStatus(transfer, transferId);
    }


}
