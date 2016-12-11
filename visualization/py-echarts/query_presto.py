#!/usr/bin/env python
# -*- coding: utf-8 -*-

from pyhive import presto

PRESTO_SERVER = {'host': 'bigdata', 'port': 8080, 'catalog': 'hive', 'schema': 'default'}
BRAND_PRICE_QUERY="select brand,sum(price) as totalPrice from record join brand_dimension on record.bid=brand_dimension.bid group by brand_dimension.brand order by totalPrice desc limit 10"

AGE_PRICE_QUERY="select cast((year(CURRENT_DATE)-year(birth)) as integer) as age,sum(price) as totalPrice from record join user_dimension on record.uid=user_dimension.uid group by cast((year(CURRENT_DATE)-year(birth)) as integer) order by totalPrice desc"

class Presto_Query:

    def query_brand_price(self):
        conn = presto.connect(**PRESTO_SERVER)
        cursor = conn.cursor()
        cursor.execute(BRAND_PRICE_QUERY)
        tuples=cursor.fetchall()
        return tuples

    def getKeys(self,tuples):
        keys=[]
        for tuple in tuples:
            keys.append(tuple[0])
        return keys

    def getValues(self, tuples):
        values=[]
        for tuple in tuples:
            values.append(tuple[1])
        return values
  
    def query_age_price(self):
        conn = presto.connect(**PRESTO_SERVER)
        cursor = conn.cursor()
        cursor.execute(AGE_PRICE_QUERY)
        tuples=cursor.fetchall()
        return tuples 

    def getAgeDict(self, tuples):
        dict={'<10':0L,'10~20':0L,'20~30':0L,'30~40':0L,'40~50':0L,'50~60':0L,'60~70':0L,'>70':0L}
        for tuple in tuples:
            age=int(tuple[0])
            price=long(tuple[1])
            age=age/10;
            if age<1:
                value=dict['<10']
                dict['<10']=value+price
            elif age>=1 and age<2:
                value=dict['10~20']
                dict['10~20']=value+price
            elif age>=2 and age<3:
                value=dict['20~30']
                dict['20~30']=value+price
            elif age>=3 and age<4:
                value=dict['30~40']
                dict['30~40']=value+price
            elif age>=4 and age<5:
                value=dict['40~50']
                dict['40~50']=value+price
            elif age>=5 and age<6:
                value=dict['50~60']
                dict['50~60']=value+price
            elif age>=6 and age<7:
                value=dict['60~70']
                dict['60~70']=value+price
            else:
                value=dict['>70']
                dict['>70']=value+price
        return dict
