# spring

## 一：程序员的春天

spring春天，是一个业务逻辑框架，用于对象生命周期的管理(**IOC**),以及交叉业务逻辑模块的管理(**AOP**)

## 二：spring能做什么？

用于实现程序设计的两个原则：高低原则，开闭原则

* 高低原则：

高内聚：一个类只关心一件事情，一个类中的方法具有相同或者相似的功能

低耦合：类和类之间耦合度较低，一个类的改变不会影响其它类.

```java
public class UserService{
	UserDAOImpl userDAOImpl = new UserDAOImpl();
}

//使用接口
//使用工厂
//解耦方案：工厂模式+接口
interface UserDAO(){
  
}
public classs UserDAOImpl implements UserDAO{
  
  
}
public class UserService{
	UserDAO userDAO = ObjectFactory.getObject("userDAO");
}

```

* 开闭原则:对扩展开，对修改闭

**spring的作用，可以替换掉ObjeccFactory,JDBCTemplate,DataAccessException,Proxy.......**

## 三：helloworld

> 重新创建一个工程spring，需要重新设置encoding,font,cvs,maven,jdk

### 1、创建模块

创建父模块：spring-parent

### 2、添加jar包

```xml
<!--junit  start-->
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.11</version>
  <scope>test</scope>
</dependency>
<!--junit  end-->

<!--spring  start-->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-core</artifactId>
  <version>${spring.version}</version>
</dependency>

<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-beans</artifactId>
  <version>${spring.version}</version>
</dependency>

<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-aop</artifactId>
  <version>${spring.version}</version>
</dependency>

<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>${spring.version}</version>
</dependency>

<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-expression</artifactId>
  <version>${spring.version}</version>
</dependency>


<!--aspectJ   start-->
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
  <version>1.8.13</version>
</dependency>
<!--aspectJ   end-->
<!--spring  end-->

<!-- log4j jar -->
<dependency>
  <groupId>log4j</groupId>
  <artifactId>log4j</artifactId>
  <version>1.2.17</version>
</dependency>
<!-- log4j jar -->
```

### 3、编写java类

```java
public class HelloWrold {

  public int someMethod() {

    System.out.println("hello spring");
    return 1;

  }
}

```

### 4、配置applicationContext.xml

```xml
<bean id="helloWrold" class="com.itany.spring.helloworld.HelloWrold"></bean>

```

### 5、编写测试类

```java
public class Test {

  public static void main(String[] args) {
    //HelloWrold helloWrold = new HelloWrold();
    //HelloWrold helloWrold1 = new HelloWrold();
    //System.out.println(helloWrold==helloWrold1);
    //helloWrold.someMethod();
    //创建spring应用程序的上下文==>创建spring容器
    //容器：用来存放东西的器皿   spring容器===》是用来存放对象的
    ApplicationContext ac = new ClassPathXmlApplicationContext
      //指定文件的加载路径,在maven项目中源路径就是resources路径
      ("applicationContext.xml");
    //通过id查找对应的bean
    HelloWrold helloWrold = (HelloWrold) ac.getBean("helloWrold");
    HelloWrold helloWrold1 = (HelloWrold) ac.getBean("helloWrold");
    //spring容器中的对象默认是单例的
    System.out.println(helloWrold==helloWrold1);
    helloWrold.someMethod();



  }
}
```

## 四：IOC

### 1、两个概念

* IOC

  inversion of control  控制反转

  将对象的创建权和管理权交给spring,由spring来管理对象的生命周期

* DI
  dependency injection  依赖注入

  为属性注入值

### 2、控制反转

#### 2.1、使用类的无参构造函数

```xml
<!--定义一个bean,默认调用类的无参构造函数-->
<bean id="helloWrold" class="com.itany.spring.helloworld.HelloWrold"></bean>

```

#### 2.2、使用类的有参构造函数

```xml
<!--调用有参构造函数-->
<bean id="someBean" class="com.itany.spring.bean.SomeBean">
  <!--
            构造函数的参数
            index:第几个参数，从0开始
            type:参数类型
            value:参数值

        -->

  <constructor-arg index="0" type="java.lang.Integer" value="1234"/>
  <constructor-arg index="1" type="java.lang.String">
    <value>mike</value>
  </constructor-arg>


</bean>

```

#### 2.3、练习

使用spring创建File对象

```xml
<bean id="file" class="java.io.File">
  <constructor-arg index="0" type="java.lang.String" value="e:/qt/JDBCTest.java"/>

</bean>
```

#### 2.4、使用静态工厂创建对象

```java
public class SomeBeanFactory {

  private static  SomeBean getSomeBean(){
    return  new SomeBean(123,"mike");

  }

  public static  String getStr(){
    return "hello itany";
  }
}

```

```xml
<!--静态工厂创建对象
        返回的是调用方法的返回值类型
    -->
<bean id="someBean1" class="com.itany.spring.bean.SomeBeanFactory" factory-method="getStr"/>
<bean id="someBean2" class="com.itany.spring.bean.SomeBeanFactory" factory-method="getSomeBean"/>
```

