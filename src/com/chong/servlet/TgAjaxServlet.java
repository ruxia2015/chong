package com.chong.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chong.DAO.TgDAO;
import com.chong.bean.CategoryBean;
import com.chong.bean.TgBean;
import com.chong.common.base.BaseAjaxServlet;
import com.chong.common.util.JacksonUtil;
import com.chong.common.util.StringTools;
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
        try
        {
            req.setAttribute("json", JacksonUtil.objToJson(beans));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public String add(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        String objJson = (req.getParameter("bean"));
        if (StringTools.isEmptyOrNone(objJson))
        {
            return "无数据，不能添加";
        }
        
        TgBean bean = (TgBean) JacksonUtil.jsonToObj(objJson, TgBean.class);
        
        //判断是否可以添加
        TgBean beanQ = new TgBean();
        beanQ.setDomain(bean.getDomain());
        beanQ = tgDAO.findBean(beanQ);
        if (beanQ != null)
        {
            return "已经存在该推广网站，添加失败";
        }
        
        tgDAO.addBean(bean);
        
        return null;
    }
    
    public void update(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        
        String objJson = (req.getParameter("bean"));
        System.out.println("==========   " + objJson);
        TgBean bean;
        bean = (TgBean) JacksonUtil.jsonToObj(objJson, TgBean.class);
        tgDAO.updateBean(bean);
        
    }
    
    public void delete(HttpServletRequest req, HttpServletResponse resp)
    {
        String id = req.getParameter("id");
        
        TgBean TgBean = new TgBean();
        TgBean.setId(id);
        tgDAO.deleteBean(TgBean);
    }
    
}
