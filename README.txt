This simple java application executes benchmarks on a PostgreSQL or MS SQLServer 
database using JMH (http://openjdk.java.net/projects/code-tools/jmh/). 
The application uses the PreparedStatement API to execute prepared statements.
The application includes a configuration.properties file (in folder 
src/main/resources) that have to be used to setup the data for db connection and 
the number of iterations to execute.


INSERT STATEMENTS

The application executes insert statements in batches of X statements. The 
commit operation is performed every X statements. The X value can be configured
in the configuration.properties file using the property 
com.vin.dbbenchmark.benchmark.DbInsertBatchBenchmark.numOfInsertStatementsPerTransaction.

The application performs N batch iterations and provide statistics 
(min, max, avg time). 
The N value can be configured in the configuration.properties file using the 
property com.vin.dbbenchmark.app.DbBenchmarkApp.numOfBatchInsertIterations.


SELECT STATEMENTS

After the insert phase, the application execute M select statement iterations 
and provide statistics (min, max, avg time). 
The M value can be configured in the configuration.properties file using the 
property com.vin.dbbenchmark.app.DbBenchmarkApp.numOfSelectIterations.


WARMUP 

Before the actual executions, the application performs a number Z of warmup 
iterations. Warmup iterations are identical to the test that is going to be 
performed, but the times collected are not considered in the actual statistics.
The Z value can be configured in the configuration.properties file using the 
property com.vin.dbbenchmark.app.DbBenchmarkApp.numOfWarmupIterations.
The warmup is performed before the insert test executing warmup insert 
statements and then before the select test executing warmup select statements.


PROJECT DESCRIPTION

The main class of the project is the class DbBenchmarkApp which runs two 
JMH Benchmark tests.

The two benchmarks performed are implemented in the classes DbInsertBatchBenchmark
and DbSelectBenchmark.

This classes use implementations of the abstract class DbHelper 
(PostgreSqlDbHelper and SqlServerDbHelper) to connect to db and execute prepared
statements using the PreparedStatement API.

The configuration of the application is read from the file 
configuration.properties (which resides in src/main/resources folder) 
by the object ConfigurationHelper.


COMPILE AND RUN

From a console go to the main folder of the project.
Unfortunately Microsoft does not provide the jdbc driver via the maven repository
so exec the following command to install it in the local repository:

mvn install:install-file -Dfile=lib/sqljdbc4-4.2.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.2 -Dpackaging=jar

Then open the file configuration.properties (in folder src/main/resources) to 
setup the data for db connection and the number of iterations to execute.

Then exec the command:

mvn package

Then exec the command:

java -cp target/benchmarks.jar;lib/* com.vin.dbbenchmark.app.DbBenchmarkApp

(folder lib contains postgresql and sql server jdbc drivers)

Alternatively open the project using NetBeans.

