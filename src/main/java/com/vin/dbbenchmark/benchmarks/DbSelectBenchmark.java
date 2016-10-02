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

import com.vin.dbbenchmark.utils.CommonUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

/**
 *
 * @author Vincenzo Micelli
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DbSelectBenchmark {
    
    /**
     *  This is the function we are Benchmarking.
     * 
     * @param state State object shared between benchmark test invocations. 
     */
    @Benchmark
    public void execTest(SelectState state) {

        //we evaluate the time needed to exec the select statement
        ResultSet resultSet = state.getDbHelper().execSelectData();
        state.setResultSet(resultSet);
    }
    
    
    @State(Scope.Benchmark)
    public static class SelectState extends DbBenchmarkState
    { 
        private int maxPrimaryKeyValue;
        private ResultSet resultSet;

        public ResultSet getResultSet() {
            return resultSet;
        }

        public void setResultSet(ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        /**
         * Setup resources at the beginning of the benchmark.
         */
        @Setup(Level.Trial)
        @Override
        public void setup()
        {
            super.setup();
            
            maxPrimaryKeyValue = getDbHelper().getMaxPrimaryKeyValue();
            getDbHelper().prepareSelectStatement();

        }

        /**
         * Release resources at the end of the benchmark.
         */
        @TearDown(Level.Trial)
        @Override
        public void teardown() 
        {
            getDbHelper().closeSelectStatement();
            super.teardown();
        }
        
        /**
         * Setup resources needed at the next test invocation.
         */
        @Setup(Level.Invocation)
        public void invocationSetup()
        {
            int primaryKeyValue = 0;
        
            // here we assume that primary key values currently in the database are all the values from 1 to maxPrimaryKeyValue 
            // (they have been inserted during the insert test)
            if(maxPrimaryKeyValue > 0)
               primaryKeyValue = CommonUtils.getRandomInt(maxPrimaryKeyValue);

            getDbHelper().setSelectDataPK(primaryKeyValue);
        }

        /**
         * Teardown resources used during the last test invocation.
         * 
         * @throws SQLException Throws SQLException.
         */
        @TearDown(Level.Invocation)
        public void invocationTeardown() throws SQLException
        {
            // close the result set
            if(resultSet != null)
                resultSet.close();
        }
    }
    
}
