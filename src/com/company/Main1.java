package com.company;

import com.company.utils.MyDateUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main1 {
    public static void main(String[] args) throws Exception {

        List<User> userList = Arrays.asList(
                new User(1,"张三", 18, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(1))),
                new User(2,"李四", 20, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(2))),
                new User(3,"王五", 21, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(3))),
                new User(4,"小明", 22, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(4))),
                new User(5,"小蓝", 23, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(5))),
                new User(6,"小红", 24, "女", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(6)))
        );

        Map<String, User> map = userList.stream().collect(Collectors.toMap(User::getName, Function.identity()));
        User mapOrDefault = map.getOrDefault("小黑", null);


        // 过滤
        List<User> filterList1 = userList.stream().filter(a -> a.getName().startsWith("小")).collect(Collectors.toList());
        List<User> filterList2 = userList.stream().filter(a -> a.getAge() > 20 && "男".equals(a.getSex())).collect(Collectors.toList());
        List<User> filterList3 = userList.stream().filter(a -> a.getName().startsWith("小")).filter(a -> a.getAge() > 20).collect(Collectors.toList());


        // 转List
        List<String> nameList = userList.stream().map(User::getName).collect(Collectors.toList());

        List<UserInfo> userInfoList = userList.stream().map(user -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(user.getName());
            userInfo.setAge(user.getAge());
            userInfo.setSex(user.getSex());
            return userInfo;
        }).collect(Collectors.toList());


        // 转Map
        Map<String, String> stringMap = userList.stream().collect(Collectors.toMap(User::getName, User::getSex));

        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        // 生成新的stream流  flatMap将原来的流转换为一个新的流并且，是以每一个值为单位的，   这里根据 逗号分隔 取分隔后的所有值
        List<String> list = Arrays.asList("1,2,3","bb","cc");

        List<String> stringList = list.stream().flatMap(l -> {
            String[] strings = l.split(",");
            return Arrays.stream(strings);
        }).collect(Collectors.toList());

        List<String> simpleStringList = list.stream().flatMap(a -> Arrays.stream(a.split(","))).collect(Collectors.toList());


        // List<List<User>>   转换成   List<User>
