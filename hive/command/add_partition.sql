
load data inpath "hdfs://bigdata:9000/etl/record/2016-11-24/2300" overwrite into table record partition(partition_date="2016-11-24",hour_minute="2300")
