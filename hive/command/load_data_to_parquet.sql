set hive.exec.dynamic.partition.mode=nonstrict;
insert into table record_parquet partition(partition_date,hour_minute) select * from record;
