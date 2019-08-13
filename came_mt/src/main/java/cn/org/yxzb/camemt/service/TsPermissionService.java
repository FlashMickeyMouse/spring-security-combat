package cn.org.yxzb.camemt.service;

import cn.org.yxzb.camemt.mapper.TsPermissionMapper;
import cn.org.yxzb.camemt.pojo.TsPermission;
import cn.org.yxzb.camemt.service.common.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class TsPermissionService extends BaseServiceImpl<TsPermissionMapper, TsPermission, Example> {
}
