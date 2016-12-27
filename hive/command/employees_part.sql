CREATE TABLE IF NOT EXISTS employees_part (
	name	STRING,
	salary	FLOAT,
	subordinates ARRAY<STRING>,
	decutions	MAP<STRING, FLOAT>,
	address	STRUCT<street:STRING, city:STRING, state:STRING, zip:INT>
)
PARTITIONED BY (state STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\001'
COLLECTION ITEMS TERMINATED BY '\002'
MAP KEYS TERMINATED BY '\003'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;

-- LOAD DATA LOCAL INPATH '/home/bigdata/hadooptraining/hive/data/employees.txt' 
-- OVERWRITE INTO TABLE employees_part PARTITION(state='IL');

--INSERT INTO TABLE employees_part PARTITION(state = 'IL')
--SELECT * FROM employees where address.state='IL'; 

-- FROM employees e
-- INSERT OVERWRITE TABLE employees_part PARTITION(state = 'IL') SELECT e.* where e.address.state='IL'
-- INSERT OVERWRITE TABLE employees_part PARTITION(state = 'CA') SELECT e.* where e.address.state='CA' 
-- INSERT OVERWRITE TABLE employees_part PARTITION(state = 'NY') SELECT e.* where e.address.state='NY'; 

FROM employees e
INSERT OVERWRITE TABLE employees_part PARTITION(state) SELECT e.*,e.address.state
-- INSERT OVERWRITE TABLE employees_part PARTITION(state = 'CA') SELECT * where e.address.state='CA'
-- INSERT OVERWRITE TABLE employees_part PARTITION(state = 'NY') SELECT * where e.address.state='NY';
