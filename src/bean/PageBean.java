package bean;

public class PageBean
{
    private int pageSize = 20;
    
    private int startIndex = 1;
    
    private int endIndex = 20;
    
    private int currentPage = 1;
    
    private int totalSize = 0;
    
    private int totalPage = 1;
    
    public PageBean()
    {
        countPageParam();
    }
    
    private void countPageParam()
    {
        //        this.pageSize = _pageSize;
        //        this.currentPage = _currentPage;
        //        this.totalSize = _totalSize;
        
        totalPage = (int) Math.ceil(this.totalSize / this.pageSize);        
        startIndex = pageSize * (currentPage - 1);
        endIndex = pageSize * currentPage;
    }
    
    public int getStartIndex()
    {
        return startIndex;
    }
    
    public int getEndIndex()
    {
        return endIndex;
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
