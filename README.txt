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
7:lookup:属性查找。通过${name}方式获取在configuration中声明属性的值。其它地方的属性通过给属性添加前缀来获取。一般格式是${prefix:attr}
8:支持js.grrovy,BeanShell来配置。
9:通过<xi:include href="log4j-xinclude-appenders.xml" />方式来添加其它文件的引用
10:junit测试专属配置，可以通过1：在src/test/resources下面添加名字为 log4j2-test.xml方式来测试输出；2：在注解@Before里添加 log4j.configurationFile
   3:public LoggerContextRule init = new LoggerContextRule("MyTestConfig.xml");初始化。但是需要包含log4j-core test-jar
11:用Logger.forName自定义Logger级别,在log4j4.xml中配置的int值大于自定义的值就会输出;可以直接在xml文件中通过CustomLevel来定义标签
   然后用Level.getLevel来获取定义的级别，获取没有定义的级别会抛出空指针异常
12:appender用来指定log输出的目的地。包括consoleAppender(system_out等)/RandomAccessFileAppender（IO表现比fileAppender更好）/Async（单开线程处理）/RollingFileAppender(TriggeringPolicy控制触发的时机
   RolloverStrategy控制log删除的时间)等
13:layout控制日志显示形式，常见格式控制：【大小写敏感】
   c(precision 精度)、C(class类名)、d（date）F（file,log发生的file）L(line发生的行)M（method发生的方法）n(由操作系统决定的换行符)t(thread线程名)
14:filter控制log event是否能被处理。返回4种结果，包括：支持，拒绝和中立
15:web项目与log4j：
  A:log4jConfiguration来表明配置文件的位置，从web项目根目录下寻找
  B:没有在web.xml中配置log4jConfiguration会从 WEB-INF下寻找log4j2.xml,有多个，优先使用log4j2-web项目名字.xml否则使用第一个
  C:通过配置classpath或者文件uri来指定配置文件的位置
  D：isLog4jAutoInitializationDisabled设置是否自动启动log4j
  E:默认情况下使用servletContext的名字作为LoggerContext的名字，当isLog4jContextSelectorNamed设置为true时，通过log4jContextName或者display-name来指定
    LoggerContext的名字（二者必须有一个，同时必须指定log4jConfiguration）
16：Extending Log4j介绍各个组件的基本作用以及实现类