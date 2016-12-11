#!/usr/bin/env python
# -*- coding: utf-8 -*-

import random,sys

BRAND_FILE="/home/bigdata/datasource/brand.list"
BRAND={"computer":("APPLE","HP","ACER","LENOVO","DELL","SONY","ASUS"),"telephone":("IPHONE","SAMSUNG","HTC","MOTOROLA","HUAWEI","OPPO","VIVO","XIAOMI","MEIZU"),"television":("HISENSE","SAMSUNG","SKYWORTH","SHARP","HAIER","PHILIPS","TCL"),"sports":("NIKE","ADIDAS","LINING","PUMA","ANTA","MIZUNO","KAPPA","NB","PEAK","361"),"food":("MENGNIU","YILI","GUANGMING","SANYUAN","WULIANGYE","MOUTAI","HONGXING","NIULANSHAN","LANGJIU"),"clothes":("ZARA","HLA","UNIQLO","PEACEBIRD","GXG","SELECTED","SEMIR","SEPTWOLVES","CAMEL"),"cosmetic":("LOREAL","NIVEA","KANS","DHC","CLINIQUE","INNISFREE","MEIFUBAO","OLAY","LANCOME")}

def get_one_brand(category_list,id):
    brand_id="%08d"%id
    category_size=len(category_list)
    category=category_list[random.randint(0,category_size-1)]
    brand_size=len(BRAND[category])
    brand=BRAND[category][random.randint(0,brand_size-1)]
    return brand_id+","+category+","+brand



def generate_brand():
    category_list=[]
    for k in BRAND:
        category_list.append(k)
    f=open(BRAND_FILE,'w')
    for i in range(count):
        brand=get_one_brand(category_list,i)
        f.write(brand+"\n")
    f.close()





if __name__ == '__main__':
    count=int(sys.argv[1])
    print("start to generate brand data...")
    generate_brand()
