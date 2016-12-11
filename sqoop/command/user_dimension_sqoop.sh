#!/bin/bash

 sqoop import --connect jdbc:mysql://bigdata:3306/log --username root --password root --table user_dimension --driver com.mysql.jdbc.Driver --m 10 --target-dir /warehouse/user_dimension
