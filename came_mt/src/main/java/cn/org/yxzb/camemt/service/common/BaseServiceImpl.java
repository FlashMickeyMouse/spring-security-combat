package cn.org.yxzb.camemt.service.common;

import cn.org.yxzb.camemt.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

/**
 * 实现BaseService抽象类
 * @author songhao
 */
public class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example>, ApplicationContextAware {


    private static Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);
    protected Mapper mapper;

    protected ApplicationContext applicationContext;

    /**
     * 让类具有感知到spring上下文的能力
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 获取类泛型class
     *
     * @return
     */
    public Class getMapperClass() {
        Class clazz = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }

    @Override
    @PostConstruct
    public void initMapper() {
        this.mapper = (Mapper) applicationContext.getBean(getMapperClass());
        LOG.info("为公共service类装配mapper-->{}", getMapperClass());
    }


    @Override
    public int selectCountByExample(Example example) {
        try {
            Method method = mapper.getClass().getDeclaredMethod("selectCountByExample", Object.class);
            Object o = method.invoke(mapper, example);
            return (Integer) o;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Record selectOneByExample(Example example) {
        try {
            Method method = mapper.getClass().getDeclaredMethod("selectOneByExample", Object.class);
            Object o = method.invoke(mapper, example);
            return (Record) o;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Record> selectByExample(Example example) {
        try {
            Method method = mapper.getClass().getDeclaredMethod("selectByExample", Object.class);
            Object o = method.invoke(mapper, example);
            return (List<Record>) o;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return Collections.EMPTY_LIST;
    }

    @Override
    public PageResult selectOnePage(int pageNum, int pageSize, Example example) {
        PageHelper.startPage(pageNum, pageSize);
        List<Record> records = selectByExample(example);
        PageInfo<Record> pageInfo = new PageInfo<>(records);
        PageResult<Record> result = new PageResult<>();
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}