# How to run the project

### Step 1: Configure AWS

- Create a kinesis stream named `Jedha-orders`
- Create a kinesis stream named `Jedha-pickers`
- Create a firehose consumer for `Jedha-orders`
- Upload the data `D02-Spark_SQL/03-Instructors/02-Project_solutions/data` on a s3 bucket

### Step 2: Publish the order events to Kinesis

- Update the aws credentials
- Change path to read data to the bucket you just created
- Run the notebook `Project-Send-order-to-kinsesis.ipynb`
- Let it run...

### Step 3: Publish the picker events to Kinesis

- Update the aws credentials
- Run the notebook `Project-Send-pickers-to-kinsesis.ipynb`
- Let it run...

### Step 4: Run the project

- Update the aws credentials
- Change the path to the firehose s3 location: `s3://path/defined/in/firehose/*/*/*/*/*` but leave the stars
- Run the notebook