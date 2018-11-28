package com.wang.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Pager<T> {

    private List<T> list; //对象记录结果集
    private int total = 0; // 总记录数
    private int limit = 10; // 每页显示记录数
    private int pages = 1; // 总页数
    private int pageNumber = 1; // 当前页

    private boolean isFirstPage=false;        //是否为第一页
    private boolean isLastPage=false;         //是否为最后一页
    private boolean hasPreviousPage=false;   //是否有前一页
    private boolean hasNextPage=false;       //是否有下一页

    private int navigatePages=8; //导航页码数
    private int[] navigatePageNumbers;  //所有导航页号

    public Pager(){}

    public Pager(int pageNo, int pageSize) {
        this.pageNumber = pageNo;
        this.limit = pageSize;
        judgePageBoudary();
    }

    public Pager(int total, int pageNumber, int limit) {
        init(total, pageNumber, limit);
    }

    private void init(int total, int pageNumber, int limit){
        //设置基本参数
        this.total=total;
        this.limit=limit;
        this.pages=(this.total-1)/this.limit+1;

        //根据输入可能错误的当前号码进行自动纠正
        if(pageNumber<1){
            this.pageNumber=1;
        }else if(pageNumber>this.pages){
            this.pageNumber=this.pages;
        }else{
            this.pageNumber=pageNumber;
        }

        //基本参数设定之后进行导航页面的计算
        calcNavigatePageNumbers();

        //以及页面边界的判定
        judgePageBoudary();
    }

    /**
     * 计算导航页
     */
    private void calcNavigatePageNumbers(){
        //当总页数小于或等于导航页码数时
        if(pages<=navigatePages){
            navigatePageNumbers=new int[pages];
            for(int i=0;i<pages;i++){
                navigatePageNumbers[i]=i+1;
            }
        }else{ //当总页数大于导航页码数时
            navigatePageNumbers=new int[navigatePages];
            int startNum=pageNumber-navigatePages/2;
            int endNum=pageNumber+navigatePages/2;

            if(startNum<1){
                startNum=1;
                //(最前navPageCount页
                for(int i=0;i<navigatePages;i++){
                    navigatePageNumbers[i]=startNum++;
                }
            }else if(endNum>pages){
                endNum=pages;
                //最后navPageCount页
                for(int i=navigatePages-1;i>=0;i--){
                    navigatePageNumbers[i]=endNum--;
                }
            }else{
                //所有中间页
                for(int i=0;i<navigatePages;i++){
                    navigatePageNumbers[i]=startNum++;
                }
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary(){
        isFirstPage = pageNumber == 1;
        isLastPage = pageNumber == pages && pageNumber!=1;
        hasPreviousPage = pageNumber!=1;
        hasNextPage = pageNumber!=pages;
    }


    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 得到当前页的内容
     * @return {List}
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 得到记录总数
     * @return {int}
     */
    public int getTotal() {
        return total;
    }

    /**
     * 得到每页显示多少条记录
     * @return {int}
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 得到页面总数
     * @return {int}
     */
    public int getPages() {
        return pages;
    }

    /**
     * 得到当前页号
     * @return {int}
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 获取 当前页 开始的 记录的 索引 index
     *
     * @return begin index
     */
    public int getBeginCountIndex() {
        return (getPageNumber() - 1) * limit;
    }

    /**
     * 得到所有导航页号
     * @return {int[]}
     */
    public int[] getNavigatePageNumbers() {
        return navigatePageNumbers;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setNavigatePageNumbers(int[] navigatePageNumbers) {
        this.navigatePageNumbers = navigatePageNumbers;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public Map getPagerInfo(Map map)
    {
        map.put("total",this.getTotal());   //总个数
        map.put("pages",this.getPages());  //总页数
        map.put("pageNo",this.getPageNumber()); //当前页
        map.put("perPage",this.getLimit());  //每页数量
    return map;
    }

    /**
     * by zhangyi 2016-12-14
     */
    private boolean entityOrField;
    private String pageStr;

    public boolean isEntityOrField() {
        return entityOrField;
    }

    public void setEntityOrField(boolean entityOrField) {
        this.entityOrField = entityOrField;
    }

    public void setPageStr(String pageStr) {
        this.pageStr = pageStr;
    }

    public String getPageStr() {
        StringBuffer sb = new StringBuffer();
        if (total > 0) {
            sb.append("	<ul>\n");
            if (pageNumber == 1) {
                sb.append("	<li><a>共<font color=red>" + total + "</font>条</a></li>\n");
                sb.append("	<li><input type=\"number\" value=\""+pageNumber+"\" id=\"toGoPage\" style=\"width:50px;text-align:center;float:left\" placeholder=\"页码\"/></li>\n");
                sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">跳转</a></li>\n");
                sb.append("	<li><a>首页</a></li>\n");
                sb.append("	<li><a>上页</a></li>\n");
            } else {
                sb.append("	<li><a>共<font color=red>" + total + "</font>条</a></li>\n");
                sb.append("	<li><input type=\"number\" value=\""+pageNumber+"\" id=\"toGoPage\" style=\"width:50px;text-align:center;float:left\" placeholder=\"页码\"/></li>\n");
                sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">跳转</a></li>\n");
                sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(1)\">首页</a></li>\n");
                sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(" + (pageNumber - 1) + ")\">上页</a></li>\n");
            }
            int showTag = 5;//分页标签显示数量
            int startTag = 1;
            if (pageNumber > showTag) {
                startTag = pageNumber - 1;
            }
            int endTag = startTag + showTag - 1;
            for (int i = startTag; i <= pages && i <= endTag; i++) {
                if (pageNumber == i){
                    sb.append("<li><a><font color='#808080'>" + i + "</font></a></li>\n");
                }
                else{
                    sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(" + i + ")\">" + i + "</a></li>\n");
                }
            }
            if (pageNumber == pages) {
                sb.append("	<li><a>下页</a></li>\n");
                sb.append("	<li><a>尾页</a></li>\n");
            } else {
                sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(" + (pageNumber + 1) + ")\">下页</a></li>\n");
                sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(" + pages + ")\">尾页</a></li>\n");
            }
            sb.append("	<li><a>第" + pageNumber + "页</a></li>\n");
            sb.append("	<li><a>共" + pages + "页</a></li>\n");


            sb.append("	<li><select title='显示条数' id=\"pageLimit\" style=\"width:55px;float:left;\" onchange=\"changeCount(this.value)\">\n");
            sb.append("	<option value='10'>10</option>\n");
            sb.append("	<option value='20'>20</option>\n");
            sb.append("	<option value='30'>30</option>\n");
            sb.append("	<option value='40'>40</option>\n");
            sb.append("	<option value='50'>50</option>\n");
            sb.append("	<option value='60'>60</option>\n");
            sb.append("	<option value='70'>70</option>\n");
            sb.append("	<option value='80'>80</option>\n");
            sb.append("	<option value='90'>90</option>\n");
            sb.append("	<option value='99'>99</option>\n");
            sb.append("	</select>\n");
            sb.append("	</li>\n");


            sb.append("</ul>\n");
            sb.append("<script type=\"text/javascript\">\n");

            //换页函数
            sb.append("function nextPage(page){");
//            sb.append(" top.jzts();");
            sb.append("	if(true && document.forms[0]){\n");
            sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
            sb.append("		if(url.indexOf('?')>-1){url += \"&" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";}\n");
            sb.append("		else{url += \"?" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";}\n");
            sb.append("		url = url + page + \"&" + (entityOrField ? "limit" : "limit") + "=" + limit + "\";\n");
            sb.append("		document.forms[0].action = url;\n");
            sb.append("		document.forms[0].submit();\n");
            sb.append("	}else{\n");
            sb.append("		var url = document.location+'';\n");
            sb.append("		if(url.indexOf('?')>-1){\n");
            sb.append("			if(url.indexOf('pageNumber')>-1){\n");
            sb.append("				var reg = /pageNumber=\\d*/g;\n");
            sb.append("				url = url.replace(reg,'pageNumber=');\n");
            sb.append("			}else{\n");
            sb.append("				url += \"&" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";\n");
            sb.append("			}\n");
            sb.append("		}else{url += \"?" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";}\n");
            sb.append("		url = url + page + \"&" + (entityOrField ? "limit" : "limit") + "=" + limit + "\";\n");
            sb.append("		document.location = url;\n");
            sb.append("	}\n");
            sb.append("}\n");

            //调整每页显示条数
            sb.append("function changeCount(value){");
//            sb.append(" top.jzts();");
            sb.append("	if(true && document.forms[0]){\n");
            sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
            sb.append("		if(url.indexOf('?')>-1){url += \"&" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";}\n");
            sb.append("		else{url += \"?" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";}\n");
            sb.append("		url = url + \"1&" + (entityOrField ? "limit" : "limit") + "=\"+value;\n");
            sb.append("		document.forms[0].action = url;\n");
            sb.append("		document.forms[0].submit();\n");
            sb.append("	}else{\n");
            sb.append("		var url = document.location+'';\n");
            sb.append("		if(url.indexOf('?')>-1){\n");
            sb.append("			if(url.indexOf('pageNumber')>-1){\n");
            sb.append("				var reg = /pageNumber=\\d*/g;\n");
            sb.append("				url = url.replace(reg,'pageNumber=');\n");
            sb.append("			}else{\n");
            sb.append("				url += \"1&" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";\n");
            sb.append("			}\n");
            sb.append("		}else{url += \"?" + (entityOrField ? "pageNumber" : "pageNumber") + "=\";}\n");
            sb.append("		url = url + \"&" + (entityOrField ? "limit" : "limit") + "=\"+value;\n");
            sb.append("		document.location = url;\n");
            sb.append("	}\n");
            sb.append("}\n");

            //跳转函数
            sb.append("function toTZ(){");
            sb.append("var toPaggeVlue = document.getElementById(\"toGoPage\").value;");
            sb.append("if(toPaggeVlue == ''){document.getElementById(\"toGoPage\").value=1;return;}");
            sb.append("if(isNaN(Number(toPaggeVlue))){document.getElementById(\"toGoPage\").value=1;return;}");
            sb.append("nextPage(toPaggeVlue);");
            sb.append("}\n");
            sb.append("</script>\n");
        }
        pageStr = sb.toString();
        return pageStr;
    }

    /**
     * 截取分页
     * @param lists
     * @return
     */
    public void interceptPage(List<T> lists){
        init(lists.size(),this.pageNumber,this.limit);
        List<T> list = new ArrayList<T>();
        int minIndex = limit*pageNumber-limit;
        int maxIndex = limit*pageNumber;
        int index = lists.size() - 1 < maxIndex ? lists.size(): maxIndex;
        for(int i = minIndex; i < index; i++){
            list.add(lists.get(i));
        }
        setList(list);
    }
}
