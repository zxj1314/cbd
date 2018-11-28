package com.test.cbd.lambda;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LambdaTest {

    public static void main(String[] args) {
        testStream();
        //testFilter();
        //testDistant();
    }

    /**
     *集合流操作
     * @param
     * @return
     * @Author zxj
     */
    public static void testStream(){
        List<Staff> staff = Arrays.asList(
                new Staff("mike", 30, new BigDecimal(10000),true),
                new Staff("jack", 27, new BigDecimal(20000),true),
                new Staff("leo", 33, new BigDecimal(30000),false)
        );

        //遍历操作
        List<StaffPublic> result1 = staff.stream().map(temp -> {
            StaffPublic obj = new StaffPublic();
            obj.setName(temp.getName());
            obj.setAge(temp.getAge());
            if ("mike".equals(temp.getName())) {
                obj.setExtra("this field is for mike only!");
            }
            return obj;
        }).collect(Collectors.toList());
        System.out.println(result1);

        //跳过流的前n个元素
        List<Staff> result2 = staff.stream()
                .skip(2)
                .collect(Collectors.toList());
        System.out.println(result2);

        //获取每个人的姓名(实则是将Perosn类型转换成String类型)
        List<String> result3 = staff.stream()
                .map(Staff::getName)
                .collect(Collectors.toList());
        System.out.println(result3);

        //判断list中是否有男性
        boolean result4 = staff.stream()
                .anyMatch(Staff::getIsMan);
        System.out.println(result4);

        //判断是否所有人都是男性
        boolean result5= staff.stream()
                .allMatch(Staff::getIsMan);
        System.out.println(result5);

        //判断是否所有人都不是男性
        boolean result6= staff.stream()
                .noneMatch(Staff::getIsMan);
        System.out.println(result6);

        //获取第一个元素findFirst,Optional是Java8新加入的一个容器，这个容器只存1个或0个元素，它用于防止出现NullpointException
        Optional<Staff> result7 = staff.stream()
                .findFirst();
        System.out.println(result7);

        //将普通流转换成数值流
        IntStream stream = staff.stream()
                .mapToInt(Staff::getAge);

        //元素求和：使用Integer.sum函数求和
        int count=stream.reduce(0, Integer::sum);
        System.out.println(count);

        //计算最大值
        OptionalInt maxAge = staff.stream()
                .mapToInt(Staff::getAge)
                .max();
        System.out.println(maxAge.getAsInt());
    }

    /**
     * 筛选filter
     * @param
     * @return
     * @Author
     */
    public static void testFilter(){
        List<Staff> staff = Arrays.asList(
                new Staff("mike", 30, new BigDecimal(10000),true),
                new Staff("jack", 27, new BigDecimal(20000),true),
                new Staff("leo", 33, new BigDecimal(30000),false)
        );

        List<Staff> result = staff.stream()
                .filter(Staff::getIsMan)
                .collect(Collectors.toList());

        System.out.println(result);
    }

    /**
     * 去重
     * @param
     * @return
     * @Author
     */
    public static void testDistant(){
        List<Staff> staff = Arrays.asList(
                new Staff("mike", 30, new BigDecimal(10000),true),
                new Staff("mike", 30, new BigDecimal(10000),true),
                new Staff("leo", 33, new BigDecimal(30000),false)
        );

        List<Staff> result = staff.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println(result);
    }

}
