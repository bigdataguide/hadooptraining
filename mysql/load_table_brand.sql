
LOAD DATA LOCAL INFILE '/home/bigdata/datasource/brand.list' INTO TABLE log.brand_dimension
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
