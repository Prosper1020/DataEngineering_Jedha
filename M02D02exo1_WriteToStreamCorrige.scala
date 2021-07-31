package d4

import java.nio.ByteBuffer

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder
import com.amazonaws.services.kinesis.model.PutRecordRequest

object exo1_WriteToStreamCorrige {
  def main(args: Array[String]): Unit = {
    println("Loading kinesis client...")

    val awsAccessKeyId = ""
    val awsSecretKey = ""
    val kinesisStreamName = ""
    val kinesisRegion = ""

    // Creating the kinesis client
    val kinesisClient = AmazonKinesisClientBuilder.standard()
      .withRegion(kinesisRegion)
      .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKeyId, awsSecretKey)))
      .build()

    // We want to order the words, each time we push new elements we get back a sequence number.
    // When we push again we pass as a parameter to kinsesis the last sequence number.
    var lastSequenceNumber: String = null

    for (i <- 0 to 10) {

      println(s"Putting words onto stream $kinesisStreamName")

      val time = System.currentTimeMillis
      // Generate words
      for (word <- Seq("Through", "three", "cheese", "trees", "three", "free", "fleas", "flew", "While", "these", "fleas", "flew", "freezy", "breeze", "blew", "Freezy", "breeze", "made", "these", "three", "trees", "freeze", "Freezy", "trees", "made", "these", "trees", "cheese", "freeze", "That's", "what", "made", "these", "three", "free", "fleas", "sneeze")) {
        val data = s"$word"
        val partitionKey = s"$word"

        // We generate the request
        val request = new PutRecordRequest()
          .withStreamName(kinesisStreamName)
          .withPartitionKey(partitionKey) // this would be important if we used more than one partition in kinesis.
          .withData(ByteBuffer.wrap(data.getBytes()))
        if (lastSequenceNumber != null) {
          request.setSequenceNumberForOrdering(lastSequenceNumber)
        }
        // We push the request to kinesis
        val result = kinesisClient.putRecord(request)
        println(result)
        lastSequenceNumber = result.getSequenceNumber()
      }
      Thread.sleep(math.max(10000 - (System.currentTimeMillis - time), 0)) // loop around every ~10 seconds
    }
  }
}
