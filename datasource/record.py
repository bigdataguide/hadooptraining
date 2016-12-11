#!/usr/bin/env python
# -*- coding: utf-8 -*-

from faker import Factory
import random,sys,time,uuid

USER_FILE="/home/bigdata/datasource/user.list"
BRAND_FILE="/home/bigdata/datasource/brand.list"
RECORD_FILE="/home/bigdata/datasource/record.list"

WEBSITE_LIST=("TAOBAO","TIANMAO","JUHUASUAN","TIANMAOCHAOSHI")
EXPRESS_LIST=("SHENTONG","SHUNFENG","EMS","YUANTONG","YUNDA","ZHONGTONG")
PROVINCE="BeiJing,ShangHai,TianJin,ChongQing,XiangGang,Aomen,AnHui,FuJian,GuangDong,GuangXi,GuiZhou,GanSu,HaiNan,HeBei,HeNan,HeiLongJiang,HuBei,HuNan,JiLin,JiangSu,JiangXi,LiaoNing,NeiMengGu,NingXia,QingHai,ShanXi1,ShanXi3,ShanDong,SiChuan,TaiWan,XiZang,XinJiang,YunNan,ZheJiang"
PROVINCE_LIST=PROVINCE.split(",");

def get_one_record(fake,user_list,brand_list,id):
    record_id="%010d"%id
    user_id=user_list[random.randint(0,len(user_list)-1)]
    brand_id=brand_list[random.randint(0,len(brand_list)-1)]
    transaction_time=int(time.time())
    price=random.randint(0,1000)
    source_province=PROVINCE_LIST[random.randint(0,len(PROVINCE_LIST)-1)]
    target_province=PROVINCE_LIST[random.randint(0,len(PROVINCE_LIST)-1)]
    website=WEBSITE_LIST[random.randint(0,len(WEBSITE_LIST)-1)]
    express=EXPRESS_LIST[random.randint(0,len(EXPRESS_LIST)-1)]
    express_id=fake.credit_card_number()
    ip=fake.ipv4()
    language=fake.language_code()
    return record_id+","+user_id+","+brand_id+","+str(transaction_time)+","+str(price)+","+source_province+","+target_province+","+website+","+str(express_id)+","+express+","+ip+","+language




def generate_record(total):
    fake = Factory.create()
    user_list=get_user_list()
    brand_list=get_brand_list()
    f=open(RECORD_FILE,'w')
    count=0
    while(count<total):
        mini_count=random.randint(1,10)
        for i in range(mini_count):
            record=get_one_record(fake,user_list,brand_list,count)
            f.write(record+"\n")
            count+=1
        time.sleep(random.randint(0,2))
    f.close()



def get_user_list():
    user_list=[]
    for line in open(USER_FILE):
        user_list.append(line.split(",")[0])
    return user_list

def get_brand_list():
    brand_list=[]
    for line in open(BRAND_FILE):
        brand_list.append(line.split(",")[0])
    return brand_list


if __name__ == '__main__':
    count=int(sys.argv[1])
    print("start to generate transaction data...")
    generate_record(count)
