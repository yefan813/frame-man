package com.frame.dao.base;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

import com.frame.common.exception.AppException;


/**
 * dao实现类
 * @param <T> 实体
 * @param <KEY> 主键
 */
public abstract class BaseDaoImpl<T, KEY extends Serializable> extends MyBatisSupport implements BaseDao<T, KEY> {
	protected static final String DEFAULT_INSERT_KEY = "insertEntry";
	protected static final String DEFAULT_INSERT_LAST_SEQUENCE_KEY = "lastSequence";
	protected static final String DEFAULT_DELETE_ARRAY_KEY = "deleteByArrayKey";
	protected static final String DEFAULT_DELETE_CONDTION = "deleteByCondtion";
	protected static final String DEFAULT_UPDATE_KEY = "updateByKey";
	protected static final String DEFAULT_SELECT_ARRAY_KEY = "selectEntryArray";
	protected static final String DEFAULT_SELECT_CONDTION = "selectEntryList";
	protected static final String DEFAULT_SELECT_CONDTION_COUNT = "selectEntryListCount";
	
	@Resource private SqlSessionTemplate sqlTemplate;
	@Resource private SqlSessionTemplate batchSqlTemplate;
	
	
	@Override
	protected  SqlSessionTemplate getSqlTemplate(boolean batch,boolean readonly){
		if(readonly) {
		}

		if(batch) {
			return batchSqlTemplate;
		}
		return sqlTemplate;
	}
	


	/**
	 * 获取命名空前前缀
	 * @param statement
	 * @return
	 */
	public abstract String getNameSpace(String statement);
	
	public int insertEntry(T...t){
		int result = 0;
		if (t == null || t.length <= 0) {return result;}
		for (T o : t) {
			if(o != null) {
				result += this.insert(getNameSpace(DEFAULT_INSERT_KEY), o);
			}
		}
		return result;
	}
	
	public int insertEntryCreateId(T t) {
		@SuppressWarnings("unchecked")
		int result = this.insertEntry(t);
		if (result > 0) {
			Integer id = (Integer)select(getNameSpace(DEFAULT_INSERT_LAST_SEQUENCE_KEY),null);
			if (id != null && id >0) {
				try {
					Class<?> clz = t.getClass();
					clz.getMethod("setId", Integer.class).invoke(t, id);//最后一次插入编号
				} catch (Exception e) {
					throw new AppException("设置新增主键失败", e);
				}
			}
		}
		return result;
	}
	
	public int deleteByKey(KEY...key) {
		return this.delete(getNameSpace(DEFAULT_DELETE_ARRAY_KEY), key);
	}
	
	public int deleteByKey(T t) {
		return this.delete(getNameSpace(DEFAULT_DELETE_CONDTION), t);
	}
	
	public int updateByKey(T t) {
		return this.update(getNameSpace(DEFAULT_UPDATE_KEY), t);
	}
	
	public T selectEntry(KEY key) {
		@SuppressWarnings("unchecked")
		List<T> list = this.selectEntryList(key);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public List<T> selectEntryList(KEY...key) {
		if (key == null || key.length <= 0) {return null;}
		return this.selectList(getNameSpace(DEFAULT_SELECT_ARRAY_KEY), key);
	}
	
	public List<T> selectEntryList(T t) {
		return this.selectList(getNameSpace(DEFAULT_SELECT_CONDTION), t);
	}
	
	public Integer selectEntryListCount(T t) {
		return this.select(getNameSpace(DEFAULT_SELECT_CONDTION_COUNT), t);
	}
}
