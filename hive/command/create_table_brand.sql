create external table if not exists brand_dimension (
 bid STRING,
 category STRING,
 brand STRING
)ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 location 'hdfs://bigdata:9000/warehouse/brand_dimension'
 ;
