package cn.org.yxzb.camemt.service.common;

import cn.org.yxzb.camemt.utils.PageResult;

import java.util.List;

/**
 * BaseService接口
 * @author songhao
 */
public interface BaseService<Record, Example> {

    /**
     * 初始化mapper
     */
    void initMapper();


    /**
     * 根据条件查询记录的数量
     * @param example
     * @return
     */
    int selectCountByExample(Example example);


    /**
     * 根据条件查询第一条符合条件的记录
     * @param example
     * @return
     */
    Record selectOneByExample(Example example);

    /**
     * 根据条件查询所有符合条件的记录
     * @param example
     * @return
     */
    List<Record> selectByExample(Example example);


    /**
     * 根据条件查询一页
     * @param pageNum
     * @param pageSize
     * @param example
     * @return
     */
    PageResult selectOnePage(int pageNum, int pageSize, Example example);



}