#### 2.5、实例工厂对象

```xml
<!-- 实例工厂创建对象
        factory-method:实例的方法
        factory-bean:实例方法所在的bean的id
        class:实例方法的返回值类型
    -->
    <bean id="factory" class="com.itany.spring.bean.SomeBeanFactory"></bean>
    <bean id="someBean3" class="com.itany.spring.bean.SomeBean" factory-bean="factory" factory-method="getSomeBean1"></bean>

```

#### 2.6、**使用FactoryBean**

**FactoryBean工厂对象，是一个特殊的bean,必须实现一个FactoryBean接口**

**通过接口中的方法创建对象**

好处：将创建的对象进行封装，使得程序员无需关心其创建过程

```java
//可以使用接口的泛型传递具体返回的对象类型,默认是object
public class SomeBeanFactoryBean implements FactoryBean<SomeBean> {
  @Override
  //返回最终创建的对象
  public SomeBean getObject() throws Exception {
    return new SomeBean(123,"mike");
  }

  @Override
  //返回创建对象的class
  public Class<SomeBean> getObjectType() {
    //return SomeBean.class;
    return null;
  }

  @Override
  //返回该对象是否单例
  public boolean isSingleton() {
    //true:是单例，否则不是单例
    return true;
  }
}
```

```xml
<!--factoryBean-->
<bean id="someBean" class="com.itany.spring.ioc1.SomeBeanFactoryBean"></bean>
```

### 3、控制反转练习

* 创建Calendar对象
* 创建Date对象
* 获取JAVA_HOM的值
* 获取查询结果

### 4、依赖注入（DI）

#### 4.1、简单类型

##### 1、基本类型或者包装类

```xml
<!--简单类型注入-->
<bean id="sdb" class="com.itany.spring.ioc2.SimpleDataBean">
  <!--为属性注入值
                     name:属性名,属性的set方法,通过反射技术setA到SimpleDataBean中去找方法名为setA
                     value:属性值

              -->
  <property name="a" value="1"/>
  <property name="b" value="1.2"/>
  <property name="c" value="1.3"/>
  <property name="d" value="中"/>
  <property name="e" value="true"/>
  <property name="f" value="123456"/>
  <property name="g" value="12"/>
  <property name="h" value="24"/>

</bean>
```

##### 2、String/Class

```xml
<!--String/Class-->
<bean id="stringClassBean" class="com.itany.spring.ioc2.StringClassBean">
  <property name="str" value="网博"/>
  <!--为class类型的属性注入值：value写:包名.类名-->
  <property name="dateClass" value="java.util.Date"/>
</bean>
```

##### 3、resource类型

```xml
<!--resource类型属性注入
       注入两种路径：
       绝对路径： file:
       类加载路径：classpath:
       -->
<bean id="resourceBean" class="com.itany.spring.ioc2.ResourceBean">
  <property name="fileResource">
    <!-- 绝对路径： file:-->
    <value>file:E:/qt/JDBCTest1.java</value>
  </property>

  <!--类加载路径：classpath:-->
  <property name="pathResource">
    <value>classpath:ioc2/spring.xml</value>
  </property>

</bean>
```

#### 4.2、集合类型

```xml
<!--集合属性的注入-->
<bean id="arrayBean" class="com.itany.spring.ioc2.ArrayBean">
  <property name="arr">
    <array>
      <value>hello</value>
      <value>world</value>

    </array>
  </property>

  <property name="list">
    <list>
      <value>list1</value>
      <value>list2</value>

    </list>

  </property>
  <property name="set">
    <set>
      <value>123</value>
      <value>123</value>
    </set>

  </property>

  <property name="props">
    <props>
      <prop key="abc">hello</prop>
      <prop key="def">world</prop>
    </props>

  </property>

  <property name="map">
    <map>
      <entry key="k1" value="v1"></entry>
      <entry key="k2" value="v2"></entry>
      <entry key="k3" value="v3"></entry>

    </map>

  </property>

</bean>
```

#### 4.3、复杂类型

除了上面出现的类型以外的引用类型

在bean中通过ref属性来引用其他的bean

```xml
<!--复杂类型属性的注入-->
<bean id="userDao" class="com.itany.spring.ioc3.UserDaoOracleImpl"></bean>
<bean id="userService" class="com.itany.spring.ioc3.UserServiceImpl">
  <property name="userDao" ref="userDao"/>

</bean>
```

**注意：在spring配置文件中，凡是出现ref字样的，都是引用其它的bean，引用的是被spring管理的bean**

#### 4.4、属性编辑器

编写属性编辑器需要的步骤：

* 继承PropertyEditorSupport
* 重写setAsText方法
* 配置spring.xml,让其被spring管理

```java
public class AddressEditor extends PropertyEditorSupport {

    @Override
    /*
      text:配置文件中注入的值:江苏，南京
      转成Address对象中属性的值

     */
    public void setAsText(String text) throws IllegalArgumentException {
        String[] str = text.split(",");
        String province = str[0];
        String city = str[1];
        Address address = new Address();
        address.setProvince(province);
        address.setCity(city);
        setValue(address);
    }
}
```

