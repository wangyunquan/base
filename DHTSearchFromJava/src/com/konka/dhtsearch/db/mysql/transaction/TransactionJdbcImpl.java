package com.konka.dhtsearch.db.mysql.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import com.konka.dhtsearch.db.mysql.exception.DhtException;
import com.konka.dhtsearch.db.mysql.jdbc.ConnectionProvider;

public class TransactionJdbcImpl implements Transaction {
    
    private static TransactionJdbcImpl instance = null;
    
    private Connection connection = null;
    
    private TransactionJdbcImpl() {
        
    }
    
    public static TransactionJdbcImpl getInstance() {
        if(TransactionJdbcImpl.instance == null) {
            TransactionJdbcImpl.instance = new TransactionJdbcImpl();
        }
        return TransactionJdbcImpl.instance;
    }
    
    @Override
	public Connection getConnection() throws DhtException {
        try {
            if(this.connection == null || this.connection.isClosed()) {
                this.connection = ConnectionProvider.getInstance().getConnection();
            } 
        } catch(SQLException sqlException) {
            throw new DhtException(sqlException);
        }
        return this.connection;
    }
    
    @Override
    public void begin() throws DhtException {
        try {
            this.getConnection().setAutoCommit(false);
        } catch (SQLException sqlException) {
            throw new DhtException(sqlException);
        }
    }

    @Override
    public void commit() throws DhtException {
        try {
            this.connection.commit();
        } catch (SQLException sqlCommitException) {
            throw new DhtException(sqlCommitException);
        } finally {
            this.close();
        }
    }

    @Override
    public void rollback() throws DhtException {
        try {
            this.connection.rollback();
        } catch (SQLException sqlRollbackException) {
            throw new DhtException(sqlRollbackException);
        } finally {
            this.close();
        }
    }
    
    private void close() throws DhtException {
        try {
            if(this.connection != null && 
               !this.connection.isClosed()) {
                ConnectionProvider.getInstance().closeConnection();
                this.connection = null;
            }
        } catch (SQLException sqlException) {
            throw new DhtException(sqlException);
        }
    }

}
