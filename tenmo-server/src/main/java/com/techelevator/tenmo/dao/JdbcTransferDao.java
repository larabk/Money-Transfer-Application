package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> getTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            getTransfers.add(mapRowToTransfer(result));
        }
        return getTransfers;
    }

    @Override
    public List<Transfer> getAllTransfersByUserId(int userId) {
        List<Transfer> getTransferByID = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount FROM transfer JOIN account ON account.account_id = transfer.account_from " +
                "JOIN account a ON a.account_id = transfer.account_to WHERE a.user_id = ? OR account.user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (result.next()) {
            getTransferByID.add(mapRowToTransfer(result));
        }
        return getTransferByID;
    }

    // for request bucks
    @Override
    public List<Transfer> getPendingTransfersByUserId(int userId) {
        List<Transfer> getPendingTransfer = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, " +
                "account_to, amount FROM transfer JOIN account ON account.account_id = transfer.account_from " +
                "JOIN account a ON a.account_id = transfer.account_to " +
                "WHERE (a.user_id = ? OR account.user_id = ?) AND transfer_status_id = 1;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (result.next()) {
            getPendingTransfer.add(mapRowToTransfer(result));
        }
        return getPendingTransfer;
    }


    @Override
    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount FROM transfer WHERE transfer_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    @Override
    public Transfer sendBucks(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "values(?, ?, ?, ?, ?) RETURNING transfer_id;";
        int newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransfer_type_id(),
                    transfer.getTransfer_status_id(), transfer.getAccount_from(), transfer.getAccount_to(),
                    transfer.getAmount());
        return getTransferByTransferId(newId);
    }

    // update transfer status ID where transfer ID = ?
    @Override
    public void updateTransferStatus(Transfer transfer, int transferId) {
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?;";
        jdbcTemplate.update(sql, transfer.getTransfer_status_id(), transferId);
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rowSet.getInt("transfer_id"));
        transfer.setTransfer_type_id(rowSet.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));
        transfer.setAccount_from(rowSet.getInt("account_from"));
        transfer.setAccount_to(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
