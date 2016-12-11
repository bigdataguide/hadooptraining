# -*- coding:utf-8 -*-

from flask import Flask, render_template
import json
from models import Chart
from query_presto import Presto_Query
from query_redis import Redis_Query

app = Flask(__name__)

@app.route("/")
def index():
    presto=Presto_Query()
    age_price_tuples=presto.query_age_price()
    age_dict=presto.getAgeDict(age_price_tuples)
    chart1 = Chart().pie("饼图", data=age_dict
           )
    
    tuples=presto.query_brand_price()
    keys=presto.getKeys(tuples)   
    values=presto.getValues(tuples) 
    chart2 = Chart() \
             .x_axis(data=keys) \
             .y_axis(formatter="{value}") \
             .bar(u"Brand Price", values, show_item_label=True)
 
    redis=Redis_Query()
    province_price=redis.query_province()
    china_province_price=redis.get_province_price(province_price)
    print china_province_price
    chart3= Chart()\
             .map(china_province_price)

    render = {
        "title": u"电商双十一大数据日志分析系统",
        "templates": [
            {"type": "chart", "title":u"不同年龄消费的情况", "option": json.dumps(chart1, indent=2)},
            {"type": "chart", "title":u"消费商品的情况", "option": json.dumps(chart2, indent=2)},
            {"type": "chart", "title":u"各省购买情况", "option": json.dumps(chart3, indent=2)}
        ]
    }
    return render_template("main.html", **render)

if __name__ == "__main__":
    app.run(debug=True)
