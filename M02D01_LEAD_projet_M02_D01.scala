// Databricks notebook source
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.Window

// COMMAND ----------

val awsAccessKeyId = "AKIAUQKC2EE3EK4YLSOO"
val awsSecretKey = "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"

// COMMAND ----------

sc.hadoopConfiguration.set("fs.s3a.access.key", awsAccessKeyId)
sc.hadoopConfiguration.set("fs.s3a.secret.key", awsSecretKey)

// COMMAND ----------

var orders = spark.read.json("s3://jedha-lead-databricks/data/orders-part*.json")
var pickers = spark.read.option("header","true").csv("s3://jedha-lead-databricks/data/pickers.csv")

/*
// alternative
val j = "/FileStore/tables/orders_part?.json"
val p = "/FileStore/tables/pickers.csv"

var orders = spark.read.json(j)
var pickers = spark.read.option("header","true").csv(p)
*/

// COMMAND ----------

display(
  orders.groupBy("status").count()
  )

// COMMAND ----------

orders = orders.withColumn("timestamp", date_format($"timestamp","yyyy-MM-dd HH-mm-ss.SSSSSS"))

// COMMAND ----------

// only select the last order of each order

val w = Window.partitionBy($"order_id").orderBy($"timestamp".desc)
val only_latest_orders = orders.withColumn("rank", row_number().over(w)).where($"rank" === 1).drop("rank")

// COMMAND ----------

val itemsPerBlock = 
  orders.filter(col("status") === "CREATED" || col("status") === "PROCESSING")
  .select(explode(col("items"))).withColumn("location", col("col.location"))
  .select(split(col("location"), "-").as("location"))
  .withColumn("block", col("location")(1))
  .groupBy(col("block")).count()

display(itemsPerBlock)

// COMMAND ----------

/*
his is very condensed:

On the first line we parse the array of items
One the 2nd line we extract the block form the location string
On the 3d line we select the nested sku
Then we remove the temporary column "block" Groupy by block, and count the items for each block */

val itemsPerBlock = only_latest_orders
      .select($"order_id", $"status", $"timestamp", explode($"items").as("item"))
      .withColumn("block", regexp_extract($"item.location", "BLOCK-(.+)-(.+)", 2))
      .withColumn("sku", $"item.sku")
      .drop("item")
      .groupBy($"block")
      .count()
      .as("items_to_pick")

display(itemsPerBlock)

// COMMAND ----------

val blockStats = pickers
    .withColumn("items_per_hour", $"pickers" * 10)
    .drop("pickers")
    .join(itemsPerBlock, "block")

display(blockStats)

// COMMAND ----------

blockStats.coalesce(1).write.mode(SaveMode.Overwrite).csv("s3://jedha-lead-databricks/data/block_stats.csv")

// COMMAND ----------


