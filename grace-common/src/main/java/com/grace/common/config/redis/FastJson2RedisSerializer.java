//package com.grace.common.config.redis;
//
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONReader;
//import com.alibaba.fastjson2.JSONWriter;
//import com.alibaba.fastjson2.filter.Filter;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.type.TypeFactory;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.SerializationException;
//
//import java.nio.charset.Charset;
//
//
///**
// * fastjson2的redis序列化器
// *
// * @author youzhengjie
// * @date 2023-06-16 00:08:14
// */
//public class FastJson2RedisSerializer<T> implements RedisSerializer<T>
//{
//
//    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
//
//    private Class<T> clazz;
//
//    /**
//     * 解决com.alibaba.fastjson2.JSONException: autoType is not support. xxx类的bug
//     * 只需要将xxx类全部加到下面的autoTypeFilter中即可（记得写全路径类名）
//     */
//    static final Filter autoTypeFilter = JSONReader.autoTypeFilter(
//            // 将所有出现autoType is not support的类都配置到这里
//            "org.springframework.security.oauth2.server.authorization.OAuth2Authorization",
//            "org.springframework.security.authentication.UsernamePasswordAuthenticationToken",
//            "org.springframework.security.core.authority.SimpleGrantedAuthority"
//
//    );
//    static {
//        //开启安全模式
//        System.setProperty("fastjson2.parser.safeMode", "true");
//    }
//    public FastJson2RedisSerializer(Class<T> clazz) {
//        super();
//        this.clazz = clazz;
//    }
//
//    @Override
//    public byte[] serialize(T t) throws SerializationException
//    {
//        if (t == null)
//        {
//            return new byte[0];
//        }
//        return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
//    }
//
//    @Override
//    public T deserialize(byte[] bytes) throws SerializationException
//    {
//        if (bytes == null || bytes.length <= 0)
//        {
//            return null;
//        }
//        String str = new String(bytes, DEFAULT_CHARSET);
//
//        return JSON.parseObject(str, clazz, autoTypeFilter, JSONReader.Feature.SupportAutoType);
//    }
//
//    protected JavaType getJavaType(Class<?> clazz)
//    {
//        return TypeFactory.defaultInstance().constructType(clazz);
//    }
//
//
//}