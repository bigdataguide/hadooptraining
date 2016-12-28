#!/bin/bash
HADOOP_HOME=/home/hadoop/hadoop-2.7.3
INPUT_PATH=/test/input
OUTPUT_PATH=/test/output
echo "Clearing output path: $OUTPUT_PATH"
$HADOOP_HOME/bin/hadoop fs -rmr $OUTPUT_PATH

${HADOOP_HOME}/bin/hadoop jar\
   ${HADOOP_HOME}/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar\
  -files mapper.php,reducer.php\
  -input $INPUT_PATH\
  -output $OUTPUT_PATH\
  -mapper "php mapper.php" \
  -reducer "php reducer.php" \
