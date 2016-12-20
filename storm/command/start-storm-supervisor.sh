#!/bin/bash

nohup /home/bigdata/apache-storm-1.0.2/bin/storm supervisor >> /home/bigdata/apache-storm-1.0.2/logs/supervisor.log 2>&1 & 
