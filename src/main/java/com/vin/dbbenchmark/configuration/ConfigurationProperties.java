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

/**
 *
 * @author Vincenzo Micelli
 */
public class ConfigurationProperties {
    public final static String NUM_OF_BATCH_INSERT_ITERATIONS = "com.vin.dbbenchmark.app.DbBenchmarkApp.numOfBatchInsertIterations"; 
    public final static String NUM_OF_INSERTS_PER_TRANSACTION= "com.vin.dbbenchmark.benchmark.DbInsertBatchBenchmark.numOfInsertStatementsPerTransaction"; 
    public final static String NUM_OF_SELECT_ITERATIONS = "com.vin.dbbenchmark.app.DbBenchmarkApp.numOfSelectIterations"; 
    public final static String NUM_OF_WARMUP_ITERATIONS = "com.vin.dbbenchmark.app.DbBenchmarkApp.numOfWarmupIterations"; 
    public final static String DBMS_NAME = "dbmsName";
    
    public final static String DB_SERVER_NAME = "com.vin.dbbenchmark.database.DbHelper.serverName";
    public final static String DB_SERVER_PORT = "com.vin.dbbenchmark.database.DbHelper.portNumber";
    public final static String DATABASE_NAME = "com.vin.dbbenchmark.database.DbHelper.databaseName";
    public final static String USERNAME = "com.vin.dbbenchmark.database.DbHelper.username";
    public final static String PASSWORD = "com.vin.dbbenchmark.database.DbHelper.password";
}