//        List<List<User>> userListList = null;
//        List<User> result = userListList.stream().flatMap(it -> it.getUserList().stream()).collect(Collectors.toList());



        // 从索引2开始向前取值（不包含索引2）
        List<User> limitList = userList.stream().limit(2).collect(Collectors.toList());
        // 从索引2开始向后取值（包含索引2）
        List<User> skipList = userList.stream().skip(2).collect(Collectors.toList());

        // 总数量
        Long count = userList.stream().map(User::getAge).count();

        // 平均年龄       averagingInt()   averagingDouble()   averagingLong()
        Double averaging = userList.stream().collect(Collectors.averagingInt(User::getAge));

        // 年龄求和  mapToInt()  mapToDouble()   mapToLong()
        Integer sum = userList.stream().mapToInt(User::getAge).sum();

        // 最大年龄       comparingInt()   comparingDouble()   comparingLong()
        User max = userList.stream().max(Comparator.comparingInt(User::getAge)).get();

        // long 可以直接比较， Long 比较是否相等 equals   比较大小转long比较  Long.longValue()
        List<Long> longList = Arrays.asList(100L, 200L, 300L);
        Long maxLong = longList.stream().max(Long::compareTo).get();

        // 最大日期      Date 比较是否相等 equals    比较大小  时间戳比较
        Date maxDate = userList.stream().max(Comparator.comparingLong(a -> a.getDate().getTime())).get().getDate();

        // 最小年龄
        User min = userList.stream().min(Comparator.comparingInt(User::getAge)).get();

        // 获取 总数，和，最小，平均，最大     summarizingInt()  summarizingDouble()  summarizingLong()
        IntSummaryStatistics summarizing = userList.stream().collect(Collectors.summarizingInt(User::getAge));



        // 检查是否匹配所有元素
        Boolean allMatch = userList.stream().allMatch(a -> a.getAge() > 10);

        // 检查是否有匹配至少一个元素
        Boolean anyMatch = userList.stream().anyMatch(a -> a.getAge() < 20);

        // 检查是否没有匹配的元素
        Boolean noneMatch = userList.stream().noneMatch(a -> a.getAge() < 10);

        // 返回第一个元素
        User first = userList.stream().findFirst().get();


        // 将流中元素反复结合起来得到一个值
        // 根据2元运算将所有的数加起来
        // 首先以0为x，1为y，结果为1，然后1为x，取3为y，结果为4，以4为x...以此类推
        List<Integer> integerList = Arrays.asList(1,3,2,6,8,3,9);
        Integer reduce1 = integerList.stream().reduce(0, (x, y) -> x + y);

        //原理同上，只是这里没有初始值，直接取1为x
        //所以ist就有可能为空，当返回的值可能为空时，结果存储在Optional容器中,避免空指针异常， .get()转换成对应类型
        Integer reduce2 = integerList.stream().reduce((x, y) -> x + y).get();


        // 去重
        List<String> distinctList = userList.stream().map(User::getSex).distinct().collect(Collectors.toList());


        // 连接       以"，"逗号拼接name
        String joiningName = userList.stream().map(User::getName).collect(Collectors.joining("，"));


        // 分区     年龄 > 20岁的 为一个List
        Map<Boolean, List<User>> partitioningByMap = userList.stream().collect(Collectors.partitioningBy(a -> a.getAge() > 20));


        // 分组
        Map<String, List<User>> groupingByMap = userList.stream().collect(Collectors.groupingBy(User::getSex));
        // 多字段分组
        Map<String, List<User>> manyMap = userList.stream().collect(Collectors.groupingBy(a -> a.getSex() + "_" + a.getAge()));
        // 多级分组
        Map<String, Map<String, List<User>>> mapMap = userList.stream().collect(Collectors.groupingBy(User::getSex, Collectors.groupingBy(a -> {
                    if (a.getAge() > 20) {
                        return "20+ ";
                    }
                    return "20- ";
                }
        )));
        // 分组计数
        Map<String, Long> countingMap = userList.stream().collect(Collectors.groupingBy(User::getSex, Collectors.counting()));


        // 正序
        List<User> sortList = userList.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
        // 倒序
        List<User> reversedList = userList.stream().sorted(Comparator.comparing(User::getAge).reversed()).collect(Collectors.toList());
        // 多字段排序，先根据age倒序排序，再根据id正序排序， 从后向前
        List<User> manySortList = userList.stream().sorted(Comparator.comparing(User::getId)).sorted(Comparator.comparing(User::getAge).reversed()).collect(Collectors.toList());


        List<User> userList1 = Arrays.asList(
                new User(1,"张三", 18, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(1))),
                new User(null,"李四", 20, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(2))),
                new User(3,"王五", 21, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(3))),
                new User(4,"小明", 22, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(4))),
                new User(null,"小蓝", 23, "男", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(5))),
                new User(6,"小红", 24, "女", new BigDecimal("111"), MyDateUtil.localDateTimeConvertDate(LocalDateTime.now().minusDays(6)))
        );
        // 排序 Comparator.nullsFirst(Integer::compareTo),用于处理要比较的一个或两个字段为空的情况.
        List<User> resUserList1 = userList1.stream().sorted(Comparator.comparing(User::getId, Comparator.nullsFirst(Integer::compareTo)).reversed()).collect(Collectors.toList());
        resUserList1.stream().forEach(System.out::println);

        System.out.println("********************");

        List<User> collect = userList1.stream().filter(a -> a.getAge() >= 20).collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
        User user = collect.stream().max(Comparator.comparing(User::getAge)).get();
        System.out.println(user);

        System.out.println("********************");

        // BigDecimal  lambda求和   BigDecimal.ZERO 从零开始
        BigDecimal totalAmount = userList.stream().map(User::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);



//        BigDecimal
//        加法：add
//        减法：subtract
//        乘法：multiply
//        除法：divide


//        首先说一下用法，BigDecimal中的 divide 主要就是用来做除法的运算。其中有这么一个方法.
//                                  第一个参数是除数，第二个参数代表保留几位小数，第三个代表的是使用的模式。
//new BigDecimal中的("100").divide(new BigDecimal(55.555), 4, RoundingMode.HALF_EVEN).toPlainString()

//        java.math.RoundingMode 几个参数详解
//
//        RoundingMode.CEILING：取右边最近的整数
//
//        RoundingMode.DOWN：去掉小数部分取整，也就是正数取左边，负数取右边，相当于向原点靠近的方向取整
//
//        RoundingMode.FLOOR：取左边最近的正数
//
//        RoundingMode.HALF_DOWN:五舍六入，负数先取绝对值再五舍六入再负数
//
//        RoundingMode.HALF_UP:四舍五入，负数原理同上
//
//        RoundingMode.HALF_EVEN:这个比较绕，整数位若是奇数则四舍五入，若是偶数则五舍六入


