package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.TgDAO;
import com.chong.bean.TgBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class TgAjaxServlet extends BaseAjaxServlet
{
    private TgDAO tgDAO = new TgDAO();
    

    
    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp)
    {
        TgBean bean = new TgBean();
        
        List<TgBean> beans = tgDAO.queryList(bean);
        try {
			req.setAttribute("json", JacksonUtil.objToJson(beans));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        
    }
    
    public void add(HttpServletRequest req, HttpServletResponse resp){
    	String objJson = (req.getParameter("bean"));    	
    	TgBean bean;
		try {
			bean = (TgBean)JacksonUtil.jsonToObj(objJson, TgBean.class);
			tgDAO.addBean(bean);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void update(HttpServletRequest req, HttpServletResponse resp){
		try {
//			String objJson = new String(req.getParameter("bean").getBytes("ISO-8859-1"),"utf-8");  
			
			String objJson = (req.getParameter("bean"));    	
			System.out.println("==========   " +objJson );
			TgBean bean;
			bean = (TgBean)JacksonUtil.jsonToObj(objJson, TgBean.class);
			tgDAO.updateBean(bean);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    public void delete(HttpServletRequest req, HttpServletResponse resp){
    	String id = req.getParameter("id");
    	
    	TgBean TgBean = new TgBean();
    	TgBean.setId(id);
    	tgDAO.deleteBean(TgBean);
    }
    
}
