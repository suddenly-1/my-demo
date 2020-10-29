package com.company;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {

//        输出姓张并且姓名长度为三的的名字
        List<String> lists = new ArrayList<>();
        lists.add("张三丰");
        lists.add("李四");
        lists.add("王五");
        lists.add("张三");
        lists.add("张无忌");

        lists.stream().filter(name->name.startsWith("张")).filter(name->name.length() == 3).forEach(name-> System.out.println(name));
        System.out.println("************");
        Stream<String> stream = lists.stream();
        System.out.println(stream.count());


        System.out.println("************");
        System.out.println("************");
//        通过Arrays中的Stream()获取一个数组流。
        Integer[] index = {1,2,3,2,1};
        Stream<Integer> arr = Arrays.stream(index);
//        通过Stream类中静态方法of()获取一个数组流
//        Stream<String> stream2 = Stream.of("aaa", "bbb", "ccc");

//        遍历
//        arr.forEach(a-> System.out.println(a));
//        判断  数组的元素大于1
//        arr.filter(x->x>1).forEach(x-> System.out.println(x));
//        从索引2开始向前取值（不包含2）
//        arr.limit(2).forEach(a-> System.out.println(a));
//        从索引2开始向后取值（包含2）
//        arr.skip(2).forEach(a-> System.out.println(a));
//        去重    注意：自定义的实体类使用distinct去重时，一定要先重写hashCode()和equals()
//        arr.distinct().forEach(a-> System.out.println(a));

        // 取一个字段去重   不需要hashCode()和equals()
//        List<String> customerNameList = quotaEffectVos.stream().map(HnRtQuotaEffectVo::getCustomerName).distinct().collect(Collectors.toList());

//        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "bbb");
//        list.stream().map(String::toUpperCase).forEach(a-> System.out.println(a));




        List<User> users = Arrays.asList(
                new User(1,"张三", 20, "男", new BigDecimal("111"), new Date()),
                new User(2,"李四", 20, "男", new BigDecimal("111"), new Date()),
                new User(3,"王五", 22, "男", new BigDecimal("111"), new Date()),
                new User(4,"小红", 22, "女", new BigDecimal("111"), new Date())
        );
        users.stream().map(a -> {
            User user = new User();
            return user;
        });
        System.out.println("--------------------------------");

//        转换成Map    HashMap
//        Map<String, String> collect = users.stream().collect(Collectors.toMap(User::getName, User::getAge));
//        System.out.println(collect);
//        生成年龄list
        users.stream().map(User::getAge).collect(Collectors.toList()).forEach(age-> System.out.println(age));
//        生成名字HashSet
//        users.stream().map(User::getName).collect(Collectors.toCollection(HashSet::new)).forEach(name-> System.out.println(name));
//        根据年龄分组
//        Map<String, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getAge));
//        collect.keySet().stream().forEach(a-> System.out.println(a));
//        collect.values().stream().forEach(b-> System.out.println(b));

//        多条件分组
//        Map<String, List<HnRtIndustryFaucet>> map = hnRtIndustryFaucets.stream().collect(Collectors.groupingBy(s -> s.getCompanyName() + '-' + s.getIndustryName()));
        System.out.println("mapMany");
        Map<String, List<User>> mapMany = users.stream().collect(Collectors.groupingBy(a -> a.getAge() + "_" + a.getSex()));
        mapMany.entrySet().stream().forEach(System.out::println);


        System.out.println("*****************");
        HashMap<String, String> strMap1 = new HashMap<>();
        strMap1.put("张三", "20");
        strMap1.put("李四", "21");
        strMap1.put("王五", "22");

        HashMap<String, Map<String,String>> stringHashMap = new HashMap<>();
        stringHashMap.put("aaa", strMap1);
        stringHashMap.values().stream().map(a->a.keySet()).forEach(a-> System.out.println(a));
        stringHashMap.values().stream().map(a->a.values()).forEach(a-> System.out.println(a));


        System.out.println("***********");
        System.out.println("***********");

        System.out.println("".isEmpty());
        System.out.println("1".isEmpty());

        System.out.println("***********");
        System.out.println("***********");

        String[] arrStr = {"a", "b", "c"};
        List<String> strings = Arrays.asList(arrStr);
        System.out.println(strings.isEmpty());



//        HashMap<String, String> map = new HashMap<>();
//        Stream<String> stream = map.keySet().stream();
//
//        Collection<String> values = map.values();
//        Stream<String> stream1 = values.stream();
//
//        Set<Map.Entry<String, String>> entries = map.entrySet();
//        Stream<Map.Entry<String, String>> stream2 = entries.stream();


//        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);
//
//        Integer [] arr = {1,2,3,4,5};
//        String [] strArr = {"a","b","c","d","e"};
//
//        Stream<Integer> arr1 = Stream.of(arr);
//        Stream<String> strArr1 = Stream.of(strArr);


        System.out.println("**************");
        System.out.println("**************");



        List<User> userList = Arrays.asList(
                new User(1,"张三", 20, "男", new BigDecimal("111"), new Date()),
                new User(2,"李四", 20, "男", new BigDecimal("111"), new Date()),
                new User(3,"王五", 22, "男", new BigDecimal("111"), new Date()),
                new User(4,"小红", 22, "女", new BigDecimal("111"), new Date())
        );

        Map<String, String> userMap = userList.stream().collect(Collectors.toMap(User::getName, User::getSex));

        System.out.println(Objects.nonNull(userList));

        userMap.entrySet().forEach(user->System.out.println(user));

        BigDecimal bigDecimal = new BigDecimal(1000000);
        BigDecimal valueThi = new BigDecimal(-1000000);

        //尽量用字符串的形式初始化
        BigDecimal stringFir = new BigDecimal("0.005");
        BigDecimal stringSec = new BigDecimal("1000000");
        BigDecimal stringThi = new BigDecimal("-1000000");

        System.out.println(stringFir.add(stringSec));

        // BigDecimal  lambda求和   BigDecimal.ZERO 从零开始
//        BigDecimal totalAmount = AmountList.stream().map(AmountDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        // BigDecimal  比较大小
        int res = new BigDecimal(111).compareTo(BigDecimal.ZERO);
        if (res == 1) {
            System.out.println("大于0 ");
        }
        if (res == -1) {
            System.out.println("小于0");
        }
        if (res == 0) {
            System.out.println("等于0");
        }

        BigDecimal a = new BigDecimal (111);
        BigDecimal b = new BigDecimal (222);
        //使用compareTo方法比较
        //注意：a、b均不能为null，否则会报空指针
        if(a.compareTo(b) == -1){
            System.out.println("a小于b");
        }
        if(a.compareTo(b) == 0){
            System.out.println("a等于b");
        }
        if(a.compareTo(b) == 1){
            System.out.println("a大于b");
        }
        if(a.compareTo(b) > -1){
            System.out.println("a大于等于b");
        }
        if(a.compareTo(b) < 1){
            System.out.println("a小于等于b");
        }



        System.out.println("******************");
        System.out.println("******************");

        List<User> test = Arrays.asList(
                new User(1,"张三", 20, "男", new BigDecimal("111"), new Date()),
                new User(2,"李四", 20, "男", new BigDecimal("111"), new Date()),
                new User(3,"王五", 22, "男", new BigDecimal("111"), new Date()),
                new User(4,"小红", 22, "女", new BigDecimal("111"), new Date()),
                new User(5,"张三", 20, "男", new BigDecimal("111"), new Date())
        );

        // 计数
//        Map<String, Long> map = test.stream().collect(Collectors.groupingBy(User::getName, Collectors.counting()));
//        map.entrySet().stream().forEach(System.out::println);

//        Map<String, Long> map = test.stream().map(User::getName).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        map.entrySet().stream().forEach(System.out::println);

        // 分组
        Map<Integer, List<User>> collect = test.stream().collect(Collectors.groupingBy(User::getAge));
        collect.entrySet().stream().forEach(System.out::println);
        System.out.println("*************");
        Map<Integer, String> collect1 = collect.get(22).stream().collect(Collectors.toMap(User::getId, User::getName));
        collect1.entrySet().stream().forEach(System.out::println);
        System.out.println("*************");
        Map<Integer, String> collect2 = collect.get(20).stream().collect(Collectors.toMap(User::getId, User::getName));
        collect2.entrySet().stream().forEach(System.out::println);
        System.out.println("*************");

        // 排序
        test.stream().forEach(System.out::println);

        System.out.println("*************");
        // 正序
        List<User> list1 = test.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        list1.stream().forEach(System.out::println);

        // 多条件，先根据name排序，再根据id排序， 从后向前
        List<User> list3 = test.stream().sorted(Comparator.comparing(User::getId)).sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
        list3.stream().forEach(System.out::println);

        System.out.println("*************");
        // 倒序
        List<User> list2 = test.stream().sorted(Comparator.comparing(User::getId).reversed()).collect(Collectors.toList());
        list2.stream().forEach(System.out::println);



        // 获取30分钟后的时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,30);
        Date time = nowTime.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        System.out.println("expiresDate：" + format);



        // Calendar.HOUR_OF_DAY     24小时制
        // Calendar.HOUR            12小时制
        // 获取当天0点时间
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
        cal.set(Calendar.MINUTE, 0);//控制分
        cal.set(Calendar.SECOND, 0);//控制秒
        Date time1 = cal.getTime();
        String format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time1);
        System.out.println("expiresDate：" + format1);


        // 分隔字符串
        System.out.println("*********");
        List<String> resCustomerNameList = new ArrayList<>();
//        String str = "a,b,c";
        String str = "abc";
        String[] strArr = str.split(",");
        System.out.println(strArr.length);
        for (int i = 0; i < strArr.length; ++i){
            resCustomerNameList.add(strArr[i]);
            System.out.println(strArr[i]);
        }


//        Map.getOrDefault(Object key, defaultValue)方法的作用是：
//        当Map集合中有这个key时，就使用这个key值；
//        如果没有就使用默认值defaultValue。

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "小红");
        map.put("age", "18");
        map.put("sex", "女");

        String name = map.getOrDefault("name", "random");
        System.out.println(name);// map的key中存在name,获得name对应的value，小红

        String score = map.getOrDefault("score", "80");
        System.out.println(score);// 80，map的key中不存在score,使用默认值80



    }
}
