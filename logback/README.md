<center>Springnboot 整合 logback</center>

#### 日志的概念

日志是用来追踪和记录我们的程序运行中的信息，我们可以利用日志很快定位问题，追踪分析。

#### 简介

[官方文档](http://logback.qos.ch/manual/)

Logback继承了log4j，是一款开源项目。它由Ceki Gülcü设计，也是 log4j的创始人。 它建立在十年来设计工业强度测试系统的经验基础上。由此产生的产品，比所有现有的日志记录系统更快，占用更小的空间，有时还会有很大的差距 。同样重要的是，日志返回提供了其他日志系统所没有的独特而非常有用的特性。

##### 主流日志框架

目前比较主流的Java常用日志框架：Logback,Log4j,Log4j2,JUL等

#### 环境版本

| 服务名称    | 版本号    |
| ----------- | --------- |
| Spring boot | 2.7.8(GA) |
| Java        | 1.8       |

#### 项目pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <packaging>pom</packaging>
    <modules>
        <module>logback</module>
    </modules>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.smile</groupId>
    <artifactId>learn</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>learn</name>
    <description>框架学习项目</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
      
       	<dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

logback是SpringBoot内置的日志处理框架,spring-boot-stater其中包含了spring-boot-starter-logging

![image-20230221101753867](https://smilebear.oss-cn-beijing.aliyuncs.com/markdown/image-20230221101753867.png)

#### 自定义logback配置文件

Spring Boot官方推荐优先使用带有-spring的文件名作为你的日志配置（如使用logback-spring.xml，而不是logback.xml），命名为logback-spring.xml的日志配置文件，spring boot可以为它添加一些spring boot特有的配置项。

`logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy`

默认的命名规则，并且放在 src/main/resources 下面即可，**注意：logback.xml加载早于application.yml**

#### logback-spring.xml配置详细介绍

##### 根节点 configuration

```xml
<configuration debug="false" scan="true" scanPeriod="30 seconds">
  	<!-- 其他配置省略-->  
</configuration>
```

| 属性名称   | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| debug      | 默认为false，若设置为true，则打印出logback内部日志信息       |
| scan       | 默认值为true，若设置为true，配置文件如果发生改变，将会被重新加载 |
| scanPeriod | 与scan配合使用，当scan为true时，此属性生效，默认的时间间隔为1分钟，设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。如可以设置为scanPeriod="30 seconds"每30秒检测一次 |

##### 定义变量

```xml
<property name="APP_NAME" value="livechat"/>
```

通过property元素可定义变量,它有name和value两个属性,变量可以使“${name}”来使用变量。作用类似于代码中的常量字符串，定义之后公共地方便可以统一使用。如日志文件名称前缀、日志路径、日志输出格式等

| 属性名称 | 描述   |
| -------- | ------ |
| name     | 变量名 |
| value    | 变量值 |

1. %d 日期时间，也可以自定义格式%d{yyyy-MM-dd HH:mm:ss.SSS}，大括号里面的格式只要是正常的计算机日期格式即可
2. %method 方法名字，或者%M
3. %level 日志级别，或者%-5level或者
4. ${PID:- } 进程号，或者 $PID
5. %thread 线程名字，或者%t
6. %logger{40} 包名字符个数，会在长度为40的空间尽可能显示较全的类名，太长会保留首字母并用点号分割
7. %line 行号，或者%L
8. %msg 消息，或者%m或者%message
9. %n 换行
10. %c类的完整名称
11. %-40.40logger{40} 其中的“-”表示左对齐，不够40个字符给右侧补空格。".40“表示超过40个字符的情况下从左侧切断。{40}表示输出字符最长40个字符，否则按照句点分割。其他同理

```xml
<springProperty scope="context" name="logLevel" source="log.level" defaultValue="INFO"/>
```

如果是在Spring或SpringBoot项目当中，想让value值是通过配置文件获取，可使用springProperty来定义

| 属性名称     | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| scope        | 作用域（设置属性的范围，例local（**默认**）、context、system） |
| name         | name 是将配置文件的路径作为当前文件的一个变量，然后用${logLevel}来引用（面向对象思想，一样的东西尽量写一份） |
| source       | source 的值logging.file.path是读取了yml文件的配置，可以分环境配置不同路径 我的yml配置 |
| defaultValue | 默认值                                                       |

1. local 从配置文件中定义其属性的位置到该配置文件的解释/执行结束为止，都存在具有本地范围的属性。因此，每次解析和执行配置文件时，都会重新定义本地作用域中的变量。
2. context 具有上下文范围的属性被插入到上下文中，并且持续时间与上下文一样长，直到被清除为止。一旦定义，上下文范围内的属性就是上下文的一部分。这样，它在所有日志记录事件中都可用，包括那些通过序列化发送到远程主机的事件。
3. system 具有系统范围的属性被插入 JVM 的系统属性中，并且持续时间与 JVM 一样长，或者直到被清除为止

##### coversionRule 自定义规则

```xml
<conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
```

| 属性名称       | 描述                       |
| -------------- | -------------------------- |
| conversionWord | 转换名称（以%clr进行使用） |
| converterClass | 转换实现类                 |

##### appender组件

```xml
<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
  <withJansi>true</withJansi>
  <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
    <level>${logLevel}</level>
  </filter>
  <encoder>
    <pattern>
      ${CONSOLE_LOG_PATTERN}
    </pattern>
    <charset>UTF-8</charset>
  </encoder>
</appender>
```

![img](https://smilebear.oss-cn-beijing.aliyuncs.com/markdown/59af626d675436b7987833b141851387.jpeg)

| 属性名称        | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| appender.name   | name指定appender名称，后面使用该appender是也是通过名称来指定 |
| appender.class  | class属性指定要实例化的appender类的完全限定名称。appender类默认有以下几种 |
| withJansi       | 如果操作系统底层不支持颜色转义，可以自动忽略颜色转义         |
| encoder         | 编码器格式                                                   |
| encoder.pattern | 文件输出格式                                                 |
| encoder.charset | 编码格式                                                     |

1. ConsoleAppender：日志输出到控制台，类名ch.qos.logback.core.ConsoleAppender。
2. FileAppender：日志输入到文件，类名ch.qos.logback.core.FileAppender。
3. RollingFileAppender：滚动记录文件，FileAppender的子类，当符合条件（大小、时间），日志进行切分处理。类名：ch.qos.logback.core.rolling.RollingFileAppender
4. SizeAndTimeBasedRollingPolicy: 基于时间和大小，滚动记录文件，FileAppender的子类，类名：ch.qos.logback.core.rolling.RollingFileAppender

##### filter过滤器

logback具有过滤器支持。logbcak允许给日志记录器appender配置一个或多个Filter(或者给整体配置一个或多个TurboFilter)，来控制:当满足过滤器指定的条件时，才记录日志(或不满足条件时，拒绝记录日志)。logback支持自定义过滤器，当然logback也自带了一些常用的过滤器，在绝大多数时候，自带的过滤器其实就够用了，一般是不需要自定义过滤器的

logback提供的过滤器支持主要分两大类：

```java
ch.qos.logback.core.filter.Filter
ch.qos.logback.classic.turbo.TurboFilter
```

| 过滤器                 | 来源        | 说明                                                         | 相对常用 |
| ---------------------- | ----------- | ------------------------------------------------------------ | -------- |
| LevelFilter            | Filter      | 对指定level的日志进行记录(或不记录)，对不等于指定level的日志不记录(或进行记录) | 是       |
| ThresholdFilter        | Filter      | 对大于或等于指定level的日志进行记录(或不记录)，对小于指定level的日志不记录(或进行记录) 提示：info级别是大于debug的 | 是       |
| EvaluatorFilter        | Filter      | 对满足指定表达式的日志进行记录(或不记录)，对不满足指定表达式的日志不作记录(或进行记录 | 是       |
| MDCFilter              | TurboFilter | 若MDC域中存在指定的key-value，则进行记录，否者不作记录       | 是       |
| DuplicateMessageFilter | TurboFilter | 根据配置不记录多余的重复的日志                               | 是       |
| MarkerFilter           | TurboFilter | 针对带有指定标记的日志，进行记录(或不作记录)                 | 否       |

TurboFilter的性能是优于Filter的，这是因为TurboFilter的作用时机是在创建日志事件ILoggingEvent对象之前，而Filter的作用时机是在创建之后。若一个日志注定是会被过滤掉不记录的，那么创建ILoggingEvent对象(包括后续的参数组装方法调用等)这个步骤无疑是非常消耗性能的

##### LevelFilter

- `DENY`：表示不用看后面的过滤器了，这里就给拒绝了，不作记录。
- `NEUTRAL`：表示需不需要记录，还需要看后面的过滤器。若所有过滤器返回的全部都是NEUTRAL，那么需要记录日志。
- `ACCEPT`：表示不用看后面的过滤器了，这里就给直接同意了，需要记录。

##### TimeBasedRollingPolicy

```xml
<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
  <fileNamePattern>${log.path}/sys-user.%d{yyyy-MM-dd}.${hostname}.log</fileNamePattern>
  <!-- 将30天的历史记录的总大小限制在3GB  -->
  <maxHistory>30</maxHistory>
  <totalSizeCap>3GB</totalSizeCap>
  <cleanHistoryOnStart>false</cleanHistoryOnStart>
</rollingPolicy>
```

| 属性名称            | 描述                                    |
| ------------------- | --------------------------------------- |
| fileNamePattern     | 日志文件名字格式                        |
| maxHistory          | 保留日志历史记录天数                    |
| totalSizeCap        | 日志大小限制                            |
| cleanHistoryOnStart | 是否删除日志（默认false）一般情况用不到 |

##### 日志级别

日志级别从低到高分为 TRACE < DEBUG < INFO < WARN < ERROR < FATAL

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false" scan="true" scanPeriod="30 seconds">
    <!-- 属性 -->
    <property name="APP_NAME" value="livechat"/>

    <springProperty scope="context" name="logLevel" source="logging.level.name" defaultValue="INFO"/>
    <springProperty scope="context" name="logPath" source="logging.file.path" defaultValue="./logs"/>

    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>

    <property name="CONSOLE_LOG_PATTERN"
              value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([${APP_NAME},%thread,%X{FS-TraceId:-},%X{FS-SpanId:-}]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wex}}"/>
    <property name="FILE_LOG_PATTERN"
              value="%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level[${APP_NAME},%thread,%X{FS-TraceId:-},%X{FS-SpanId:-}]%logger{100}.%method:%L- %m%n"/>

    <!-- 控制台输出 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <withJansi>true</withJansi>

        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>${logLevel}</level>
        </filter>

        <encoder>
            <pattern>
                ${CONSOLE_LOG_PATTERN}
            </pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 输出INFO|DEBUG日志到文件 -->
    <appender name="FILE_INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logPath}/info/info_livechat.log</file>

        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>

        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 日志级别,只输出INFO的log -->
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>NEUTRAL</onMismatch>
        </filter>

        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 日志级别,只输出DEBUG的log -->
            <level>DEBUG</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>

        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 日志文件路径 -->
            <fileNamePattern>${logPath}/${APP_NAME}.info.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <!-- 每天滚动 -->
            <maxFileSize>100MB</maxFileSize>
            <!-- 保留90天 -->
            <maxHistory>90</maxHistory>
            <!-- 限制日志文件大小 -->
            <totalSizeCap>30GB</totalSizeCap>
        </rollingPolicy>
    </appender>

    <!-- 输出ERROR日志到文件 -->
    <appender name="FILE_ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logPath}/error/error_livechat.log</file>

        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>

        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <!-- 日志级别,只输出WARN以上的log -->
            <level>WARN</level>
        </filter>

        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 日志文件路径 -->
            <fileNamePattern>${logPath}/${APP_NAME}.error.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <!-- 每天滚动 -->
            <maxFileSize>100MB</maxFileSize>
            <!-- 保留30天 -->
            <maxHistory>90</maxHistory>
            <!-- 限制日志文件大小 -->
            <totalSizeCap>30GB</totalSizeCap>
        </rollingPolicy>
    </appender>

    <logger name="com.smile.learn.logback.controller" level="info" additivity="true">
        <appender-ref ref="CONSOLE" />
    </logger>

    <root level="${logLevel}">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE_INFO"/>
        <appender-ref ref="FILE_ERROR"/>
    </root>
</configuration>
```

参考地址：

https://blog.csdn.net/qq_36850813/article/details/108569093

https://blog.csdn.net/wo541075754/article/details/109193354

https://blog.csdn.net/lh155136/article/details/125312351

