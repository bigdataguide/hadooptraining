# -*- coding: utf-8 -*-

import json

class Chart(dict):
    """
    图表模板
    """
    def __init__(self):
        super(Chart, self).__init__()
        self["calculable"] = True
        self["tooltip"] = {"show": True}
        self["toolbox"] = {
            "show": True,
            "x": "left",
            "feature": {
                "dataView": {
                    "show": True,
                    "readOnly": False
                },
                "magicType": {
                    "show": True,
                    "type": ["line", "bar"]
                },
                "restore": {
                    "show": True
                },
                "saveAsImage": {
                    "show": True
                },
                "dataZoom": {
                    "show": True,
                    "title": {
                        "dataZoom": u"区域缩放",
                        "dataZoomReset": u"区域缩放后退"
                    }
                }
            }
        }
        self["legend"] = {
            "show": True,
            "data": []
        }
        self["series"] = []

    def title(self, x="center", **kwargs):
        """
        设置图表标题
        """
        self["title"].update({
            "x": x
        })
        self["title"].update(kwargs)
        return self

    def tooltip(self, show=True, trigger='axis', formatter=None, **kwargs):
        """
        设置提示信息
        """
        self["tooltip"].update({
            "show": show,
            "trigger": trigger
        })
        if formatter is not None:
            self["tooltip"].update({"formatter": formatter})
        self["tooltip"].update(kwargs)
        return self

    def legend(self, show=True, data=None, orient='horizontal', **kwargs):
        """
        设置图例
        `data`: [u"图例1", u"图例2", u"图例3"]
        `orient`: "vertical"|"horizontal"
        """
        data = [] if data is None else data
        self["legend"].update({
            "show": show,
            "data": data,
            "orient": orient
        })
        self["legend"].update(kwargs)
        return self

    def toolbox(self, show=True, x='left', **kwargs):
        """
        设置工具箱
        """
        self["toolbox"].update({
            "show": show,
            "x": x
        })
        self["toolbox"].update(kwargs)
        return self

    def pie(self, name, data=None, radius="55%", center=None, auto_legend=True, **kwargs):
        """
        添加一个饼图
        `data`: {u"名称": 100}, u"名称2": 200}
        """
        center = ["50%", "60%"] if center is None else center
        data = {} if data is None else data
        self["series"].append(self.__merge_dict({
            "type": "pie",
            "name": name,
            "radius": radius,
            "center": center,
            "data": [{"name": n, "value": v} for n, v in data.items()]
        }, kwargs))
        if auto_legend:
            legend_data = self["legend"]["data"]
            [legend_data.append(x) for x in data if x not in legend_data]
        return self

    def bar(self, name, data=None, auto_legend=True, y_axis_index=0, **kwargs):
        """
        添加一个柱状图
        `data`: [10, 20, 30, 40]
        `auto_legend`: 自动生成图例
        """
        data = [] if data is None else data
        self["series"].append(self.__merge_dict({
            "type": "bar",
            "name": name,
            "data": data,
            "yAxisIndex": y_axis_index
        }, kwargs))
        if "yAxis" not in self:
            self.y_axis()
        if name not in self["legend"]["data"] and auto_legend:
            self["legend"]["data"].append(name)
        return self

    def line(self, name, data=None, mark_max_point=False, mark_min_point=False, show_item_label=False, auto_legend=True, y_axis_index=0, **kwargs):
        """
        添加一个折线图
        `data`: [10, 20, 30, 40]
        """
        data = [] if data is None else data
        mark_point = []
        if mark_max_point:
            mark_point.append({"type": "max", "name": "最大值"})
        if mark_min_point:
            mark_point.append({"type": "min", "name": "最小值"})
        self["series"].append(self.__merge_dict({
            "type": "line",
            "name": name,
            "data": data,
            "markPoint": {
                "data":mark_point
            },
            "itemStyle": {
                "normal": {
                    "label": {"show": show_item_label}
                }
            },
            "yAxisIndex": y_axis_index
        }, kwargs))
        if "yAxis" not in self:
            self.y_axis()
        if name not in self["legend"]["data"] and auto_legend:
            self["legend"]["data"].append(name)
        return self

    def x_axis(self, data=None, type_="category", name="", **kwargs):
        """
        添加X轴
        """
        data = [] if data is None else data
        if "xAxis" not in self:
            self["xAxis"] = []
        self["xAxis"].append(self.__merge_dict({
            "type": type_,
            "name": name,
            "data": data
        }, kwargs))
        return self

    def y_axis(self, data=None, type_="value", name="", formatter=None, **kwargs):
        """
        添加X轴
        """
        if "yAxis" not in self:
            self["yAxis"] = []
        self["yAxis"].append(self.__merge_dict({
            "type": type_,
            "name": name,
        }, {"axisLabel": {"formatter": formatter}} if formatter is not None else {}, kwargs))
        if data is not None:
            self["yAxis"] = data
        return self
    def map(self,data,**kwargs):
        self["legend"]={
            "orient":"vertical",
            "left": "left",
            "data":['price']
        }
        self["toolbox"]={
            "show": True,
            "orient": "vertical",
            "left": "right",
            "top": "center",
            "feature": {
              "mark":{"show":True},
              "dataView": {"show": True, "readOnly": False},
              "restore": {"show":True},
              "saveAsImage": {"show":True}
          }
        }
        #data={"name": '北京',"value": 10 }
        #data=json.dumps(data,ensure_ascii=False)
        self["series"]=[{
            "name":"price",
            "type": "map",
            "mapType": "china",
            "roam": False,
            "label": {
                "normal": {
                    "show": True
                },
                "emphasis": {
                    "show": True
                }
            },
            #"data": [data]
            "data": [{"name": n, "value": v} for n, v in data.items()]
        }]
        self["visualMap"]={
            "min": 0,
            "max": 2500,
            "left": 'left',
            "top": 'bottom',
            "text": ['高','低'],
            "calculable": True
        }
        self["tooltip"]={
            "trigger":"item"
        }
       # self["title"]={
       #     "text": 'price',
       #     "subtext": 'price',
       #     "left": 'center'
       # }
        return self


    @staticmethod
    def __merge_dict(*args):
        """
        合并多个字典并返回
        """
        return reduce(lambda x, y: dict(x.items() + y.items()), args)


def main():
    c = Chart().tooltip()
    print json.dumps(c)

if __name__ == "__main__":
    main()
