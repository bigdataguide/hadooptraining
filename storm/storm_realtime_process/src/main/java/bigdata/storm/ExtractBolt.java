package bigdata.storm;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by qianxi.zhang on 11/26/16.
 */
public class ExtractBolt extends BaseBasicBolt {
  public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
    String word = (String) tuple.getValue(0);

    String price = "0";
    String province = "other";
    String website = "other";

    String[] attributes_list = word.split(",");

    if (attributes_list.length == 12) {
      price = attributes_list[4];
      province = attributes_list[6];
      website = attributes_list[7];
    }

    basicOutputCollector.emit("province", new Values(province, price));
    basicOutputCollector.emit("website", new Values(website, price));
  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declareStream("province", new Fields("province", "price"));
    outputFieldsDeclarer.declareStream("website", new Fields("website", "price"));
  }
}
