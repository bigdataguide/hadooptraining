#! /bin/bash
while read LINE; do
  for word in $LINE
  do
    echo "$word 1"
  done
done