```xml
<!--配置属性编辑器-->
<bean id="customEditor" class="org.springframework.beans.factory.config.CustomEditorConfigurer">
  <property name="customEditors">
    <map>
      <!--
          key:对应的目标类
          value:对应的编辑器
          每当遇到key对应的类型的属性的时候，使用value所对应的属性编辑器
	  -->
      <entry key="com.itany.spring.ioc4.Address"
             value="com.itany.spring.ioc4.AddressEditor"></entry>
    </map>
  </property>
</bean>
```

#### 4.5、作业

```xml
<!--
       在控制台打印：
       姓名：张三，学校：南京市29中   电话：025-83567889
       -->

<bean id="name" class="com.itany.spring.ioc5.Name">
  <property name="firstName" value="三"/>
  <property name="lastName" value="张"/>

</bean>

<bean id="schoolFactory" class="com.itany.spring.ioc5.SchoolFactory"></bean>
<bean id="school" class="com.itany.spring.ioc5.School" factory-bean="schoolFactory" factory-method="getSchool">
  <constructor-arg index="0" type="java.lang.String" value="29中学"/>
  <constructor-arg index="1" type="java.lang.String" value="南京市"/>

</bean>

<bean id="tel" class="com.itany.spring.ioc5.Telephone">
  <property name="prefix" value="025"/>
  <property name="name" value="83567889"/>
</bean>

<bean id="student" class="com.itany.spring.ioc5.Student">
  <property name="name" ref="name"/>
  <property name="school" ref="school"/>
  <property name="telephone" value="025-83567889"/>

</bean>

<!--配置telephoe的属性编辑器-->
<bean id="customEditorConfigurer" class="org.springframework.beans.factory.config.CustomEditorConfigurer">
  <property name="customEditors">
    <map>
      <entry key="com.itany.spring.ioc5.Telephone"
             value="com.itany.spring.ioc5.TelephonePropertyEditor"></entry>
    </map>

  </property>

</bean>
```

### 5、对象的生命周期

spring对对象生命周期的每一个步骤都可以进行管理和配置

#### 5.1、生命周期

| java     | spring                              |
| -------- | ----------------------------------- |
| 创建对象     | 实例化(调用构造函数)                         |
| 为对象赋值    | DI                                  |
|          | **postProcessBeforeInitialization** |
|          | 初始化方法init-method                    |
|          | **postProcessAfterInitialization**  |
| 就绪       | 就绪                                  |
|          |                                     |
| 使用       | 使用                                  |
|          |                                     |
| GC进行垃圾回收 | 销毁方法：destroy-method                 |
| 从内存中销毁   | 从容器中销毁                              |

> 对象是先创建对象还是先赋值?
>
> 先赋值

```java
public class Demo {

  {

    System.out.println(5);
  }
  static {
    System.out.println(2);
  }

  static {
    System.out.println(3);
  }
  static {
    System.out.println(4);
  }
  //private int i=1/0;


  public Demo() {
    System.out.println(1);
  }

  public static void main(String[] args) {
    new Demo();
  }
}
```

> 执行顺序：静态块>块>构造函数，同一个块，再按顺序执行

#### 5.2、init-method配置

init-method属性对应bean中的一个方法，用于对象的初始化(**不是构造函数**)

```xml
<bean id="someBean" class="com.itany.spring.ioc6.SomeBean" init-method="initAaaa"></bean>
```

#### 5.3、destroy-method配置

destroy-method属性对应bean中的一个方法，当spring容器中的bean被销毁之前，该方法会被调用。

```xml
<bean id="someBean" class="com.itany.spring.ioc6.SomeBean" init-method="initAaaa" destroy-method="des"></bean>
```

#### 5.4、lazy-init配置

对象的创建是在容器创建的时候创建的，可以通过lazy-init的属性设置,修改对象创建的时机。

lazy-init

* default  默认值 行为和false一致
* false  不进行延迟加载
* true  延迟初始化，**当第一次从容器中获取该对象时**，进行对象的创建

```xml
<bean id="someBean" class="com.itany.spring.ioc6.SomeBean" init-method="initAaaa" destroy-method="des" lazy-init="true" ></bean>
```

#### 5.5、scope配置

String中的bean,默认是单例的，可以通过对scope属性的设置去改变对象的创建次数

scope

* singleton 默认值  单例
* prototype  每从容器中获取一次，都会创建一个新的对象
* request  每次请求创建一个对象
* session  每次会话创建一个对象

```xml
<bean id="someBean" class="com.itany.spring.ioc6.SomeBean" init-method="initAaaa" destroy-method="des" lazy-init="true" scope="singleton"></bean>
```

#### 5.6、**后处理bean**

所谓的后处理bean,是一个特殊的类，需要实现某个特殊接口，用于对其它的bean做后处理操作

后处理：在DI和init-method之后，对对象做进一步的处理

