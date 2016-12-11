package bigdata.storm;

import org.apache.storm.task.TopologyContext;
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
public class ProvinceBolt extends BaseBasicBolt {

  Map<String, Long> province_price = new HashMap<String, Long>();

  public void execute(Tuple input, BasicOutputCollector collector) {
    String province = input.getStringByField("province");
    long price = Long.valueOf(input.getStringByField("price"));
    long totalPrice = price;
    if (province_price.containsKey(province)) {
      totalPrice += province_price.get(province);
    }
    province_price.put(province, totalPrice);
    collector.emit(new Values(province, String.valueOf(totalPrice)));
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("province", "price"));
  }
}
