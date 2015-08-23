package edu.kit.common.hibernate3.support;

import java.io.File;
import java.util.Date;

import edu.kit.ActMgr.dao.IconDao;
import edu.kit.ActMgr.domain.Account;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Classification;
import edu.kit.ActMgr.domain.Icon;
import edu.kit.ActMgr.domain.Photo;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.ManagerTemplate;

/**
 * 根据.hbm.xml配置文件创建相应的数据库表
 * @author fgh
 *
 */
public class SystemInit 
{
	private String systemRootPath;
	private ManagerTemplate manager;
	
	public static final int SYS_NULL_ID=1;
	public static final int SYS_RECORD_PHOTO_NULL=2;
	
	public static final int SYNCED=1;
	public static final int NOT_SYNC=0;
	public static final int SYNC_DELETE=-1;
	
	//系统图标分类
	public static int [] IconClass={0,1,2,3,4};
	
	public SystemInit(String systemRootPath,ManagerTemplate manager) 
	{
		super();
		this.systemRootPath = systemRootPath;
		this.manager=manager;
	}
	
	public void createSystemNullItems()
	{
		//新建系统空用户
		User user=new User(SYS_NULL_ID,  "SYS_NULL_USER", "SYS_NULL_EMAIL", "SYS_NULL_PASSWORD", null, null, null, null,null);
		manager.getUserDao().save(user);
		//新建系统空图标，设置系统空图标的用户为系统空用户
		Icon icon=new Icon(SYS_NULL_ID, "SYS_NULL_ICON", -1, user, SYNCED);
		manager.getIconDao().save(icon);
		//新建系统空账本
		AccountBook accountBook=new AccountBook(SYS_NULL_ID, "SYS_NULL_ACCOUNTBOOK", user, icon, null, null, null, null, null, null, SYNCED);
		manager.getAccountBookDao().save(accountBook);
		//设置系统空用户的账本为系统空账本
		user.setUsingAccountBook(accountBook);
		manager.getUserDao().update(user);
		//新建系统空用户照片
		Photo userPhoto=new Photo(SYS_NULL_ID,"SYS_NULL_USER_PHOTO.png" ,"SYS_NULL_USER_PHOTO",new Date(), accountBook, SYNCED);
		manager.getPhotoDao().save(userPhoto);
		//设置系统空用户的照片为系统空照片
		user.setPhoto(userPhoto);
		manager.getUserDao().update(user);
		//新建系统空账本
		Account account=new Account(SYS_NULL_ID, "SYS_NULL_ACCOUNT", 0.0, 0.0, icon, accountBook, SYNCED);
		manager.getAccountDao().save(account);
		//新建系统空类别
		Classification classification=new Classification(SYS_NULL_ID, "SYS_NULL_CLASSIFICATION", 0.0, 0.0, icon, accountBook, SYNCED);
		manager.getClassificationDao().save(classification);
		//新建系统空商家
		Shop shop=new Shop(SYS_NULL_ID, "SYS_NULL_SHOP", 0.0, 0.0, icon, accountBook, SYNCED);
		manager.getShopDao().save(shop);	
		//收支记录空照片
		Photo recordPhoto=new Photo(SYS_RECORD_PHOTO_NULL, "SYS_NULL_RECORD_PHOTO.png", "SYS_NULL_RECORD_PHOTO", new Date(), accountBook, SYNCED);
		manager.getPhotoDao().save(recordPhoto);
	}
	
	public void readSystemIcons()
	{
		IconDao iconDao=manager.getIconDao();
		User system=manager.getUserDao().get(SYS_NULL_ID);
		for(int i:IconClass)
		{
			File dir=new File(systemRootPath+"upload/"+SYS_NULL_ID+"/"+i);
			for(File file:dir.listFiles())
			{
				//文件不为隐藏文件才能导入
				if(!file.isHidden())
				{
					Icon icon=new Icon();
					icon.setIname(file.getName());
					icon.setType(i);
					icon.setUser(system);
					icon.setSync(SYNCED);
					iconDao.save(icon);
				}
			}
		}
	}
}
