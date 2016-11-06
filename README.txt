log4j2使用：
1:不使用任何配置，默认只输出日志到终端，格式为 %d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n，而且只会输出error级别
2:读取默认配置路径xml文件。log4j2通过自己预先定义的优先级顺序，依次拼接出配置文件的全名，通过classloader.getResource【loader.class line 43】来获取URI
  通过URI来读取配置文件并赋值给相对应的configuration【Classloader默认读取的是 src/main/resources】
3:添加Logger配置专属于本类/包的输出
4:additivity。子logger拥有自己的appender,会打印出当前的消息到配置的console,同时自动继承父appender，父logger也会根据其配置的appender输出到console
5:configure之packages.逗号分隔的包名，用来查找插件，只有类加载的时候才会加载插件
  configure之schema.检查configure语法
  configure之shutdownHook。配置jvm关闭的时候是否自动关闭log4j.默认配置自动关闭
  configure之status。终端需要记录下的log级别。包括trace,debug,info,warn,error,fatal.在logger中配置的level可以覆盖status设置
  configure值strict.使用严格模式的xml格式。(json不支持)
6:Logger:用来指定LoggerConfig的配置。必须要有一个name属性,可以添加level,additivity属性。默认level是error,additivity默认false
  每个configure必须有一个root logger.rootlogger与其它logger主要区别在于root不需要有name属性，也不支持additivity
7:属性替代。通过${name}方式获取在configuration中声明属性的值。其它地方的属性通过给属性添加前缀来获取。一般格式是${prefix:attr}
8:支持js.grrovy,BeanShell来配置。
9:通过<xi:include href="log4j-xinclude-appenders.xml" />方式来添加其它文件的引用
10:junit测试专属配置，可以通过1：在src/test/resources下面添加名字为 log4j2-test.xml方式来测试输出；2：在注解@Before里添加 log4j.configurationFile
   3:public LoggerContextRule init = new LoggerContextRule("MyTestConfig.xml");初始化。但是需要包含log4j-core test-jar
