package cn.org.yxzb.camemt.mapper;

import cn.org.yxzb.camemt.mapper.dto.UserRolesPermissions;
import cn.org.yxzb.camemt.pojo.TsUser;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TsUserMapper extends Mapper<TsUser> {

    @Select("SELECT\n" +
            "\t`user`.id,\n" +
            "\t`user`.`username`,\n" +
            "\t`role`.`role_name` AS roleName,\n" +
            "\t`permission`.`name` AS permissionName,\n" +
            "\t`permission`.expression \n" +
            "FROM\n" +
            "\t`ts_user` AS `user`\n" +
            "\tLEFT JOIN ts_user_role AS user_role ON `user`.id = user_role.user_id\n" +
            "\tLEFT JOIN ts_role AS role ON user_role.role_id = role.id\n" +
            "\tLEFT JOIN ts_role_permission AS role_permission ON role_permission.role_id = role.id\n" +
            "\tLEFT JOIN ts_permission AS permission ON permission.id = role_permission.permission_id \n" +
            "WHERE\n" +
            "\t`user`.`id` = #{id, jdbcType=VARCHAR} ")
    List<UserRolesPermissions> getUserRolesPermissions(String id);
}