
LOAD DATA LOCAL INFILE '/home/bigdata/datasource/user.list' INTO TABLE log.user_dimension
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
