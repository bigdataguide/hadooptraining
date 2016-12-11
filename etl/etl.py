#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys,time


def timestamp_datetime(value):
    format = '%Y-%m-%d %H:%M:%S'
    value = time.localtime(value)
    dt = time.strftime(format,value)
    return dt

# input comes from STDIN (standard input)
for line in sys.stdin:
    # remove leading and trailing whitespace
    columns = line.split(",");

    if len(columns) != 12:
        continue

    for i in range(len(columns)):
        columns[i] = columns[i].strip()

    columns[3]=timestamp_datetime(int(columns[3]))
    print columns[0]+","+columns[1]+","+columns[2]+","+columns[3]+","+columns[4]+","+columns[5]+","+columns[6]+","+columns[7]+","+columns[8]+","+columns[9]