```java
public class SomeBeanPostProcessor implements BeanPostProcessor {
  @Override
  //在init之前执行
  /*

        第一个参数：o:创建的对象
        第二个参数：s:bean的id(这个id就是被spring管理的bean的id属性的值)
        return:bean的对象

     */
  public Object postProcessBeforeInitialization(Object bean, String beanId) throws BeansException {

    System.out.println(beanId);
    System.out.println(bean.getClass());
    if(bean.getClass()==SomeBean1.class){
      System.out.println(((SomeBean1)(bean)).getName());
      ((SomeBean1)(bean)).setName("rose");
    }
    return bean;
  }

  @Override
  //在init之后执行
  public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
    System.out.println("SomeBeanPostProcessor.postProcessAfterInitialization");
    return bean;
  }
}
```

```xml
<bean id="someBean1" class="com.itany.spring.ioc7.SomeBean1">
  <property name="name" value="mike"/>

</bean>
<bean id="someBean2" class="com.itany.spring.ioc7.SomeBean2">

  <property name="name" value="张三"/>

</bean>
<bean id="someBean3" class="com.itany.spring.ioc7.SomeBean3">

  <property name="name" value="李四"/>
</bean>

<!--配置后处理器
              后处理bean 会作用于所有其他被spring管理的bean
              不需要其它bean来引用，不需要配置id
       -->
<bean class="com.itany.spring.ioc7.SomeBeanPostProcessor"></bean>
```

#### 5.7、后处理器练习

> 需求：给数据源注入属性文件的值

```
思路：
1：使用Resources将datasources.properties文件注入到bean中
2：获取bean中的所有属性
3：遍历
4：对每一个属性的值，判断，如果以${开头，以}结尾
   将原有的值用properties文件中对应的值进行替换
   
Class c = o.getClass();
Fields[]  fields=c.getDeclaredFilelds();
Field f = null;
//设置f可以被访问
f.setAccessable(true);

//获取o这个对象f属性的值
f.get(o);
//为这个o对象的f属性赋值value
f.set(o,value);
```

```java
public class PropertyPlaceholderConfigurer implements BeanPostProcessor {

  private Resource location;

  public void setLocation(Resource location) {
    this.location = location;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {

    try {
      //根据location加载对应的propertis文件
      Properties p = new Properties();
      p.load(location.getInputStream());
      //获取bean中所有的属性
      Field[] fields = bean.getClass().getDeclaredFields();
      //遍历
      for (Field f : fields) {
        //设置f可以被访问
        f.setAccessible(true);
        //获取每一个f对应的值
        String key = (String) f.get(bean);
        //通过正则表达式判断这个key是不是以${开头，}结尾
        String reg="\\$\\{\\w+\\}";
        if(key.matches(reg)){
          key = key.substring(2,key.length()-1);
          String value = p.getProperty(key);
          //为这个o对象的f属性赋值value
          f.set(bean,value);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return  bean;

  }

  @Override
  public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
    return o;
  }
```

### 6、访问properties文件

```xml
<!--使用spring给我们提供的后处理器，读取属性文件中的值-->
  <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">

    <property name="location" value="classpath:ioc8/datasource.properties"/>
</bean>
```

### 7、配置继承关系

```xml
<!--
             parent： 值是另一个bean的id,表明是一种继承关系
             注意：
             代码中的继承，配置中不一定继承
             配置中继承，代码中一定继承
             原因：被spring管理的bean已经不是原来的bean,是其代理bean
       -->

<bean id="child" class="com.itany.spring.ioc8.Child" parent="parent">

</bean>
<bean id="parent" class="com.itany.spring.ioc8.Parent">
  <property name="name" value="mike"/>

</bean>
```

### 8、抽象配置

```xml
<!--在spring中有些类(最常用于抽象类)，不想被容器初始化，可以设置一个属性abstract="true"-->
<bean id="calendar" class="java.util.Calendar" abstract="true"></bean>
```

### 9、bean的自动装配

通过设置autowire属性实现类中的属性的自动装配

autowrire

* **byName**:在容器中寻找id和类中的属性名一致的bean注入到属性中
* no:不进行自动装配，此时需要使用property为属性注入值
* default:和no一样，默认值
* byType:在容器中尝试根据属性的类型找对应的bean进行注入
* constructor:使用构造器参数寻找合适的bean注入到属性中

## 五：AOP

### 1、什么是aop?

AOP:Aspect Oriented Programming 面向切面编程

oop  pl  sop  

将程序中的交叉业务逻辑  从代码中提取出来

封装成切面(方法),由AOP容器(spring)在**适当的时机**（编译器/运行期）

动态的织入到具体的业务逻辑中.

```java
//交叉业务逻辑
class Tran{
  begin();
  commit();
  rollback();
}

//逻辑代码+交叉业务逻辑
class **Proxy{
  Tran tran = new Tran();
  public void someMethod(){
    tran.begin();
    ......
    tran.commit();
    
    
    tran.rollback;
    
  }
}
```

### 2、实现原理

使用的是动态代理技术自动的生成代理类和代理对象,从而实现切面的织入

spring实现aop的方法

1、对于实现了某个接口的类，spring使用java.lang.reflect.Proxy实现动态代理

