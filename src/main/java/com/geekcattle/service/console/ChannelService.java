/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.service.console;

import com.geekcattle.mapper.console.AdminMapper;
import com.geekcattle.mapper.console.ChannelMapper;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.console.Channel;
import com.geekcattle.util.CamelCaseUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Optional;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:43
 */
@Service
public class ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    public List<Channel> getPageList(Channel channel) {
        PageHelper.offsetPage(channel.getOffset(), channel.getLimit(), CamelCaseUtil.toUnderlineName(channel.getSort())+" "+channel.getOrder());
        return channelMapper.selectAll();
    }

    public Integer getCount(Example example){
        return channelMapper.selectCountByExample(example);
    }

    public Channel getById(String id) {
        return channelMapper.selectByPrimaryKey(id);
    }

    public Admin findByNo(String no) {
        return channelMapper.selectByNo(no);
    }

    public void deleteById(String id) {
        channelMapper.deleteByPrimaryKey(id);
    }
    
    public void insert(Channel channel){
    		channelMapper.insert(channel);
    }

    public void save(Channel channel) {
        if (channel.getCid() != null) {
        		channelMapper.updateByPrimaryKey(channel);
        } else {
        		channelMapper.insert(channel);
        }
    }

    public void updateExample(Channel channel, Example example){
    		channelMapper.updateByExampleSelective(channel, example);
    }



}
