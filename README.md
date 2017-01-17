# pi-sensor-demo
室内物联网监控系统, 采用 树莓派作为单片机, 利用 温度/ 湿度/ PM2.5 /摄像头 传感器等外接的传感器对室内环境 进行全方位的监控. 


### 文件结构
* pi-sensor-hardware/  硬件设计文档
* pi-sensor-client/   传感器程序代码
* pi-sensor-server/   服务端传感器代码
* README.md     系统整体设计 

### 物料清单
| 器件名称 | 价格 |
|        |      |
|          |      |

* DHT 传感器 1个
* 树莓派B型 1个
* 树藤科技PM2.5 传感器1个
* 摄像头 1个 
* 云服务器一台

### 技术架构 
![技术架构](http://7xrppw.com1.z0.glb.clouddn.com/razorerSnip20170117_24.png)

#### 终端系统与程序
* 采用android things -> 全部的
* 终端采用树莓派的python 脚本
* 

#### 服务端
* 服务端采用SpringBoot 微服务框架
* 具体的技术使用文档: 

#### 数据层 
* 采用influxdb时间序列的存储. 比较适用于物联网时间序列数据存储和查询
* 具体的使用文档: 
 
#### 展示层
* 展示层使用 [freeboad-iot展示](https://github.com/Freeboard/freeboard)
