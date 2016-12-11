create table if not exists record_orc (
 rid STRING,
 uid STRING,
 bid STRING,
 trancation_date TIMESTAMP,
 price INT,
 source_province STRING,
 target_province STRING,
 site STRING,
 express_number STRING,
 express_company STRING
)
 PARTITIONED BY (
 partition_date STRING,
 hour_minute STRING
 )
STORED AS ORC;
