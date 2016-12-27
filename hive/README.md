# Hive实验手册

### 0. 下载本实验所需要的Git工程

```
cd /home/bigdata
git clone https://github.com/bigdataguide/hadooptraining.git
cd hadooptraining/hive
```
hive目录下包含三个子目录:
* conf: hive的配置
* command: 启动Hive服务的的命令和一些实验用的SQL语句
* data中包含的是外部的一些数据，可以直接导入到Hive表中

**注意：本实验使用的用户主目录是bigdata, 你需要将目录替换为你自己的主目录，下同。**


### 1. 下载安装Hadoop

参考之前的课程内容,下面假定我们的Hadoop目录安装在/home/bigdata/hadoop-2.7.3中，如果你不是安装在这个目录需要替换为你自己的目录

### 2. 配置Mysql
```
#Ubuntu
sudo apt-get install mysql libmysql-java
#CentOS
sudo yum install mysql mysql-connector-java
#启动Mysql
sudo service mysqld start
```

### 3. 安装Hive

#### 3.1 下载Hive二进制包
```    
wget http://apache.mirrors.pair.com/hive/hive-2.1.1/apache-hive-2.1.1-bin.tar.gz /tmp/
#解压hive到工作目录
tar -zxvf /tmp/apache-hive-2.1.1-bin.tar.gz -C /home/bigdata/
cd /home/bigdata/apache-hive-2.1.1-bin/
```

#### 3.2 配置Hive：拷贝Git工程中的配置到Hive目录
```
cp /home/bigdata/hadooptraining/hive/conf/hive-env.sh /home/bigdata/apache-hive-2.1.1-bin/conf/
cp /home/bigdata/hadooptraining/hive/conf/hive-site.xml /home/bigdata/apache-hive-2.1.1-bin/conf/
```
**配置文件的说明见附1,根据实际情况选择自己的主目录**

#### 3.3 启动Hive组件
```
export HADOOP_HOME=/home/bigdata/hadoop-2.7.3
export PATH=/home/bigdata/apache-hive-2.1.1-bin:$PATH
#启动MetaStore Server
nohup hive --service metastore >> /home/bigdata/apache-hive-2.1.0-bin/logs/metastore.log 2>&1 &
#启动HiveServer2 
nohup hive --service hiveserver2 >> /home/bigdata/apache-hive-2.1.0-bin/logs/hive.log 2>&1 &
```
### 4. 启动Hive
#### 4.1 启动Hive CLI
```
hive
```
#### 4.2 启动Beeline CLI
```
beeline -n bigdata -pbigdata -u "jdbc:hive2://localhost:10000/default;auth=noSasl"
#或者
beeline 
beeline> !connect jdbc:hive2://localhost:10000/default bigdata bigdata
```

### 附 配置文件说明
在hive-env.sh中，我们配置了HADOOP_HOME目录，你需要将主目录替换为你自己的主目录
HADOOP_HOME=/home/bigdata/hadoop-2.7.3

在hive-site.xml中，我们配置了:  
1）使用mysql存储元数据
```xml
    <property>
        <name>javax.jdo.option.ConnectionURL</name>
        <value>jdbc:mysql://localhost/metastore?createDatabaseIfNotExist=true</value>
    </property>
    <property>
        <name>javax.jdo.option.ConnectionDriverName</name>
        <value>com.mysql.jdbc.Driver</value>
    </property>
    <property>
        <name>javax.jdo.option.ConnectionUserName</name>
        <value>root</value>
    </property>
    <property>
        <name>javax.jdo.option.ConnectionPassword</name>
        <value>root</value>
    </property>
```    
2）hive在HDFS上的存储路径
```xml
    <property>
        <name>hive.metastore.warehouse.dir</name>
        <value>/warehouse</value>
    </property>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://bigdata:9000</value>
    </property>
```    
3）metastore的端口
```xml
   <property>
        <name>hive.metastore.uris</name>
        <value>thrift://bigdata:9083</value>
    </property>
```    
4）HiveServer2的端口
```xml
   <property>
        <name>hive.server2.thrift.port</name>
        <value>10000</value>
    </property>
    <property>
        <name>beeline.hs2.connection.user</name>
        <value>bigdata</value>
        </property>
     <property>
        <name>beeline.hs2.connection.password</name>
        <value>bigdata</value>
     </property>
```
5) 此外我们还配置自动创建Meta Store的数据库和表
```xml
    <property>
        <name>datanucleus.autoCreateSchema</name>
        <value>true</value>
    </property>
    <property>
        <name>datanucleus.autoStartMechanism</name>
        <value>SchemaTable</value>
    </property>
    <property>
        <name>datanucleus.schema.autoCreateTables</name>
        <value>true</value>
    </property>
```    
