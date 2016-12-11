package bigdata.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.*;
import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianxi.zhang on 11/26/16.
 */
public class LogProcessTopology {

  public static final String brokerZkStr = "bigdata:2181";
  public static final String topicName = "log";
  public static final String offsetZkRoot = "/storm" + "-" + topicName;
  public static final String offsetZkId = "offsetZkId";
  public static final String redis_hots = "bigdata";
  public static final int redis_port = 6379;

  public static StormTopology getStormTopology() {

    BrokerHosts hosts = new ZkHosts(brokerZkStr);
    SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, offsetZkRoot, offsetZkId);
    spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
    KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

    JedisPoolConfig poolConfig =
        new JedisPoolConfig.Builder().setHost(redis_hots).setPort(redis_port).build();

    RedisStoreMapper provinceStoreMapper = new ProvinceStoreMapper();
    RedisStoreBolt provinceStoreBolt = new RedisStoreBolt(poolConfig, provinceStoreMapper);

    RedisStoreMapper websiteStoreMapper = new WebsiteStoreMapper();
    RedisStoreBolt websiteStoreBolt = new RedisStoreBolt(poolConfig, websiteStoreMapper);

    TopologyBuilder builder = new TopologyBuilder();
    builder.setSpout("spout", kafkaSpout, 1);
    builder.setBolt("extractbolt", new ExtractBolt(), 1).shuffleGrouping("spout");

    builder.setBolt("provincebolt", new ProvinceBolt(), 1)
        .fieldsGrouping("extractbolt", "province", new Fields("province"));
    builder.setBolt("websitebolt", new WebsiteBolt(), 1)
        .fieldsGrouping("extractbolt", "website", new Fields("website"));

    builder.setBolt("provinceredisstore", provinceStoreBolt).shuffleGrouping("provincebolt");
    builder.setBolt("websiteredisstore", websiteStoreBolt).shuffleGrouping("websitebolt");

    return builder.createTopology();
  }

  public static Config getConfig() {
    Config conf = new Config();
    return conf;
  }

  public static void main(String[] args) {

    Config conf = getConfig();
    StormTopology topology = getStormTopology();

    if (args != null && args.length > 0) {
      //提交到集群运行
      try {
        StormSubmitter.submitTopology(args[0], conf, topology);
      } catch (AlreadyAliveException e) {
        e.printStackTrace();
      } catch (InvalidTopologyException e) {
        e.printStackTrace();
      } catch (AuthorizationException e) {
        e.printStackTrace();
      }
    } else {
      //本地模式运行
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("Topotest", conf, topology);
      Utils.sleep(1000000);
      cluster.killTopology("Topotest");
      cluster.shutdown();
    }
  }

}
