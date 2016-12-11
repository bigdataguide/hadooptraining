#!/usr/bin/env python
# -*- coding: utf-8 -*-


import redis  

PROVINCE_MAP={"BeiJing":"北京","ShangHai":"上海","TianJin":"天津","ChongQing":"重庆","XiangGang":"香港","Aomen":"澳门","AnHui":"安徽","FuJian":"福建","GuangDong":"广东","GuangXi":"广西","GuiZhou":"贵州","GanSu":"甘肃","HaiNan":"海南","HeBei":"河北","HeNan":"河南","HeiLongJiang":"黑龙江","HuBei":"湖北","HuNan":"湖南","JiLin":"吉林","JiangSu":"江苏","JiangXi":"江西","LiaoNing":"辽宁","NeiMengGu":"内蒙古","NingXia":"宁夏","QingHai":"青海","ShanXi1":"山西","ShanXi3":"陕西","ShanDong":"山东","SiChuan":"四川","TaiWan":"台湾","XiZang":"西藏","XinJiang":"新疆","YunNan":"云南","ZheJiang":"浙江"}

class Redis_Query:

    def query_province(self):
        r = redis.StrictRedis(host='127.0.0.1', port=6379)
        return r.hgetall('province')  

    def get_province_price(self,dict):
        china_price={}
        for k,v in dict.items():
            if k in PROVINCE_MAP:
                new_key=PROVINCE_MAP[k]
                china_price[new_key]=v
        return china_price
