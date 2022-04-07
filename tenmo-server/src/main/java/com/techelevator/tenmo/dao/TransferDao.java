package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfersByUserId(int userId);
    List<Transfer> getAllTransfers();
    Transfer getTransferByTransferId(int transferId);
    List<Transfer> getPendingTransfersByUserId(int userId);
    Transfer sendBucks(Transfer transfer);
    void updateTransferStatus(Transfer transfer, int transferId);


}
