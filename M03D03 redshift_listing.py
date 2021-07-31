# -*- coding: utf-8 -*-

# [START tutorial]
from datetime import timedelta

# [START import_module]
# The DAG object; we'll need this to instantiate a DAG
from airflow import DAG
# Operators; we need this to operate!
from airflow.operators.bash_operator import BashOperator
from airflow.utils.dates import days_ago
from airflow.operators.s3_to_redshift_operator import S3ToRedshiftTransfer

# [END import_module]

# [START default_args]
default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': days_ago(2),
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
}
# [END default_args]

# [START instantiate_dag]
dag = DAG(
    'jedha-s3-redshift',
    default_args=default_args,
    description='A simple tutorial DAG for JEDHA',
    schedule_interval=timedelta(days=1),
)
# [END instantiate_dag]

# t1, t2 and t3 are examples of tasks created by instantiating operators
# [START basic_task]
t1 = BashOperator(
    task_id='print_date',
    bash_command='date',
    dag=dag,
)

# [END basic_task]
redshift = S3ToRedshiftTransfer(
        task_id='redshift',
        schema = 'public',
        table = 'listings',
        s3_bucket='airflow-bco',
        s3_key='listings.csv',
        redshift_conn_id='redshift_conn', #reference to a specific redshift database
        aws_conn_id='aws_default', #reference to a specific S3 connection
        copy_options=['delimiter \',\'', 'ignoreheader 1', 'format CSV', 'quote \'"\''],
        dag=dag,
    )

t1 >> redshift
# [END tutorial]