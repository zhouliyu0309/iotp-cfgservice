#### 设备协议模型使用手册
> 设备协议模型是设备在物联网平台的功能描述，包括设备的感知数据、属性、状态、事件。物联网平台通过设备协议模型组装上报设备的数据。

##### 名词解释
| 模型类型 | 描述 |结构类型|存储类型|
| --- | --- | --- | --- |
|感知| 一般用于描述设备上报的感知数据，如当前环境温度等 |支持：lv_bytes、json、xml|支持：MYSQL、HBASE、HBASE_BATCH、REDIS、JMS、PUSH|
|属性| 一般用于描述设备的基本参数 |支持：lv_bytes、json、xml|支持:MYSQL|
|状态|  |支持：lv_bytes、json、xml|支持:REDIS|
|事件| 设备运行时的事件，如设备故障或告警时的温度等 |支持：lv_bytes、json、xml|支持:MYSQL|

##### 设备协议模型
* 头信息校验规则
```
1.判断设备型号是否存在
2.判断模型类型（仅事件及结构体允许多个）
3.事件名不重复
4.必须先添加感知类模型
```
* 头信息添加规则
```
#后端默认添加
1.感知类：
（1）模型名称=设备协议类型：sensor
2.属性、状态
（1）模型名称=设备协议类型：prop、status
（2）模型结构类型=感知类类型
（3）存储类型
3.事件
（1）模型结构类型=感知类类型
（2）存储类型
```
* 字段信息校验规则
```
1.字段名不为空
2.字段名不重复
3.结构类型为LV_BYTES校验规则如下：
（1）非定长字符串：关联字段不为空且类型为Integer
（2）修改、删除整型字段时验证:是否作为关联字段，ps:若该字段已作为关联字段,需先切换非定长字符串关联字段，再进行修改、删除操作

```
* 字段信息添加规则
```
#后端默认添加
1.默认添加基本类型长度
```

* 设备模型发布校验
```
1.模型字段不为空
```
* 作为模板校验
```
1.感知模型不为空
2.是否已作为模板
```
* 复制模板校验
```
1.模型已存在
2.所选型号未定义模板
```

###### 接口修改
```
见com.cetiti.iotp.itf.cfgservice
```
###### 公共包修改
```
见com.cetiti.ddapv2.iotplatform.common.thingModel

```

#### 运行环境

* MAVEN:3.5.2
* JDK:8

#### 使用
```
#本地运行
cd iotp-cfgservice
mvn install
mvn spring-boot:run
#打包
cd iotp-cfgservice
mvn clean package

```
#### 服务器地址

##### 开发环境
* 服务器端口：10.0.40.185:8083
* 接口文档:  10.0.40.185:8083/swagger-ui.html

##### 自测环境
* 服务器端口：10.0.20.89:8083
* 接口文档:  10.0.20.89:8083/swagger-ui.html