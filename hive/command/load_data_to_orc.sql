set hive.exec.dynamic.partition.mode=nonstrict;
insert into table record_orc partition(partition_date,hour_minute) select * from record;
