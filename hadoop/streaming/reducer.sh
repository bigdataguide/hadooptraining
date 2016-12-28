#! /bin/bash
count=0
started=0
word=""
while read LINE;do
  newword=`echo $LINE | cut -d ' '  -f 1`
  if [ "$word" != "$newword" ];then
    [ $started -ne 0 ] && echo "$word\t$count"
    word=$newword
    count=1
    started=1
  else
    count=$(( $count + 1 ))
  fi
done
echo "$word\t$count"
