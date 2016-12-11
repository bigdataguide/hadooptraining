#!/bin/bash

nohup hive --service hiveserver2 >> /home/bigdata/apache-hive-2.1.0-bin/logs/hive.log 2>&1 &
