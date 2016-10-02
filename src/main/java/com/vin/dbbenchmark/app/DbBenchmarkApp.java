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

package com.vin.dbbenchmark.app;

import com.vin.dbbenchmark.benchmarks.DbInsertBatchBenchmark;
import com.vin.dbbenchmark.benchmarks.DbSelectBenchmark;
import com.vin.dbbenchmark.configuration.ConfigurationHelper;
import com.vin.dbbenchmark.database.DBMSName;
import com.vin.dbbenchmark.database.DbHelper;
import com.vin.dbbenchmark.database.DbHelperFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * @author Vincenzo Micelli
 */
public class DbBenchmarkApp {

    public static void main(String[] args) {
        try {
            
            
            ConfigurationHelper helper = new ConfigurationHelper();
            int numOfBatchInsertIterations = helper.getNumberOfBatchInsertIterations();
            int numOfSelectIterations = helper.getNumberOfSelectIterations();
            int numOfWarmupIterations = helper.getNumberOfWarmupIterations();
            DBMSName dbmsName = helper.getDbmsName();

            // if dbms has not been properly specified in the configuration file it is not possible to execute the tests.
            if(dbmsName == null)
                return;

            // get db helper 
            DbHelper dbHelper = DbHelperFactory.getDbHelper(dbmsName);

            if(dbHelper.connect() != 0)
            {
                return;
            }

            //create the table that will be used for the tests. 
            int createTableResult = dbHelper.createTable();
            dbHelper.closeConnection();

            // if there were errors during the creation of the table it is not possible to execute the tests.
            if(createTableResult != 0)
                return;
            
            
            Options optInsertBatch = new OptionsBuilder()
                    .include(DbInsertBatchBenchmark.class.getSimpleName())
                    .warmupIterations(numOfWarmupIterations)
                    .measurementIterations(numOfBatchInsertIterations)
                    .forks(1)
                    .build();
            
             Options optSelectStatements = new OptionsBuilder()
                    .include(DbSelectBenchmark.class.getSimpleName())
                    .warmupIterations(numOfWarmupIterations)
                    .measurementIterations(numOfSelectIterations)
                    .forks(1)
                    .build();

            System.out.print("START INSERT BATCH BENCHMARK\n\n");
             
            new Runner(optInsertBatch).run();
            
            System.out.print("\n\nSTART SELECT STATEMENTS BENCHMARK\n\n");
            
            new Runner(optSelectStatements).run();
            
        } catch (RunnerException ex) {
            Logger.getLogger(DbBenchmarkApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
