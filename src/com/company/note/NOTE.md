抽象：抽象是将一类对象的共同特征总结出来构造的过程，包括数据的抽象和行为的抽象两方面。抽象只关注对象有哪些属性和行为，不关注这些行为的细节是什么。
继承：继承是从已有类得到继承信息创建新类的过程。提供继承信息的类被称为父类（超类、基类）；得到继承信息的类被称为子类（派生类）。继承让变换中的软件系统有了一定的延续，

#JDK 和 JRE 有什么区别？
JDK：Java Development Kit 的简称，java开发工具包，提供了java的开发环境和运行环境
JRE：Java Runtime Environment 的简称，java运行环境，为java的运行提供了所需环境
具体来说 JDK 其实包含了JRE，同时还包含了编译java源码的编译器javac，还包含了很多java程序调试和分析工具。
简单来说：如果你需要运行java程序，只需要安装JRE就可以了，如果需要编写java程序，需要安装JDK

#== 和 equals() 的区别是什么？
对于基本类型和应用类型 == 的作用是不同的
基本类型：比较的是值是否相等
引用类型：比较的是引用是否相等

equals 本质上就是 == ，只不过String、Integer 等重写了 equals 方法，把他变成了值的比较

#两个对象的 hashCode() 相同， equals() 也一定为 true 对吗？
不对，两个对象的 hashCode() 相同，equals() 不一定为 true
因为在散列表中，hashCode() 相等即两个键值对的哈希值相等，然而哈希值相等，并不一定能得出键值对相等

#final在java中的作用
final: final修饰的类不能被继承，final修饰的方法不能被重写，final修饰的变量为常量，常量必须初始化，初始化之后的常量不能被修改。

#java中 Match.round(-1.5) 等于多少
等于 -1 ，因为在数轴上取值时，中间值（0.5）向右取整，所以正0.5向上取整。负0.5直接舍弃。

#String属于基础的数据类型吗
String 不属于基础类型 String 数据对象
基础数据类型有 bet、boolean、short、char、int、long、double、float

#Java中操作字符串都有哪些类？它们之前有什么区别？
操作字符串的类有：String、StringBuffer、StringBuilder
String 和 StringBuffer、StringBuilder 的区别在于 String 声明的变量是不可变的对象，每次操作都会生成新的 String 对象，然后指针指向新的 String 对象，
而 StringBuffer、StringBuilder 可以在原有对象的基础上进行操作，所以在经常改变字符串内容的情况下最好不要使用 String。

StringBuffer、StringBuilder 最大的区别在于，StringBuffer 是线程安全的，而 StringBuilder 是非线程安全的，
单 StringBuilder 的性能是高于 StringBuffer 的，所以单线程环境下推荐使用 StringBuilder ，多线程环境下使用 StringBuffer

#String str = "i"; 与 String str = new String("i"); 一样吗？
不一样，因为内存的分配方式不一样。String str = "i"; 的方式，java虚拟机会将其分配到常量池中，而 String str = new String("i"); 则会被分配到堆内存中。

#字符串反转
StringBuilder 或 StringBuffer 的 reverse()
new StringBuilder().append("abcdefg").reverse();
new StringBuffer().append("abcdefg").reverse();

#String类的常用方法有哪些？
indexOf(); 返回指定字符的索引
charAt(); 返回指定索引的字符
replace(); 字符串替换
trim(); 去除字符串两端空白
split(); 分割字符串，返回一个分割后的字符串数组
getBytes(); 返回字符串的Byte类型数组
length(); 返回字符串长度
toLowerCase(); 将字符串转成小写字母
toUpperCase(); 将字符串转成大写字母
substring(); 截取字符串
equals(); 字符串比较

#抽象类必须要有抽象方法吗
抽象类没有抽象方法完全可以正常运行

#普通类和抽象类有哪些区别
普通类不能包含抽象方法，普通类可以直接实例化
抽象类可以包含抽象方法，抽象类不能直接实例化

#抽象类能使用final修饰吗
不能，定义抽象类就是让其他类继承的，如果定义为final该类就不能被继承，这样彼此就会产生矛盾，所有final不能修饰抽象类，编辑器也也会提示错误信息

#接口和抽象类的区别
实现：抽象类的子类使用 extends 来继承；接口必须使用 implements 来实现接口
构造函数：抽闲类可以有构造函数；接口不能有
main方法：抽象类可以有main方法，并且可以运行；接口不能有main方法
实现数量：类可以实现多个接口；但只能继承一个抽象类
访问修饰符：接口中的方法默认使用 public 修饰；抽象类中的方法可以使用任意修饰符

#java中 IO 流分为几种？
按功能来分：输入流（input）、输出流（output）
按类型来分：字节流、字符流
字节流和字符流的区别：字节流按8位传输以字节为单位输入输出数据、字符流按16位传输以字符为单位输入输出数据

#BIO、NIO、AIO 有是什么区别？
BIO：Block IO 同步阻塞式 IO，就是我们平常使用的传统IO，它的特点是模式简单使用方便，并发处理能力低
NIO：New IO 同步非阻塞式 IO，是传统IO的升级，客户端和服务器端通过 Channel（通道）通讯，实现了多路复用
AIO：Asynchronous IO 是NIO 的升级，也叫 NIO2，实现了异步非阻塞 IO，异步 IO 的操作基于事件和回调机制

#Files 常用的方法有哪些？
Files.exists(); 检测文件路径是否存在
Files.createFile(); 创建文件
Files.createDirectory(); 创建文件夹
Files.delete(); 删除一个文件或目录
Files.copy(); 复制文件
Files.move(); 移动文件
Files.size(); 查看文件数量
Files.read(); 读取文件
Files.write(); 写入文件


ArrayList 初始容量为0 插入第一条插入数据扩容到10 后面按照当前容量的1.5倍进行扩容
HashMap   初始容量16 当前容量的2倍进行扩容