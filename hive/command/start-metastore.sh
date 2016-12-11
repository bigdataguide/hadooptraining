#!/bin/bash

nohup hive --service metastore >> /home/bigdata/apache-hive-2.1.0-bin/logs/hive.log 2>&1 &