```java
public class TestProxy {

  public static void main(String[] args) {
    UserDao userDao = new UserDaoImpl();
    System.out.println("事务开始....");
    userDao.select();
    System.out.println("事务结束....");

    //System.out.println("事务开始....");
    userDao.select();
    //System.out.println("事务结束....");

    /*  UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(),
                new Class[]{UserDao.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("事务开始....");
                        new UserDaoImpl().select();
                        System.out.println("事务结束....");
                        return null;
                    }
                }

        );
        userDaoProxy.select();
        userDaoProxy.select();*/
  }



}

interface UserDao{
  public void select();
}

class UserDaoImpl implements UserDao{

  @Override
  public void select() {
    System.out.println("UserDaoImpl.select");
  }
}
```

2、对于没有实现任何接口的类，spring使用第三方的jar包：cglib包生成代理类对象，生成的代理类是目标类的子类。

**基于以上，可以得出结论：如果使用spring对某一个类进行aop,那么这个类必须实现某个接口，或者可以被继承.**

```java
final class A{}  //无法使用spring做aop
```

### 3、几个名词

* 切面

  aspect  交叉业务逻辑代码

* 通知(spring2.*不再使用)

  advice 切面的实际实现，放切面的类或者方法

  ![a](pic/a.jpg)

* 引入

  introduction  在不改变原有类的代码的基础上，为其添加新的属性和方法

  破坏封装

* 切入点

  pointcut

  切面应用到目标对象方法的位置

  位置如何定义：使用AspectJ切点表达式,使用到aspectJ  jar包

  ![b](pic/b.jpg)

* 织入

  weaving

  动词：是一个将切面应用到目标对象，创建代理对象的过程

### 4、Spring AOP  2.*实现方式

#### 4.1、helloworld

##### A:编写目标类

```java
/*
定义的目标类
 */
public class UserService {

    public void rigist(){
        System.out.println("UserService.rigist");
    }

    public void login(){
        System.out.println("UserService.login");
    }

    public void delete(){
        System.out.println("UserService.delete");
    }
}

```

##### B:编写一个通知

> 交叉业务逻辑

```java
/*
定义通知
 */
public class SomeAdvice {

    public void someAdvice(){
        System.out.println("method start at "+
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
```

##### C:配置通知

```xml
<!--配置目标对象-->
<bean id="userService" class="com.itany.spring.aop1.UserService"></bean>

<!--配置通知-->
<bean id="someAdvice" class="com.itany.spring.aop1.SomeAdvice"></bean>

<!--配置AOP-->
<aop:config>
  <!--配置切入点
              expression:aspectJ切点表达式
              within(com.itany.spring.aop1.UserService),定义了一个切入点，切com.itany.spring.aop1.UserService类中的任意方法

              -->
  <aop:pointcut id="pc" expression="within(com.itany.spring.aop1.UserService)"/>
  <!--织入
              将someAdvice定义的someAdvice切入到pc这个切点上
              -->
  <aop:aspect ref="someAdvice">

    <aop:before method="someAdvice" pointcut-ref="pc"/>
  </aop:aspect>

</aop:config>
```

##### D:编写测试类

```java
public class Test {

  public static void main(String[] args) {
    ApplicationContext ac = new ClassPathXmlApplicationContext("aop1/spring.xml");
    UserService userService = (UserService) ac.getBean("userService");
    userService.rigist();
    System.out.println("-----------------");
    userService.login();
    System.out.println("-----------------");
    userService.delete();
    System.out.println("-----------------");

  }
}
```

### 5、前置通知

#### 1、作用

在执行目标对象方法之前执行的通知

#### 2、配置方式

```xml
<aop:pointcut id="pc" expression="within(com.itany.spring.aop1.UserService)"/>
<!--织入
                将someAdvice定义的someAdvice切入到pc这个切点上
                -->
<aop:aspect ref="someAdvice">

  <aop:before method="someAdvice" pointcut-ref="pc"/>
</aop:aspect>

</aop:config>
```

#### 3、方法的参数

所有通知的方法都可以存在一个JointPoint类型的参数,使用这个参数，可以获取目标方法的一些信息.

```java
//前置通知加参数(需要Aspectj  jar包)
public void otherMethod(JoinPoint jp){
  //获取目标方法的参数数组
  Object[] args = jp.getArgs();
  System.out.println(Arrays.asList(args));

  //获取目标方法的方法签名
  Signature signature = jp.getSignature();
  //修饰符
  int modifiers = signature.getModifiers();
  System.out.println(Modifier.toString(modifiers));

  //方法名称
  String name = signature.getName();
  System.out.println(name);

  //获取目标方法所在的类的class
  Class type = signature.getDeclaringType();
  System.out.println(type);

  //目标对象
  Object target = jp.getTarget();
  System.out.println(target);

  Object obj = jp.getThis();
  System.out.println(obj);


}
```

### 6、后置通知

#### 1、作用

在目标方法执行之后执行的通知

#### 2、配置方式

