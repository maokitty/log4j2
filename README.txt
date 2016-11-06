log4j2使用：
1:不使用任何配置，默认只输出日志到终端，格式为 %d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n，而且只会输出error级别
2:读取默认配置路径xml文件。log4j2通过自己预先定义的优先级顺序，依次拼接出配置文件的全名，通过classloader.getResource【loader.class line 43】来获取URI
  通过URI来读取配置文件并赋值给相对应的configuration【Classloader默认读取的是 src/main/resources】
3:添加Logger配置专属于本类/包的输出
4:additivity。子logger拥有自己的appender,会打印出当前的消息到配置的console,同时自动继承父appender，父logger也会根据其配置的appender输出到console
