# junload-maven
 oracle相比informix而言，在卸数方面相对不那么方便。以前使用proc写过一个unload，这次考虑迁移到java版本。
 使用maven package打包后，生成一个**可执行jar**.
 ```shell
 java -jar junload-1.0.0-jar-with-dependencies.jar 参数 
 ````
 
## -c configfile  数据库配置文件，如

```java
jdbc.url=jdbc:oracle:thin:@//IP:11521/PDB_00
jdbc.driver=oracle.jdbc.driver.OracleDriver
jdbc.user=user
jdbc.password=password
jdbc.timeout=10 #数据库建立连接超时时间
```
## -t tabname 
表名，一般用于整表卸数 ，作为 ***-q*** 的简化版本，等同于 ```sql select * from tabname ```
## -q sql 
指定sql语句，如 ```sql select sysdate from dual ```
## -f sqlfile 
对于sql语句较长的情况下，可以编辑一个文本用来存放***sql***语句
## -d delimiter 
字段间的分隔符，默认为 |@|
## -o outfile
输出文本
