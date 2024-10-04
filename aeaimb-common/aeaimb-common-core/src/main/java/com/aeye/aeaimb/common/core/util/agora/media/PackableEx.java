package com.aeye.aeaimb.common.core.util.agora.media;


public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);

    ByteBuf marshal(ByteBuf out);

}
