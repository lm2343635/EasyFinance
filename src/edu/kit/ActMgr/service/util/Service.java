package edu.kit.ActMgr.service.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.kit.ActMgr.service.AccountBookManager;
import edu.kit.ActMgr.service.AccountHistoryManager;
import edu.kit.ActMgr.service.AccountManager;
import edu.kit.ActMgr.service.ClassificationManager;
import edu.kit.ActMgr.service.ExchangeRateManager;
import edu.kit.ActMgr.service.PhotoManager;
import edu.kit.ActMgr.service.ReportManager;
import edu.kit.ActMgr.service.SetManager;
import edu.kit.ActMgr.service.ShopManager;
import edu.kit.ActMgr.service.TallyManager;
import edu.kit.ActMgr.service.UserManager;

public class Service 
{
	private AccountBookManager accountBookManager;
	private AccountHistoryManager accountHistoryManager;
	private AccountManager accountManager;
	private ClassificationManager classificationManager;
	private ExchangeRateManager exchangeRateManager;
	private PhotoManager photoManager;
	private ReportManager reportManager;
	private SetManager setManager;
	private ShopManager shopManager;
	private TallyManager tallyManager;
	private UserManager userManager;
	
	public AccountBookManager getAccountBookManager() {
		return accountBookManager;
	}

	public AccountHistoryManager getAccountHistoryManager() {
		return accountHistoryManager;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public ClassificationManager getClassificationManager() {
		return classificationManager;
	}

	public ExchangeRateManager getExchangeRateManager() {
		return exchangeRateManager;
	}

	public PhotoManager getPhotoManager() {
		return photoManager;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public SetManager getSetManager() {
		return setManager;
	}

	public ShopManager getShopManager() {
		return shopManager;
	}

	public TallyManager getTallyManager() {
		return tallyManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setAccountBookManager(AccountBookManager accountBookManager) {
		this.accountBookManager = accountBookManager;
	}

	public void setAccountHistoryManager(AccountHistoryManager accountHistoryManager) {
		this.accountHistoryManager = accountHistoryManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public void setClassificationManager(ClassificationManager classificationManager) {
		this.classificationManager = classificationManager;
	}

	public void setExchangeRateManager(ExchangeRateManager exchangeRateManager) {
		this.exchangeRateManager = exchangeRateManager;
	}

	public void setPhotoManager(PhotoManager photoManager) {
		this.photoManager = photoManager;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public void setSetManager(SetManager setManager) {
		this.setManager = setManager;
	}

	public void setShopManager(ShopManager shopManager) {
		this.shopManager = shopManager;
	}

	public void setTallyManager(TallyManager tallyManager) {
		this.tallyManager = tallyManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public static Service get(ServletContext servletContext)
	{
		ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return (Service)ctx.getBean("service");
	}
}
