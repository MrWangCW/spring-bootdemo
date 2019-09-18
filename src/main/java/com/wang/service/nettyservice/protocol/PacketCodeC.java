package com.wang.service.nettyservice.protocol;

import com.wang.service.nettyservice.protocol.command.Command;
import com.wang.service.nettyservice.protocol.request.*;
import com.wang.service.nettyservice.protocol.response.*;
import com.wang.service.nettyservice.serialize.Serializer;
import com.wang.service.nettyservice.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;


public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();
    /**
     * 魔数
     */
    public static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP = new HashMap<>();

    private static final Map<Byte, Serializer> SERIALIZER_MAP = new HashMap<>(26);

    static {
        PACKET_TYPE_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.LIST_GROUP_MEMBERS_REQUEST, GroupMembersRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LIST_GROUP_MEMBERS_RESPONSE, GroupMembersResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.JOIN_QUIT_MESSAGE_RESPONSE, JoinQuitMsgResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        Serializer serializer = new JSONSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlogrithm(), serializer);
    }


    /**
     * 编码数据
     * 魔数 -- 版本号 -- 序列化算法 --   指令 -- 数据长度 --   数据
     * 4字节 -- 1字节  --   1字节    --  1字节 --   4字节  --  N字节
     *
     * @param packet 编码对象
     * @return ByteBuf
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 创建 ByteBuf 对象
//        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 解码
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return SERIALIZER_MAP.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return PACKET_TYPE_MAP.get(command);
    }
}
