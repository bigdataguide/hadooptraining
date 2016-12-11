create external table if not exists user_dimension (
 uid STRING,
 name STRING,
 gender STRING,
 birth DATE,
 province STRING
)ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 location 'hdfs://bigdata:9000/warehouse/user_dimension'
 ;
