# 功能集合

##  douz-rate-limit-guava

- google guava的限流实现
- 适合单体应用
- 制作成了starter

##  引用

```
// 1.mvn clean install后添加依赖
<dependency>
    <groupId>com.dsp</groupId>
    <artifactId>douz-rate-limit-guava-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

```
// 2.配置文件中至少：
douz.limit.enabled = true
```
```
// 3. 使用@RateLimitGuava
```


## TODO

- maven依赖调整
- 项目版本
- 配置项douz.limit.type尚未完善