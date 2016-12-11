package bigdata.storm;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianxi.zhang on 11/26/16.
 */
public class WebsiteBolt extends BaseBasicBolt {

  Map<String, Long> website_price = new HashMap<String, Long>();

  public void execute(Tuple input, BasicOutputCollector collector) {
    String website = input.getStringByField("website");
    long price = Long.valueOf(input.getStringByField("price"));
    long totalPrice = price;
    if (website_price.containsKey(website)) {
      totalPrice += website_price.get(website);
    }
    website_price.put(website, totalPrice);
    collector.emit(new Values(website, String.valueOf(totalPrice)));
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("website", "price"));
  }
}

