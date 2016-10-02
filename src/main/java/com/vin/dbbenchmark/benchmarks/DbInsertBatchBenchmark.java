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
package com.vin.dbbenchmark.benchmarks;

import com.vin.dbbenchmark.configuration.ConfigurationHelper;
import com.vin.dbbenchmark.database.DbHelper;
import com.vin.dbbenchmark.utils.CommonUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;



/**
 *
 * @author Vincenzo Micelli
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DbInsertBatchBenchmark{
    
    /**
     * This is the function we are Benchmarking.
     * 
     * @param state State object shared between benchmark test invocations. 
     * @throws SQLException Throws SQLException.
     */
    @Benchmark
    public void execTest(InsertBatchState state) throws SQLException
    {
        //we evaluate the time needed to execute the batch and to commit (using the PreparedStatement API)
        state.getDbHelper().execInsertDataBatch();
    }

    @State(Scope.Benchmark)
    public static class InsertBatchState extends DbBenchmarkState
    {
        private int numInsertsPerTransaction;
        
        /**
         * Setup resources at the beginning of the benchmark.
         */
        @Setup(Level.Trial)
        @Override
        public void setup()
        {
            super.setup();

            ConfigurationHelper helper = new ConfigurationHelper();
            numInsertsPerTransaction = helper.getNumberOfInsertsPerTransaction();

            getDbHelper().prepareInsertStatement();

        }

        /**
         * Release resources at the end of the benchmark.
         */
        @TearDown(Level.Trial)
        @Override
        public void teardown() 
        {
            getDbHelper().closeInsertStatement();
            
            super.teardown();
        }

        /**
         * Setup resources needed at the next test invocation.
         */
        @Setup(Level.Invocation)
        public void invocationSetup()
        {
            List<DbHelper.DbEntry> entries = new ArrayList<>();

            //init data for insert batch
            for(int i = 0; i < numInsertsPerTransaction; i++ )
            {
                DbHelper.DbEntry entry = new DbHelper.DbEntry();
                entry.setVarcharField(CommonUtils.getRandomString(20));
                entry.setIntField(CommonUtils.getRandomInt(100000));
                entry.setDecimalField(CommonUtils.getRandomBigDecimal(100000, 2));
                entry.setDateField(CommonUtils.getCurrentTimeStamp());

                entries.add(entry);
            }

            getDbHelper().setInsertDataBatch(entries);

        }
        
        /**
         * Teardown resources used during the last test invocation (if needed).
         * 
         */
        @Setup(Level.Invocation)
        public void invocationTeardown()
        {
            //nothing to do in the benchmark
        }
    
    }
    
}