```xml
<aop:aspect ref="someAdvice">

  <aop:before method="someMethod" pointcut-ref="pc"/>
  <!--  <aop:before method="otherMethod" pointcut-ref="pc"/>-->
  <aop:after method="after" pointcut-ref="pc"/>
</aop:aspect>
```

### 7、环绕通知

#### 1、作用

环绕通知是在目标方法执行前，后执行的通知，可以在方法执行前，或者方法执行后执行某些代码，**同时可以决定目标方法是否执行以及何时执行.**

#### 2、配置方式

```xml
<aop:aspect ref="someAdvice">

  <!--  <aop:before method="someMethod" pointcut-ref="pc"/>-->
  <!--  <aop:before method="otherMethod" pointcut-ref="pc"/>-->
  <!--   <aop:after method="after" pointcut-ref="pc"/>-->
  <aop:around method="someMethod1" pointcut-ref="pc"/>
</aop:aspect>

```

#### 3、通知代码

对于环绕通知，方法需要遵循以下几个要求

* 方法的返回值必须是Object
* 方法的参数必须是ProceedingJoinPoint类型
* 方法必须抛出Throwable

```java
public Object someMethod1(ProceedingJoinPoint pjp) throws Throwable{

  //在环绕通知中，需要程序员通过手动方式执行目标方法
  //方法的返回值就是目标方法的返回值
  System.out.println("11111");
  Object result = pjp.proceed();
  System.out.println("2222");
  return result;
}

```

#### 4、目标方法

```java
public Integer regist1(Integer a,Integer b){
  System.out.println("执行了a+b的操作："+a+b);
  return a+b;
}
```

#### 5、环绕通知练习

> 需求：实现方法缓存通知

当重复调用同一个方法，并且传入相同的参数的时候，从缓存中获取方法的结果，而不需要再次执行该方法(session.get/load)

**必须重写equals和hashCode方法**

java补充

list  --->remove  contains

set--->判断重复

map--->get/containsKey

equals和hashCode的区别？

String对象中的equals比较大小，hashCode比较地址

hashCode和equals在没有被重写时是一样的,可以查看Object的equals方法

```java
public class SomeAdvice {

  private Map<Key,Object> cache = new HashMap<>();

  public Object someMethod(ProceedingJoinPoint pjp) throws Throwable{

    //如何判断一个函数是不是重复的
    //1：获取该函数的目标类的字节码对象
    Class<?> cls = pjp.getTarget().getClass();
    //2：获取目标方法名称
    String name = pjp.getSignature().getName();

    //3：获取目标参数
    Object[] args = pjp.getArgs();

    Key key = new Key(cls,name,args);
    Object value = cache.get(key);
    if(value==null){
      value=pjp.proceed();
      cache.put(key,value);
    }
    return value;
  }
}
```

```java
class Key{
  private Class cls;

  private String methodName;

  private Object[] args;

  public Class getCls() {
    return cls;
  }

  public void setCls(Class cls) {
    this.cls = cls;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

  public Key(Class cls, String methodName, Object[] args) {
    this.cls = cls;
    this.methodName = methodName;
    this.args = args;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Key key = (Key) o;

    if (cls != null ? !cls.equals(key.cls) : key.cls != null) return false;
    if (methodName != null ? !methodName.equals(key.methodName) : key.methodName != null) return false;
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(args, key.args);

  }

  @Override
  public int hashCode() {
    int result = cls != null ? cls.hashCode() : 0;
    result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
    result = 31 * result + (args != null ? Arrays.hashCode(args) : 0);
    return result;
  }
}
```

### 8、AspectJ表达式

切点表达式，是一个表达式(expression)用于定义切点（pointcut）

#### 1、within(xxxx)

within(包名.类名)

将切面应用到类的所有方法上

#### 2、execution

匹配特定包下的特定类中的具有特定参数，返回特定类型的特定方法

execution(expre)

expre:【方法的返回值类型】【包名】.【类名】.方法名(【参数类型1，参数类型2....】)

> a   b.c.d.e.f.g(h,i)

#### 3、例子

```
*  任意一部分
.. 任意多部分（0-无穷）
```



```java
execution(void aop2.test.SomeClass.doSome())
execution(java.lang.String aop2.test.SomeClass.doSome())
execution(* aop2.test.SomeClass.doSome())
execution(* aop2.test.SomeClass.doSome(java.lang.String))
//必须有一个参数，类型任意
execution(* aop2.test.SomeClass.doSome(*))
//可以有任意多个参数(0-无穷)
execution(* aop2.test.SomeClass.doSome(..))
//任意方法,任意参数
execution(* aop2.test.SomeClass.*(..))
//任意一个以Some结尾的方法
execution(* aop2.test.SomeClass.*Some(..))
//aop2包下任意以Some结尾的方法
execution(* aop2.*.*Some(..))
```

#### 4、组合使用

within()  and execution()

within()  or execution()

## 六：spring整合JDBC

### 1、添加spring-jdbc支持

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-jdbc</artifactId>
  <version>${spring.version}</version>
</dependency>

<dependency>
  <groupId>commons-dbcp</groupId>
  <artifactId>commons-dbcp</artifactId>
  <version>1.4</version>
