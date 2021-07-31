// Databricks notebook source
import com.amazonaws.services.kinesis.model.PutRecordRequest
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import java.nio.ByteBuffer
import scala.util.Random

val awsAccessKeyId = ""
val awsSecretKey = ""
val kinesisStreamName = ""
val kinesisRegion = ""


// COMMAND ----------

val kinesis = spark.readStream
  .format("kinesis")
  .option("streamName", kinesisStreamName)
  .option("region", kinesisRegion)
  .option("initialPosition", "TRIM_HORIZON") // we start reading from from the oldest record
  .option("awsAccessKey", awsAccessKeyId)
  .option("awsSecretKey", awsSecretKey)
  .load()

val result = kinesis.selectExpr("lcase(CAST(data as STRING)) as word")
  .groupBy($"word")
  .count()

// COMMAND ----------

display(result)

// COMMAND ----------

val query = result.writeStream
    .outputMode("complete")
    .format("console")
    .start()

// COMMAND ----------

val awsAccessKeyId = ""
val awsSecretKey = ""

sc.hadoopConfiguration.set("fs.s3a.access.key", awsAccessKeyId)
sc.hadoopConfiguration.set("fs.s3a.secret.key", awsSecretKey)

val s3url = "s3://jedha-lead-databricks/kinesisoutput/"

val result = kinesis.selectExpr("lcase(CAST(data as STRING)) as word")

val query = result.writeStream
    .outputMode("append")
    .format("parquet")
    .option("path", s3url)
    .option("checkpointLocation", s"${s3url}checkpoint/")
    .start()


// COMMAND ----------

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

val schema = StructType(Seq(
  StructField("ingredients", ArrayType(StringType)),
  StructField("name", StringType),   // typo dans le cours julie !!
  StructField("size", IntegerType),
))



// COMMAND ----------

val result = kinesis.selectExpr("CAST(data as STRING) as json")
  .withColumn("pizza", from_json($"json", schema))
  .withColumn("ingredients", expr("pizza.ingredients"))
  .withColumn("name", expr("pizza.name"))
  .withColumn("size", expr("pizza.size"))
  .drop("json") // we do not need this column anymore, so we remove it from the dataset

// COMMAND ----------

display(result)
