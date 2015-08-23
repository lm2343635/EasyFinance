package edu.kit.ActMgr.service.util;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.dao.AccountBookDao;
import edu.kit.ActMgr.dao.AccountDao;
import edu.kit.ActMgr.dao.AccountHistoryDao;
import edu.kit.ActMgr.dao.ClassificationDao;
import edu.kit.ActMgr.dao.IconDao;
import edu.kit.ActMgr.dao.PhotoDao;
import edu.kit.ActMgr.dao.RecordDao;
import edu.kit.ActMgr.dao.ShopDao;
import edu.kit.ActMgr.dao.SynchronizationHistoryDao;
import edu.kit.ActMgr.dao.TemplateDao;
import edu.kit.ActMgr.dao.TransferDao;
import edu.kit.ActMgr.dao.UserDao;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.servlet.UserServlet;

public class ManagerTemplate 
{
	protected AccountBookDao accountBookDao;
	protected AccountDao accountDao;
	protected AccountHistoryDao accountHistoryDao;
	protected ClassificationDao classificationDao;
	protected IconDao iconDao;
	protected PhotoDao photoDao;
	protected RecordDao recordDao;
	protected ShopDao shopDao;
	protected SynchronizationHistoryDao synchronizationHistoryDao;
	protected TemplateDao templateDao;
	protected TransferDao transferDao;
	protected UserDao userDao;

	public AccountBookDao getAccountBookDao() {
		return accountBookDao;
	}
	public AccountDao getAccountDao() {
		return accountDao;
	}
	public ClassificationDao getClassificationDao() {
		return classificationDao;
	}
	public IconDao getIconDao() {
		return iconDao;
	}
	public PhotoDao getPhotoDao() {
		return photoDao;
	}
	public RecordDao getRecordDao() {
		return recordDao;
	}
	public ShopDao getShopDao() {
		return shopDao;
	}
	public TemplateDao getTemplateDao() {
		return templateDao;
	}
	public TransferDao getTransferDao() {
		return transferDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setAccountBookDao(AccountBookDao accountBookDao) {
		this.accountBookDao = accountBookDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;
	}
	public void setIconDao(IconDao iconDao) {
		this.iconDao = iconDao;
	}
	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}
	public void setRecordDao(RecordDao recordDao) {
		this.recordDao = recordDao;
	}
	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	public void setTransferDao(TransferDao transferDao) {
		this.transferDao = transferDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public AccountHistoryDao getAccountHistoryDao() {
		return accountHistoryDao;
	}
	public void setAccountHistoryDao(AccountHistoryDao accountHistoryDao) {
		this.accountHistoryDao = accountHistoryDao;
	}
	
	public SynchronizationHistoryDao getSynchronizationHistoryDao() {
		return synchronizationHistoryDao;
	}
	public void setSynchronizationHistoryDao(
			SynchronizationHistoryDao synchronizationHistoryDao) {
		this.synchronizationHistoryDao = synchronizationHistoryDao;
	}
	/**
	 * 得到当前正在使用账本
	 * @param session HttpSession
	 * @return 账本
	 */
	public AccountBook getUsingAccountBookFromSession(HttpSession session)
	{
		User user=userDao.get(UserServlet.getUser(session).getUid());
		AccountBook usingAccountBook=user.getUsingAccountBook();
		return usingAccountBook;
	}
}