//        保留两位小数   .setScale(2, BigDecimal.ROUND_HALF_UP)
//        new BigDecimal("2.225667").setScale(2, BigDecimal.ROUND_DOWN); 得到2.22
//
//        BigDecimal.ROUND_DOWN:直接省略多余的小数，比如1.28如果保留1位小数，得到的就是1.2
//
//        BigDecimal.ROUND_UP:直接进位，比如1.21如果保留1位小数，得到的就是1.3
//
//        BigDecimal.ROUND_HALF_UP:四舍五入，2.35保留1位，变成2.4
//
//        BigDecimal.ROUND_HALF_DOWN:四舍五入，2.35保留1位，变成2.3
//
//        后边两种的区别就是如果保留的位数的后一位如果正好是5的时候，一个舍弃掉，一个进位。


        // BigDecimal  比较大小
        int res = new BigDecimal("111").compareTo(BigDecimal.ZERO);
        if (res == 1) {
            System.out.println("res大于0");
        }
        if (res == -1) {
            System.out.println("res小于0");
        }
        if (res == 0) {
            System.out.println("res等于0");
        }

        BigDecimal a = new BigDecimal (1111);
        BigDecimal b = new BigDecimal (2222);
        //使用compareTo方法比较
        //注意：a、b均不能为null，否则会报空指针
        if(a.compareTo(b) == -1){
            System.out.println("a 小于 b");
        }
        if(a.compareTo(b) == 0){
            System.out.println("a 等于 b");
        }
        if(a.compareTo(b) == 1){
            System.out.println("a 大于 b");
        }
        if(a.compareTo(b) > -1){
            System.out.println("a 大于等于 b");
        }
        if(a.compareTo(b) < 1){
            System.out.println("a 小于等于 b");
        }



        // 获取30天前的时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, -30);    // 减少30天
//        nowTime.add(Calendar.DATE, 30);    // 增加30天
        nowTime.set(Calendar.HOUR_OF_DAY, 0);   //控制时
        nowTime.set(Calendar.MINUTE, 0);        //控制分
        nowTime.set(Calendar.SECOND, 0);        //控制秒
        Date time = nowTime.getTime();


        // 获取当天0点时间
        // Calendar.HOUR_OF_DAY     24小时制
        // Calendar.HOUR            12小时制
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);   //控制时
        cal.set(Calendar.MINUTE, 0);        //控制分
        cal.set(Calendar.SECOND, 0);        //控制秒
        Date time1 = cal.getTime();





//        JDK8新特性里提供了3个时间类：LocalDate、LocalTime、LocalDateTime
        System.out.println("*****************");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //  减去一个月
        System.out.println(localDateTime.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //  减去一个月，取这个月的第一天
        System.out.println(localDateTime.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //  增加一个月
        System.out.println(localDateTime.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //  增加一个月，取这个月的最后一天
        System.out.println(localDateTime.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));



        byte[] decode = Base64.decode("suddenly.com");
        System.out.println(decode);



//        正则
//        Pattern类表示的是某种匹配模式。一个Pattern对象和一个正则表达式相关联，而不表示具体的匹配。
//        Pattern pattern = Pattern.compile("[a-z]\\d{3}.*");
//        Matcher类表示的是具体的匹配。一个Matcher对象和一个具体的字符串相关联，表示在指定模式下的这个字符串的匹配。
//        Matcher matcher = pattern.matcher("hello123world");
//        具体的匹配信息都在Matcher对象中，所以多个Matcher对象可以共享一个Pattern对象。
//        查看给定的字符串是否完全匹配指定模式：
//        System.out.println(matcher.matches()); // true
//        下面的代码完全等价，这是一个简便方法
//        System.out.println(Pattern.matches("[a-z]+\\d{3}.*", "hello123world")); // true

        System.out.println("******");
        Pattern pattern = Pattern.compile("[a-z]+\\d{3}.*");
        Matcher matcher = pattern.matcher("hello123world");
        System.out.println(matcher.matches());

        System.out.println(Pattern.matches("[a-z]+\\d{3}.*", "hello123world"));















    }









}
