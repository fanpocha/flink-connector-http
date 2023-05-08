# flink-connector-http

***介绍***

一个基于flink的http输入输出连接器

***使用***

1、mvn引入

```
    <dependency>
            <groupId>com.fu</groupId>
            <artifactId>flink-connector-http</artifactId>
            <version>1.0.0</version>
    </dependency>
``` 

2、sql source

```sql
               CREATE TABLE `source-test` (
                 `id` varchar   COMMENT 'id',
                 `name` varchar  COMMENT 'name',
                 PRIMARY KEY (`id`)  not enforced
               ) WITH (
                'connector' = 'http',
                'format' = 'file',
                'method' = 'get',
                'url' = 'https://localhost:8088/sdflkcx.csv'
               )";

```

***可用的 options***


|键|	数据类型|默认值|是否必填|	描述|
| ------|------ | -------------------|-------|-------|
|url|	STRING ||是|请求url|
|method|	STRING |get|否|请求方式|
|format|STRING|file|否|数据类型|
|format.file.type|	STRING|csv|否|文件格式|
|format.type.charset|STRING|gb2312|否|文件流量编码|

    
