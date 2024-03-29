package com.company.main;

import com.company.dto.UserInfoDTO;
import com.company.entity.User;
import com.company.utils.MyDateUtil;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Demo {
    public static void main(String[] args) throws Exception {

//                select * from user where id = 123
//                delete from user where id = 123
//                insert into user (name, age, sex) values("xiaoming", "20", "nan")
//                update user set name = "xiaoming", age = "20", sex = "nan" where id = 123

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

        Map<String, User> map1 = userList.stream().collect(Collectors.toMap(User::getSex, a -> a, (k1, k2) -> k1));

        // 过滤
        List<User> filterList1 = userList.stream().filter(a -> a.getName().startsWith("小")).collect(Collectors.toList());
        List<User> filterList2 = userList.stream().filter(a -> a.getAge() > 20 && "男".equals(a.getSex())).collect(Collectors.toList());
        List<User> filterList3 = userList.stream().filter(a -> a.getName().startsWith("小")).filter(a -> a.getAge() > 20).collect(Collectors.toList());


        // 转List
        List<String> nameList = userList.stream().map(User::getName).collect(Collectors.toList());

        List<UserInfoDTO> userInfoDTOList = userList.stream().map(user -> {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setName(user.getName());
            userInfoDTO.setAge(user.getAge());
            userInfoDTO.setSex(user.getSex());
            return userInfoDTO;
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

        // 空格拼接name
        String nameJoining = userList.stream().map(User::getName).collect(Collectors.joining(" "));

        // 检查是否匹配所有元素
        Boolean allMatch = userList.stream().allMatch(a -> a.getAge() > 17);

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

        // 对象去重
        List<User> resUserList = userList.parallelStream().filter(distinctByKey(User::getSex)).collect(Collectors.toList());

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


        // Date 类型 最好 把时间戳存到单独的一个 Long类型的字段   然后根据  时间戳字段排序
        // .stream().sorted(Comparator.comparingLong(User::getTimestamp).reversed()).collect(Collectors.toList());

        // 日期倒序
        List<User> collect2 = userList.stream().sorted((x, y) -> {
            Long.compare(x.getDate().getTime(), y.getDate().getTime());
            return 0;
        }).collect(Collectors.toList());

        // 日期倒序
        List<User> collect1 = userList.stream().sorted(Comparator.comparing(User::getDate).reversed()).collect(Collectors.toList());
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

        // 年龄大于等于20
        List<User> collect = userList1.stream().filter(a -> a.getAge() >= 20).collect(Collectors.toList());

        // 最大年龄的信息
        User user = collect.stream().max(Comparator.comparing(User::getAge)).get();


        // BigDecimal  lambda求和   BigDecimal.ZERO 从零开始
        BigDecimal totalAmount = userList.stream().filter(a -> a.getAmount() != null).map(User::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

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


//        正则
////        Pattern类表示的是某种匹配模式。一个Pattern对象和一个正则表达式相关联，而不表示具体的匹配。
//        Pattern pattern = Pattern.compile("[a-z]\\d{3}.*");
////        Matcher类表示的是具体的匹配。一个Matcher对象和一个具体的字符串相关联，表示在指定模式下的这个字符串的匹配。
//        Matcher matcher = pattern.matcher("hello123world");
////        具体的匹配信息都在Matcher对象中，所以多个Matcher对象可以共享一个Pattern对象。
////        查看给定的字符串是否完全匹配指定模式：
//        System.out.println(matcher.matches()); // true
////        下面的代码完全等价，这是一个简便方法
//        System.out.println(Pattern.matches("[a-z]+\\d{3}.*", "hello123world")); // true


        Map<String, List<String>> testMap = new HashMap<>();
        testMap.put("aaa", Arrays.asList("a1", "a2", "a3"));
        testMap.put("bbb", Arrays.asList("b1", "b2", "b3"));
        testMap.put("ccc", Arrays.asList("c1", "c2", "c3"));

        testMap.keySet().forEach(System.out::println);
        testMap.entrySet().forEach(System.out::println);
        // map遍历value
        testMap.entrySet().forEach(value -> {
            String key = value.getKey();
            List<String> value1 = value.getValue();
        });


        // 去除空格
        String str111 = " abc ";
        String str222 = " a b c ";
        str111.trim();
        str222.replaceAll(" ", "");


        // Arrays.asList  放入  ArrayList  才可以使用 .add
        List<String> strList = new ArrayList(Arrays.asList("a", "b"));
        strList.add("c");
        System.out.println(strList);


        // URLEncoder 编码转换
        String  urlEncoderStr = java.net.URLEncoder.encode("测试" ,"utf-8");
        String  stringStr = java.net.URLDecoder.decode(urlEncoderStr,"utf-8");


        // 字符串格式化拼接     %s 字符串   %b 布尔   %d 整数   %f 浮点数   %tx 日期与时间类型
        String format = String.format("hello,%s%s%s", "word", ",", "测试");
        System.out.println(format);


        // json 字段映射
        // List<ExBlackInfo> exBlackInfos = JSONArray.parseArray(jsonObject.getString("data"), ExBlackInfo.class);





//        String path = "/api/v1/contract/open";
//        boolean matches = path.matches("(.*)/api/v1/contract(.*)");
//        System.out.println(matches);
//
//        String Str = "www.runoob.com";
//        System.out.println("返回值：" + Str.matches("(.*)runoob(.*)"));
//        System.out.println("返回值：" + Str.matches("(.*)google(.*)"));
//        System.out.println("返回值：" + Str.matches("www(.*)"));


        // 不要在foreach里用remove操作，remove元素使用Iterator迭代的方式，如果并发操作需要对Iterator对象加锁
        ArrayList<String> myList = new ArrayList<>();
        myList.add("1");
        myList.add("2");

//        Iterator<String> iterator = myList.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            if ("2".equals(next)) {
//                iterator.remove();
//            }
//        }
//        for (String my : myList) {
//            if ("2".equals(my)) {
//                myList.remove(my);
//            }
//        }


        Map<String, String> map2 = new HashMap<>();
        map2.put("aaa", "123");
//        stringStringHashMap.put("examId", "123");
        String value = (String) map2.get("examId");
        System.out.println(value);

        // 长度小于6,用0补全
        String s = String.format("%06d", 12845);


//        Stream.of("one", "two", "three", "four")
//                .filter(e -> e.length() > 3)
//                .peek(e -> System.out.println("Filtered value: " + e))
//                .map(String::toUpperCase)
//                .peek(e -> System.out.println("Mapped value: " + e))
//                .collect(Collectors.toList());


        List<String> testList=new ArrayList<>(Arrays.asList("1", "2", "3", "10"));
        List<String> oneList=new ArrayList<>(Arrays.asList("1", "2", "3", "10", "20", "30"));
        // 交集
        testList.retainAll(oneList);
        System.out.println(testList); //[1,2,10]
        // 差集
        testList.removeAll(oneList);
        System.out.println(testList); //[3]
        // 并集
        testList.removeAll(oneList);
        testList.addAll(oneList);
        System.out.println(testList); //[3,1,2,10,20,30]


        List<String> aaa = new ArrayList<>(Arrays.asList("a", "a", "a"));
        List<String> bbb = new ArrayList<>(Arrays.asList("b", "b", "b"));
        // subList() 左闭右开，取值
        // list的clear()方法会把sublist截取的集合部分从list中移除掉
        aaa.subList(0, bbb.size()).clear();
        System.out.println(aaa);


        System.out.println(-1 >> 1);

        String aaaaaa = "ABCDEFG";
        System.out.println(aaaaaa.substring(3));
        System.out.println(aaaaaa.substring(3,5));

        System.out.println((20+2*9)/4);
        System.out.println(9%7);
        System.out.println((20+2*9)/4%7);
        System.out.println(11%3);

        int[][] myArray = {{23},{46,20},{98,81,64},{54,43,55,76}};
        int array[] = new int[10];


//        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        String str = bufferedReader.readLine();
//        System.out.println(str);
//
//        System.out.println(str.charAt(1));
//        System.out.println(str.charAt(1) >= '0');

//        String str[] = {"Green", "Red", "Yellow"};
//        String s1 = str[2 % str.length];
//        System.out.println(s1);



    }

    static String ZIP_FILE = "D:\\test\\aaa.zip";
    static String JPG_FILE = "D:\\test\\浦发银行信息科技部测评个人报告样例.docx";
    static String FILE_NAME = "test.docx";

    /**
     * 生成压缩文件
     *
     */
    public static void zipFileBuffer() {
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOut)) {

            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(JPG_FILE))) {
                zipOut.putNextEntry(new ZipEntry(FILE_NAME));
                int temp = 0;
                while
                ((temp = bufferedInputStream.read()) != -1) {
                    bufferedOutputStream.write(temp);
                }
            }

        } catch
        (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 增加小时
     *
     * @param date
     * @param i
     * @return
     */
    public static Date addHourOfDate(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, i);
        return c.getTime();
    }






    /**
     * 过滤特殊字符去除空格
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。 ，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll(" ").trim();
    }

    /**
     * 根据key去重对象
     *
     * @param keyExtractor
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

//    // 求两个对象List的交集
//    private List<People> sameList(List<People> oldArrayList, List<People> newArrayList) {
//        List<People> resultList = newArrayList.stream()
//                .filter(item -> oldArrayList.stream().map(e -> e.getCode())
//                        .collect(Collectors.toList()).contains(item.getCode()))
//                .collect(Collectors.toList());
//        return resultList;
//    }
//
//    // 求两个对象List的差集
//    private List<People> diffList(List<People> firstArrayList, List<People> secondArrayList) {
//        List<People> resultList = firstArrayList.stream()
//                .filter(item -> !secondArrayList.stream().map(e -> e.getCode()).collect(Collectors.toList()).contains(item.getCode()))
//                .collect(Collectors.toList());
//        return resultList;
//    }
//
//    // 求两个对象LIST的差集(多属性比对)
//    private List<People> diffList(List<People> firstArrayList, List<People> secondArrayList) {
//        List<People> resultList = firstArrayList.stream()
//                .filter(item -> !secondArrayList.stream().map(e -> e.getCode() + "&" + e.getName())
//                        .collect(Collectors.toList()).contains(item.getCode() + "&" + item.getName())
//                )
//                .collect(Collectors.toList());
//        return resultList;
//    }


}
