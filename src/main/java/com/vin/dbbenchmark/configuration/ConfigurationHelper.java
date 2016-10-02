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
package com.vin.dbbenchmark.configuration;

import com.vin.dbbenchmark.database.DBMSName;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincenzo Micelli
 */
public class ConfigurationHelper {
    
    private static final int DEFAULT_NUM_OF_BATCH_INSERT_ITERATIONS = 10;
    private static final int DEFAULT_NUM_OF_INSERT_PER_TRANSACTION = 10;
    private static final int DEFAULT_NUM_OF_SELECT_ITERATIONS = 10;
    private static final int DEFAULT_NUM_OF_WARMUP_ITERATIONS = 5;
    
    private static final String CONFIGURATION_FILE = "configuration.properties";
    
    private Properties properties = new Properties();
    
    public ConfigurationHelper()
    {
        String resourceName = CONFIGURATION_FILE;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        properties = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
        } catch (IOException ex) {
            
            String error = "Failed to load configuration properties.";
            Logger.getLogger(ConfigurationHelper.class.getName()).log(Level.SEVERE, error, ex);
        }

    }
    
    /**
     *
     * @return The num of batch insert iterations from the configuration file. 
     * If the property has not been set or has been set to 0, return default value.
     */
    public int getNumberOfBatchInsertIterations()
    {
        if(properties == null)
            return DEFAULT_NUM_OF_BATCH_INSERT_ITERATIONS;
            
        int numOfExecutions;
        
        String numOfExecutionsProp = properties.getProperty(ConfigurationProperties.NUM_OF_BATCH_INSERT_ITERATIONS);

        try {
            numOfExecutions = Integer.parseInt(numOfExecutionsProp);
        } catch (NumberFormatException numberFormatException) {
            numOfExecutions = 0;
        }
        
        if(numOfExecutions <= 0)
        {
            String warning = "Invalid input for property " + ConfigurationProperties.NUM_OF_BATCH_INSERT_ITERATIONS + ".\n" +
                             "Input value is not a positive number: " + numOfExecutionsProp + ".\n" +
                             "Using default value " + DEFAULT_NUM_OF_BATCH_INSERT_ITERATIONS + "\n\n";
            
            Logger.getLogger(ConfigurationHelper.class.getName()).log(Level.WARNING, warning);
            
            numOfExecutions = DEFAULT_NUM_OF_BATCH_INSERT_ITERATIONS;
        }
        
        return numOfExecutions;
    }
    
    /**
     *
     * @return The num of inserts per transaction from the configuration file. 
     * If the property has not been set or has been set to 0, return default value.
     */
    public int getNumberOfInsertsPerTransaction()
    {
        if(properties == null)
            return DEFAULT_NUM_OF_INSERT_PER_TRANSACTION;
        
        int numOfExecutions;
            
        String numOfExecutionsProp = properties.getProperty(ConfigurationProperties.NUM_OF_INSERTS_PER_TRANSACTION);

        try {
            numOfExecutions = Integer.parseInt(numOfExecutionsProp);
        } catch (NumberFormatException numberFormatException) {
            numOfExecutions = 0;
        }
        
        if(numOfExecutions <= 0)
        {
            String warning = "Invalid input for property " + ConfigurationProperties.NUM_OF_INSERTS_PER_TRANSACTION + ".\n" +
                             "Input value is not a positive number: " + numOfExecutionsProp + ".\n" +
                             "Using default value " + DEFAULT_NUM_OF_INSERT_PER_TRANSACTION + "\n\n";
            
            Logger.getLogger(ConfigurationHelper.class.getName()).log(Level.WARNING, warning);
            
            numOfExecutions = DEFAULT_NUM_OF_INSERT_PER_TRANSACTION;
        }
        
        return numOfExecutions;
    }
    
    public int getNumberOfSelectIterations()
    {
        if(properties == null)
            return DEFAULT_NUM_OF_SELECT_ITERATIONS;
            
        int numOfExecutions;
        
        String numOfExecutionsProp = properties.getProperty(ConfigurationProperties.NUM_OF_SELECT_ITERATIONS);

        try {
            numOfExecutions = Integer.parseInt(numOfExecutionsProp);
        } catch (NumberFormatException numberFormatException) {
            numOfExecutions = 0;
        }
        
        if(numOfExecutions <= 0)
        {
            String warning = "Invalid input for property " + ConfigurationProperties.NUM_OF_SELECT_ITERATIONS + ".\n" +
                             "Input value is not a positive number: " + numOfExecutionsProp + ".\n" +
                             "Using default value " + DEFAULT_NUM_OF_SELECT_ITERATIONS + "\n\n";
            
            Logger.getLogger(ConfigurationHelper.class.getName()).log(Level.WARNING, warning);
            
            numOfExecutions = DEFAULT_NUM_OF_SELECT_ITERATIONS;
        }
        
        return numOfExecutions;
    }
    
    public int getNumberOfWarmupIterations()
    {
        if(properties == null)
            return DEFAULT_NUM_OF_WARMUP_ITERATIONS;
        
        int numOfExecutions;
        
        String numOfExecutionsProp = properties.getProperty(ConfigurationProperties.NUM_OF_WARMUP_ITERATIONS);

        try {
            numOfExecutions = Integer.parseInt(numOfExecutionsProp);
        } catch (NumberFormatException numberFormatException) {
            numOfExecutions = -1;
        }
        
        if(numOfExecutions < 0)
        {
            String warning = "Invalid input for property " + ConfigurationProperties.NUM_OF_WARMUP_ITERATIONS + ".\n" +
                             "Input value is not a number: " + numOfExecutionsProp + ".\n" +
                             "Using default value " + DEFAULT_NUM_OF_WARMUP_ITERATIONS + "\n\n";
            
            Logger.getLogger(ConfigurationHelper.class.getName()).log(Level.WARNING, warning);
            
            numOfExecutions = DEFAULT_NUM_OF_WARMUP_ITERATIONS;
        }
        
        return numOfExecutions;
    }
    
    public DBMSName getDbmsName()
    {
        if(properties == null)
            return null;
        
        String dbmsNameProp = properties.getProperty(ConfigurationProperties.DBMS_NAME,"");
        
        DBMSName dbmsName = null;
        try {
            dbmsName = DBMSName.valueOf(dbmsNameProp);
        } catch (Exception e) {
            String error = "Invalid input for property " + ConfigurationProperties.DBMS_NAME + ": " + dbmsNameProp + ".\n\n";
            Logger.getLogger(ConfigurationHelper.class.getName()).log(Level.SEVERE, error);
        }
        
        return dbmsName;
    }
    
    public String getDbServerName()
    {
        if(properties == null)
            return null;
        
        String prop = properties.getProperty(ConfigurationProperties.DB_SERVER_NAME,"");

        return prop;
    }
    
    public String getDbServerPortNumber()
    {
        if(properties == null)
            return null;
        
        String prop = properties.getProperty(ConfigurationProperties.DB_SERVER_PORT,"");

        return prop;
    }
    
    public String getDatabaseName()
    {
        if(properties == null)
            return null;
        
        String prop = properties.getProperty(ConfigurationProperties.DATABASE_NAME,"");

        return prop;
    }
    
    public String getUsername()
    {
        if(properties == null)
            return null;
        
        String prop = properties.getProperty(ConfigurationProperties.USERNAME,"");

        return prop;
    }
    
    public String getPassword()
    {
        if(properties == null)
            return null;
        
        String prop = properties.getProperty(ConfigurationProperties.PASSWORD,"");

        return prop;
    }
    
}
