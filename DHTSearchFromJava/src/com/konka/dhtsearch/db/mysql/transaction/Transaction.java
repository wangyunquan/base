package com.konka.dhtsearch.db.mysql.transaction;

import java.sql.Connection;

import com.konka.dhtsearch.db.mysql.exception.DhtException;

public interface Transaction {
    
    public Connection getConnection() throws DhtException;
    
    public void begin() throws DhtException;

    public void commit() throws DhtException;

    public void rollback() throws DhtException;
    
}