</dependency>
```

### 2、方式1(推荐)

```java
public class UserDaoImpl implements UserDao {

  private JdbcTemplate jdbcTemplate;

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public User login(String username, String pass) {

    String sql = new StringBuffer()
      .append(" select  ")
      .append(" id,username,password,age ")
      .append(" from ")
      .append(" t_user ")
      .append(" where username=? and password=? ")
      .toString();
    List<User> users = jdbcTemplate.query(sql, new Object[]{username, pass},
                                          new RowMapper<User>() {
                                            @Override
                                            public User mapRow(ResultSet rs, int i) throws SQLException {
                                              User user = new User();
                                              user.setId(rs.getInt("id"));
                                              user.setUsername(rs.getString("username"));
                                              user.setPassword(rs.getString("password"));
                                              user.setAge(rs.getInt("age"));
                                              return user;
                                            }
                                          });


    return users.isEmpty()?null:users.get(0);
  }
}
```

```xml
<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
  <property name="location" value="classpath:datasource.properties"/>

</bean>

<!--配置数据源-->
<bean id="ds" class="org.apache.commons.dbcp.BasicDataSource">
  <property name="driverClassName" value="${jdbc.driverClassName}"/>
  <property name="url" value="${jdbc.url}"/>
  <property name="username" value="${jdbc.username}"/>
  <property name="password" value="${jdbc.password}"/>

</bean>

<!--方式1-->
<!--配置JDBCTemplate-->
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">

  <property name="dataSource" ref="ds"/>
</bean>
<!--配置DAO-->
<bean id="userDao" class="com.itany.spring.dao.impl.UserDaoImpl">
  <property name="jdbcTemplate" ref="jdbcTemplate"/>

</bean>
```

### 3、方式2

```java
public class UserDaoImplV2 extends JdbcDaoSupport implements UserDao {


    @Override
    public User login(String username, String pass) {

            String sql = new StringBuffer()
                    .append(" select  ")
                    .append(" id,username,password,age ")
                    .append(" from ")
                    .append(" t_user ")
                    .append(" where username=? and password=? ")
                    .toString();
        List<User> users = getJdbcTemplate().query(sql, new Object[]{username, pass},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setAge(rs.getInt("age"));
                        return user;
                    }
                });


        return users.isEmpty()?null:users.get(0);
    }
}
```

```xml
<!--方式2-->
<bean id="userDao" class="com.itany.spring.dao.impl.UserDaoImplV2">
  <property name="dataSource" ref="ds"/>

</bean>
```

## 七：spring整合mybatis

### 1、添加mybatis支持

```xml
<!-- myBatis jar -->
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.4.5</version>
</dependency>
<!-- myBatis jar -->
<!-- myBatis-spring jar -->
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis-spring</artifactId>
  <version>1.3.1</version>
</dependency>
<!-- myBatis-spring jar -->

<!--集成事务  start-->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-tx</artifactId>
  <version>${spring.version}</version>
</dependency>
<!--集成事务  end-->

```

```xml
<mapper namespace="com.itany.mybatis.dao.UserDao">
    <select id="selectUserById" resultType="user" parameterType="java.lang.Integer">
        select id,username,password,age from t_user where id=#{id}

    </select>
</mapper>
```

```xml
<!--整合数据源-->
<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
  <property name="location" value="classpath:datasource.properties"/>

</bean>

<!--配置数据源-->
<bean id="ds" class="org.apache.commons.dbcp.BasicDataSource">
  <property name="driverClassName" value="${jdbc.driverClassName}"/>
  <property name="url" value="${jdbc.url}"/>
  <property name="username" value="${jdbc.username}"/>
  <property name="password" value="${jdbc.password}"/>

</bean>

