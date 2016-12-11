#!/usr/bin/env python
# -*- coding: utf-8 -*-

from faker import Factory
import random
import sys
USER_FILE="/home/bigdata/datasource/user.list"
PROVINCE="BeiJing,ShangHai,TianJin,ChongQing,XiangGang,Aomen,AnHui,FuJian,GuangDong,GuangXi,GuiZhou,GanSu,HaiNan,HeBei,HeNan,HeiLongJiang,HuBei,HuNan,JiLin,JiangSu,JiangXi,LiaoNing,NeiMengGu,NingXia,QingHai,ShanXi1,ShanXi3,ShanDong,SiChuan,TaiWan,XiZang,XinJiang,YunNan,ZheJiang"
PROVINCE_LIST=PROVINCE.split(",");
def get_one_user(fake,id):
    uid="%08d"%id
    name=fake.last_name()
    gender=fake.simple_profile()["sex"]
    birth=fake.simple_profile()["birthdate"]
    province=PROVINCE_LIST[random.randint(0,len(PROVINCE_LIST)-1)]
    return uid+","+name+","+gender+","+birth+","+province

def generate_user(count):
    fake = Factory.create()
    f=open(USER_FILE,'w')
    for i in range(count):
        user=get_one_user(fake,i)
        f.write(user+"\n")
    f.close()


if __name__ == '__main__':
    count=int(sys.argv[1])
    print("start to generate user data...")
    generate_user(count)
