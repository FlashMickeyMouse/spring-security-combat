package cn.org.yxzb.camemt.service;

import cn.org.yxzb.camemt.mapper.TsUserMapper;
import cn.org.yxzb.camemt.mapper.dto.UserRolesPermissions;
import cn.org.yxzb.camemt.pojo.TsUser;
import cn.org.yxzb.camemt.service.common.BaseServiceImpl;
import cn.org.yxzb.camemt.utils.PageResult;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class TsUserService extends BaseServiceImpl<TsUserMapper, TsUser, Example> {




    public TsUser getUserByUsername(String username) {
        Example example = new Example(TsUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        TsUser user = selectOneByExample(example);
        return user;
    }

    public List<String> getAuthorities(String id) {
        List<UserRolesPermissions> list = mapper.getUserRolesPermissions(id);
        ArrayList<String> ps = new ArrayList<>(list.size());
        for (UserRolesPermissions urp : list) {
            ps.add(urp.getExpression());
        }
        return ps;
    }

    public PageResult selectList(int pageNum, int pageSize, Example example) {
        PageResult result = selectOnePage(pageNum, pageSize, example);
        return result;
    }
}
