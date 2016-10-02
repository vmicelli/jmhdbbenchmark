/*
Copyright 2016 Vincenzo Micelli

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.

 * Neither the name of Oracle nor the names of its contributors may be used
   to endorse or promote products derived from this software without
   specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.vin.dbbenchmark.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This helper class extends {@link DbHelper} to implement methods to open and close a connection to PostgreSql and create a test table.
 * 
 * @author Vincenzo Micelli
 */
public class PostgreSqlDbHelper extends DbHelper {

    @Override
    public int connect() {
        
        if(connection != null)
            return 0;
        
        try {
            // Create a variable for the connection string.
            String connectionUrl = "jdbc:postgresql://" + serverName + ":" + portNumber + "/" + databaseName;
            
            // Establish the connection.
            Class.forName("org.postgresql.Driver");  
            connection = DriverManager.getConnection(connectionUrl,username,password);
            
            return 0;
 
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PostgreSqlDbHelper.class.getName()).log(Level.SEVERE, null, ex);
            
            return - 1;
        }

    }

    @Override
    public int createTable() {
        int result = 0;
        
        String dropTableSQL = "  DROP TABLE IF EXISTS " + TABLE_NAME;
                
        try (PreparedStatement dropTablePreparedStatement = connection.prepareStatement(dropTableSQL)) {

            connection.setAutoCommit(true);
            // execute drop SQL stetement
            dropTablePreparedStatement.executeUpdate();
            
        } catch (SQLException  ex) {
            Logger.getLogger(PostgreSqlDbHelper.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        
        if(result < 0)
            return result;
        
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_PK_NAME + " SERIAL PRIMARY KEY, "
                    + COLUMN_VARCHAR_NAME + " VARCHAR(20) NOT NULL, "
                    + COLUMN_INT_NAME + " INTEGER NOT NULL, "
                    + COLUMN_DECIMAL_NAME + " DECIMAL(9,2) NOT NULL, "
                    + COLUMN_DATE_NAME + " DATE NOT NULL "
                    + ")";
        
        try (PreparedStatement createTablePreparedStatement = connection.prepareStatement(createTableSQL)) {

            // execute create SQL stetement
            createTablePreparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSqlDbHelper.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        
        return result;
    }

    @Override
    public void closeConnection() {
        try {
            
            if(connection != null)
            {
                connection.close();
                connection = null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSqlDbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
