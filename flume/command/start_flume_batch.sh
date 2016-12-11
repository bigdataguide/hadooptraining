#!/bin/bash

flume-ng agent --conf /home/bigdata/apache-flume-1.7.0-bin/conf --conf-file /home/bigdata/apache-flume-1.7.0-bin/conf/flume-conf-logAnalysis.properties --name logAgent -Dflume.root.logger=DEBUG,console -Dflume.monitoring.type=http -Dflume.monitoring.port=34545

