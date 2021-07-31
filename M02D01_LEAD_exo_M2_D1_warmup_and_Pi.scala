// Databricks notebook source
// TODO: load the file located in the project /data/Airplane_Crashes_and_Fatalities_Since_1908.csv and show the dataset

// TODO: count the number of airplanes that crashed

// TODO: count the number of crashes, and the number of Fatalities by operator

// TODO: filter the dataset to only have military planes

// TODO: Convert the date and the time into a timestamp object
// Hint, convert a string to a timestamp
// import org.apache.spark.sql.functions.to_timestamp
// to_timestamp(someTimeString, "dd-MM-yyyy HH:mm")

// TODO: Fin the hour of the day with the most crashes
// Tips
// to get the hour out of a date column: hour(col("Datetime"))
// - you can drop columns containing NA: df.na.drop()


// COMMAND ----------

display(dbutils.fs.ls("/FileStore/tables"))

// COMMAND ----------

//val fp = "/FileStore/tables/Airplane_Crashes_and_Fatalities_Since_1908.csv"

val airplaneCrashed = spark.read.option("header","true").csv("/FileStore/tables/Airplane_Crashes_and_Fatalities_Since_1908.csv")
airplaneCrashed.show

// COMMAND ----------

// TODO: count the number of airplanes that crashed 
airplaneCrashed.count

// COMMAND ----------

// TODO: count the number of crashes, and the number of Fatalities by operator

import org.apache.spark.sql.functions._

airplaneCrashed.groupBy(col("Type")).agg(
  count("Fatalities").as("Total Crashes"), 
  avg("Fatalities").as("Moy Fatalities")).show





// COMMAND ----------

import org.apache.spark.sql.functions.{col, explode, udf}

// TODO: filter the dataset to only have military planes
display(
  airplaneCrashed.filter(col("Operator").contains("Military"))
  )



// COMMAND ----------

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import org.apache.spark.sql.functions.to_timestamp

val airplaneCrashedWithDateTime = airplaneCrashed.withColumn("Datetime", to_timestamp(concat($"Date", lit(" "), $"Time"), "MM/dd/yyyy H:mm" ))
airplaneCrashedWithDateTime.show

// COMMAND ----------

// Fin the hour of the day with the most crashes
// Tips
// to get the hour out of a date column: hour(col("Datetime"))
// - you can drop columns containing NA: df.na.drop()
airplaneCrashedWithDateTime.withColumn("Hour of Day", hour(col("Datetime"))).groupBy(col("Hour of Day")).count().na.drop().orderBy(desc("count")).show()

// COMMAND ----------

//

// COMMAND ----------

//

// COMMAND ----------

// Use RDDs to estimate the value of PI
// You should use the monte-carlo method. 
// We pick random points in the unit square ((0, 0) to (1,1)) 
// and see how many fall in the unit circle. 
// The fraction should be π / 4, so we use this to get our estimate.
val NUM_SAMPLES = 1000000

val count = sc.parallelize(1 to NUM_SAMPLES).filter { _ =>
  val x = math.random
  val y = math.random
  x*x + y*y < 1
}.count()

println(s"Pi is roughly ${4.0 * count / NUM_SAMPLES}")


// COMMAND ----------