<!--整合mybatis-->
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
  <!--配置数据源-->
  <property name="dataSource" ref="ds"/>
  <!--扫描别名包-->
  <property name="typeAliasesPackage" value="com.itany.mybatis.entity"/>
  <!--需要关联的mapper映射文件-->
  <property name="mapperLocations">
    <array>
      <value>classpath:com/itany/mybatis/mapper/*Mapper.xml</value>
    </array>

  </property>


</bean>

<!--扫描mapper的路径
       mapper对应dao,使用dao接口的首字母小写，找到对应的dao接口的代理实现类-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
  <property name="basePackage" value="com.itany.mybatis.dao"/>
</bean>
```

## 八：spring整合web

### 1、添加web支持

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-web</artifactId>
  <version>${spring.version}</version>
</dependency>

<!-- servlet jstl -->
<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>javax.servlet-api</artifactId>
  <version>3.1.0</version>
  <scope>provided</scope>
</dependency>

<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>jsp-api</artifactId>
  <version>2.0</version>
  <scope>provided</scope>
</dependency>

<dependency>
  <groupId>jstl</groupId>
  <artifactId>jstl</artifactId>
  <version>1.2</version>
</dependency>
<!-- servlet jstl -->


<build>

  <plugins>
    <plugin>
      <groupId>org.apache.tomcat.maven</groupId>
      <artifactId>tomcat7-maven-plugin</artifactId>
      <configuration>
        <path>/web</path>
        <port>8888</port>
      </configuration>
    </plugin>
  </plugins>
  <resources>
    <resource>
      <directory>src/main/java</directory>
      <includes>
        <include>**/*.xml</include>
        <include>**/*.properties</include>
      </includes>
    </resource>
    <resource>
      <directory>src/main/resources</directory>
      <includes>
        <include>**/*.xml</include>
        <include>**/*.properties</include>
      </includes>
    </resource>

  </resources>
</build>

```

### 2、配置监听器

```xml
<!--配置spring监听器
      在web.xml启动后，自动初始化spring.xml
      默认加载.WEB-INF/applicationContext.xml
      我们更习惯于放在classpath路径下,可以通过context-param进行配置

      -->

<context-param>
  <param-name>contextConfigLocation</param-name>
  <param-value>classpath:spring.xml</param-value>
</context-param>
<listener>
  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

### 3、创建servlet

```java
@WebServlet("/test")
public class SomeServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("SomeServlet.service");
    //在web层如何获取被spring管理的bean
    //获取的是已经被监听器加载的spring容器
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    SomeService someService = (SomeService) ac.getBean("someService");
    someService.someMethod();


    //name=mike&name=rose&name=zhangsan
    String[] names = req.getParameterValues("name");
    req.setAttribute("names",names);
    req.getRequestDispatcher("index.jsp").forward(req, resp);
  }
}
```

## 九：spring注解

Spring在实际开发中，提供了一系列的注解，用于替换配置文件

实际开发中：注解+配置文件

### 1、IOC

#### 1.1、组件的定义

* @Component
  * @Service:一般用在service上
  * @Controller:一般用在action上
  * @Repository:一般用在dao上
  * 以上三个注解和@Component注解的作用是一样的

```java
/**
 * 定义一个组件
 * 组件名：默认是类名，首字母小写
 * 可以使用value属性设置新的组件名，
 * 注解有一个特点，如果注解中只写了一个属性，并且该属性叫value,该属性可以不写
 */
@Service("ss")
public class SomeService {
}
```

#### 1.2、DI

```java
@Service("ss1")
public class SomeService1 {

    //@Value("mike")
    @Value("${name}")
    private String name;

    @Autowired
    /**
     *  @Autowired
     *  默认根据类型byType,改成byName即可,使用 @Qualifier
     */
    @Qualifier("sav2")
    private ServiceA serviceA;


    @Override
    public String toString() {
        return "SomeService1{" +
                "name='" + name + '\'' +
                ", serviceA=" + serviceA +
                '}';
    }
}
```

```java
//di
SomeService1 someService1 = (SomeService1) ac.getBean("ss1");
System.out.println(someService1);

```

#### 1.3、生命周期

```java
@Service("sss")
@Scope("singleton")
public class SomeServiceScope {

  @Lazy(true)//只适用于多例
  public SomeServiceScope() {

    System.out.println("SomeServiceScope.SomeServiceScope");
  }
  //init-method
  @PostConstruct
  public void init(){
    System.out.println("SomeServiceScope.init");
  }

  //destroy-method
  @PreDestroy//只适用于单例
  public void des(){
    System.out.println("SomeServiceScope.des");
  }
}
```

### 2、AOP

```java
@Component
//告诉spring,这是一个组件
//告诉spring,这是一个切面
@Aspect
public class AllAdvice {

  //配置切点
  @Pointcut("execution(* com.itany.spring.aop.TestBean.*(..))")
  public void a(){}

  //前置通知
  @Before("a()")
  public void before(){
    System.out.println("前置通知");
  }
  //后置通知
  @After("a()")
  public void after(JoinPoint jp){
    System.out.println("后置通知");
  }

  //环绕通知
  @Around("a()")
  public Object arround(ProceedingJoinPoint pjp) throws Throwable{
    System.out.println("环绕通知开始....");
    Object obj = pjp.proceed();
    System.out.println("环绕通知结束....");
    return obj;

  }


}
```

```xml
<!--组件扫描
       扫包：扫描某个包中所有类中的注解
       -->
<context:component-scan base-package="com.itany.spring.aop"/>

<!--配置AOP-->
<aop:aspectj-autoproxy/>

```

### 3、事务

```java
/**
 * Author:helloboy
 * Date:2018-07-17 14:04
 * Description:<描述>
 */
@Service
//对于类中的所有方法通用
//rollbackFor:什么时候进行回滚
//propagation:事务的隔离级别
@Transactional(rollbackFor =Exception.class,propagation = Propagation.REQUIRED)
public class SomeService {

    //对于查询方法，给一个只读事务
    @Transactional(readOnly = true)
    public List findAll(){
        return null;
    }
    public void add(){

    }
}
```

```xml
<!--配置事务管理器
       用于指定事务管理器
       默认值：transactionManager
       如果事务管理器用的是默认值， <tx:annotation-driven>就不需要再指定
       -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager"></bean>

<!--启用事务管理器-->
<tx:annotation-driven/>
```













































