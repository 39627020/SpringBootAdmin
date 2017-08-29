/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.controller.console;

import com.geekcattle.model.console.Admin;
import com.geekcattle.model.console.Channel;
import com.geekcattle.service.console.ChannelService;
import com.geekcattle.util.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import java.util.List;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:58
 */
@Controller
@RequestMapping("/console/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/channel/index";
    }

    @RequestMapping(value = "/from", method = {RequestMethod.GET})
    public String from(Channel channel, Model model) {
    		String id = channel.getCid();
    		Channel chan = new Channel();
    		if(!StringUtils.isEmpty(id)) {
    			chan = channelService.getById(id);
    		}
    		model.addAttribute("channel", chan);
    		return "console/channel/from";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Channel channel) {
        ModelMap map = new ModelMap();
        List<Channel> Lists = channelService.getPageList(channel);
        map.put("pageInfo", new PageInfo<Channel>(Lists));
        map.put("queryParam", channel);
        return ReturnUtil.Success("加载成功", map, null);
    }


    @Transactional
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap save(@Valid Channel channel, BindingResult result) {
        try {
            if (result.hasErrors()) {
                for (ObjectError er : result.getAllErrors())
                    return ReturnUtil.Error(er.getDefaultMessage(), null, null);
            }
            if (StringUtils.isEmpty(channel.getCid())) {
                Example example = new Example(Admin.class);
                example.createCriteria().andCondition("no = ", channel.getNo());
                Integer userCount = channelService.getCount(example);
                if (userCount > 0) {
                    return ReturnUtil.Error("渠道号已存在", null, null);
                }
                String Id = UuidUtil.getUUID();
                channel.setCid(Id);
                channel.setCreatedAt(DateUtil.getCurrentTime());
                channel.setUpdatedAt(DateUtil.getCurrentTime());
                channelService.insert(channel);
            } else {
                channel.setUpdatedAt(DateUtil.getCurrentTime());
                channelService.save(channel);
            }
            return ReturnUtil.Success("操作成功", null, "/console/channel/index");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(String[] ids) {
        try {
            if (ids != null) {
                if (StringUtils.isNotBlank(ids.toString())) {
                    for (String id : ids) {
                        channelService.deleteById(id);
                    }
                }
                return ReturnUtil.Success("删除成功", null, null);
            } else {
                return ReturnUtil.Error("删除失败", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.Error("删除失败", null, null);
        }
    }

}
