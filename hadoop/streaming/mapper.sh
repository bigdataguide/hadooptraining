#! /bin/bash
while read LINE; do
  for word in $LINE
  do
    echo "$word 1"
    # in streaming, we define counter by 
    # [reporter:counter:<group>,<counter>,<amount>]
    # define a counter named counter_no, in group counter_group
    # increase this counter by 1
    # counter shoule be output through stderr
    echo "reporter:counter:counter_group,counter_no,1" >&2
    echo "reporter:counter:status,processing......" >&2
    echo "This is log for testing, will be printed in stdout file" >&2
  done
done
