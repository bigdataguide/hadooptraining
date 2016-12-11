#!/bin/bash

sqoop import --connect jdbc:mysql://bigdata:3306/log --username root --password root --table brand_dimension --driver com.mysql.jdbc.Driver --m 10 --target-dir /warehouse/brand_dimension
