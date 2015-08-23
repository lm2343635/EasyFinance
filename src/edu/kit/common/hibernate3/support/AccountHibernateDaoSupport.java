package edu.kit.common.hibernate3.support;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AccountHibernateDaoSupport extends HibernateDaoSupport
{
	/**
	 * 使用hql语句进行分页查询
	 * @param hql 需要查询的hql语句
	 * @param offset 第一条记录索引
	 * @param pageSize 每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql,final int offset,final int pageSize)
	{
		List list=getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException 
			{
				List result=session.createQuery(hql)
						.setFirstResult(offset)
						.setMaxResults(pageSize)
						.list();
				return result;
			}
			
		});
		return list;
	}
	
	/**
	 * 使用hql语句进行分页查询
	 * @param hql 需要查询的hql语句
	 * @param value 如果hql有一个参数需要传入，value就是传入hql语句的参数
	 * @param offset 第一条记录索引
	 * @param pageSize 每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql , final Object value ,final int offset, final int pageSize)
	{
		//通过一个HibernateCallback对象来执行查询
		List list = getHibernateTemplate().executeFind(new HibernateCallback()
		{
			//实现HibernateCallback接口必须实现的方法
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				//执行Hibernate分页查询
				List result = session.createQuery(hql)
					//为hql语句传入参数
					.setParameter(0, value) 
					.setFirstResult(offset)
					.setMaxResults(pageSize)
					.list();
				return result;
			}
		});
		return list;
	}

	/**
	 * 使用hql语句进行分页查询
	 * @param hql 需要查询的hql语句
	 * @param values 如果hql有多个个参数需要传入，values就是传入hql的参数数组
	 * @param offset 第一条记录索引
	 * @param pageSize 每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize)
	{
		//通过一个HibernateCallback对象来执行查询
		List list = getHibernateTemplate().executeFind(new HibernateCallback()
		{
			//实现HibernateCallback接口必须实现的方法
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				//执行Hibernate分页查询
				Query query = session.createQuery(hql);
				//为hql语句传入参数
				for (int i = 0 ; i < values.length ; i++)
				{
					query.setParameter( i, values[i]);
				}
				List result = query.setFirstResult(offset)
					.setMaxResults(pageSize)
					.list();
				return result;
			}
		});
		return list;
	}
}
