/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.mapper.console;

import com.geekcattle.model.console.Admin;
import com.geekcattle.model.console.Channel;
import com.geekcattle.util.CustomerMapper;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:32
 */
public interface ChannelMapper extends CustomerMapper<Channel> {
    Admin selectByNo(String No);
}
