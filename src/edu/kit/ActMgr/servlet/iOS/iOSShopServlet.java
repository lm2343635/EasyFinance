package edu.kit.ActMgr.servlet.iOS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import edu.kit.ActMgr.bean.ShopBean;
import edu.kit.ActMgr.data.iOS.iOSShopData;
import edu.kit.ActMgr.domain.AccountBook;
import edu.kit.ActMgr.domain.Shop;
import edu.kit.ActMgr.domain.User;
import edu.kit.ActMgr.service.util.DaoManager;
import edu.kit.common.hibernate3.support.SystemInit;

@WebServlet("/iOSShopServlet")
public class iOSShopServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DaoManager dao;
	private String task;
       
    public iOSShopServlet() 
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//在doGet中调用doPost，提供测试使用
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		task=request.getParameter("task");
		dao=DaoManager.get(getServletContext());
		switch (task)
		{
		case "getShops":
			getShops(request,response);
			break;
		case "push":
			push(request,response);
			break;
		case "update":
			update(request,response);
			break;
		default:
			break;
		}
	}

	private void getShops(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int abid=Integer.parseInt(request.getParameter("abid"));
		AccountBook accountBook=dao.getAccountBookDao().get(abid);
		List<ShopBean> shops=new ArrayList<ShopBean>();
		for(Shop shop:dao.getShopDao().findByAccountBook(accountBook))
		{
			shop.setSync(SystemInit.SYNCED);
			dao.getShopDao().update(shop);
			shops.add(new ShopBean(shop));
		}
		JSONArray jsonArray=JSONArray.fromObject(shops);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());			
	}

	@SuppressWarnings("unchecked")
	private void push(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String arrayString=request.getParameter("array");
		if(iOSSynchronizeServlet.DEBUG==true)
			System.out.println("Get shop message from client: "+arrayString);
		JSONArray jsonArray=JSONArray.fromObject(arrayString);
		List<iOSShopData> datas=JSONArray.toList(jsonArray,new iOSShopData(),new JsonConfig());
		for(iOSShopData data: datas)
		{
			Shop shop=dao.getShopDao().get(data.getSid());
			//如果服务中不存在iOS客户端中传过来的实体，就要在服务器中新建该实体
			if(shop==null)
			{
				shop=new Shop();
				shop.setSname(data.getSname());
				shop.setSin(data.getSin());
				shop.setSout(data.getSout());
				shop.setSicon(dao.getIconDao().get(data.getIid()));
				shop.setAccountBook(dao.getAccountBookDao().get(data.getAbid()));
				//设置同步属性为已同步
				shop.setSync(SystemInit.SYNCED);
				int sid=dao.getShopDao().save(shop);
				data.setSync(SystemInit.SYNCED);
				data.setSid(sid);
			}
			else
			{
				
			}
			jsonArray=JSONArray.fromObject(datas);
			response.setContentType("text/json");
			response.getWriter().print(jsonArray.toString());
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int uid=Integer.parseInt(request.getParameter("uid"));
		User user=dao.getUserDao().get(uid);
		List<iOSShopData> shopDatas=new ArrayList<iOSShopData>();
		for(Shop shop: dao.getShopDao().findNotSyncByUser(user))
		{
			shop.setSync(SystemInit.SYNCED);
			dao.getShopDao().update(shop);
			shopDatas.add(new iOSShopData(shop));
		}
		JSONArray jsonArray=JSONArray.fromObject(shopDatas);
		response.setContentType("text/json");
		response.getWriter().print(jsonArray.toString());
	}
}
