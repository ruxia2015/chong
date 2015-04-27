package com.chong.bean;

public class PageBean
{
    private int pageSize = 20;
    
    private int startLimit = 1;
    
    private int endLimit = 20;
    
    private int currentPage = 1;
    
    private int totalSize = 0;
    
    private int totalPage = 1;
    
    public PageBean()
    {
        countPageParam();
    }
    
    private void countPageParam()
    {

        totalPage = (int) Math.ceil(this.totalSize / this.pageSize);        
        startLimit = pageSize * (currentPage - 1);
        endLimit = pageSize * currentPage;
    }
    
   

    
    public int getStartLimit()
    {
        return startLimit;
    }

    public int getEndLimit()
    {
        return endLimit;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
        countPageParam();
    }
    
    public int getTotalSize()
    {
        return totalSize;
    }
    
    public void setTotalSize(int totalSize)
    {
        this.totalSize = totalSize;
        countPageParam();
    }
    
    public int getTotalPage()
    {
        return totalPage;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
        countPageParam();
        
    }
    
}
