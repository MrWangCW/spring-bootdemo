package com.wang.service.nettyservice.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.wang.service.nettyservice.serialize.Serializer;
import com.wang.service.nettyservice.serialize.SerializerAlogrithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
