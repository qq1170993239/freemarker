package com.lix.test.lambda;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * lambda表达式的基本操作
 */
public class Lambda1 {


    public static void main(String[] args) {
        Stream<String> strs = Stream.of("abc", "abcd", "abcde", "efg", "cd", "aaaaaa", "cba", "dc", "bbbbbb", "eee");
        // filter传入为一个boolean值，为true的会被收集
//        List<String> collect = strs.filter(e -> e.length() > 3).collect(Collectors.toList());
//        System.out.println(collect);

        // map传入一个提供一个操作后的返回值
//        List<String> collect = strs.map(String::toUpperCase).collect(Collectors.toList());
//        System.out.println(collect);

        // collectingAndThen两个参数，第一个是对Collectors做的操作，第二个是第一个参数返回值需要做的操作
//        String xxx = strs.map(String::toUpperCase).collect(Collectors.collectingAndThen(Collectors.joining(";"), e -> e.concat("xxx")));
//        System.out.println(xxx);

        // 先对集合中的元素进行映射，然后再对映射的结果使用Collectors操作;有点像先map之后再做其他操作
//        String collect = strs.collect(Collectors.mapping(x -> x.toUpperCase(), Collectors.joining("-")));
//        System.out.println(collect);

        // groupingBy第一个参数按照什么分组（案例中为字符串长度），第二个参数是分组后的每组的操作
//        Map<Integer, String> collect = strs.collect(Collectors.groupingBy(String::length, Collectors.joining("+")));
//        System.out.println(collect);

        // joining的三个参数，第一个中间连接，第二个为开头，第三个为结尾
//        String collect = strs.collect(Collectors.joining("*", "+", "-"));
//        System.out.println(collect);

        // 第一个参数相当于给一个初始值，第二个参数相当于累积操作，最终结果为“+++ABCABCDABCDEEFGCDAAAAAACBADCBBBBBBeee”
        String reduce = strs.reduce("+++", (a, b) -> a.toUpperCase() + b);
        System.out.println(reduce);
    }

}
