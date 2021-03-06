package com.frame.service.base;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.frame.common.exception.AppException;
import com.frame.dao.base.BaseDao;
import com.frame.domain.common.Page;


/**
 * service实现类
 * @author
 * @since 2013-12-30
 * @param <T> 实体
 * @param <KEY> 主键
 */
public abstract class BaseServiceImpl<T, KEY extends Serializable> implements BaseService<T, KEY> {
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

	/**
	 * 获取DAO操作类
	 */
	public abstract BaseDao<T, KEY> getDao();
	
	@Transactional(rollbackFor=Exception.class)
	public int insertEntry(T...t) {
		return getDao().insertEntry(t);
	}
	
	public int insertEntryCreateId(T t) {
	    return getDao().insertEntryCreateId(t);
	}
	
	public int deleteByKey(KEY...key) {
		return getDao().deleteByKey(key);
	}
	
	public int deleteByCondtion(T condition) {
		return getDao().deleteByKey(condition);
	}
	
	public int updateByKey(T condition) {
		return getDao().updateByKey(condition);
	}
	
	@SuppressWarnings("unchecked")
	public int saveOrUpdate(T t) {
		Integer id = 0;
		try {
			Class<?> clz = t.getClass();
			id = (Integer)clz.getMethod("getId").invoke(t);
		} catch (Exception e) {
			LOGGER.warn("获取对象主键值失败!");
		}
		if(id != null && id > 0) {
			return this.updateByKey(t);
		} 
		return this.insertEntry(t);
	}
	
	@SuppressWarnings("unchecked")
	public int save(T t) {
		return this.insertEntry(t);
	}
	
	public int update(T t) {
		return this.updateByKey(t);
	}

	public T selectEntry(KEY key) {
		return getDao().selectEntry(key);
	}
	
	public List<T> selectEntryList(KEY...key) {
		return getDao().selectEntryList(key);
	}
	
	public List<T> selectEntryList(T condition) {
		return getDao().selectEntryList(condition);
	}
	
	public Integer selectEntryListCount(T condition){
		return getDao().selectEntryListCount(condition);
	}
	
	public Page<T> selectPage(T condition, Page<T> page) {
		try {
			Class<?> clz = condition.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condition, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condition, page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}
		Integer size = getDao().selectEntryListCount(condition);
		if(size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(this.selectEntryList(condition));
		return page;
	}
}
