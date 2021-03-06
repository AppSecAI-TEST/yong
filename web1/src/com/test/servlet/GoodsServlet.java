package com.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.test.dto.Goods;
import com.test.dto.Page;
import com.test.service.GoodsService;

public class GoodsServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private GoodsService gs = new GoodsService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		request.setCharacterEncoding("UTF-8");
		String resultStr = "";
		doProcess(response,resultStr);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("UTF-8");
		Gson g = new Gson();
		Goods goods = g.fromJson(request.getReader(), Goods.class);
		System.out.println(goods);
		String command = goods.getCommand();
		if(command.equals("list"))
		{
			int totalCnt = gs.getTotalCount(goods);
			Page page = goods.getPage();
			page.setTotalCnt(totalCnt);
			List<Goods> list = gs.selectGoods(goods);
			List vendorList = gs.selectVendor(goods);
			HashMap resultMap = new HashMap();
			resultMap.put("page", page);
			resultMap.put("list", list);
			resultMap.put("vendor", vendorList);
			String jsonStr = g.toJson(resultMap);
			doProcess(response, jsonStr);
		}
	}

	
	public void doProcess(HttpServletResponse resq, String writeStr) throws IOException {
		resq.setContentType("text/html; charset = UTF-8");
		PrintWriter out = resq.getWriter();
		out.print(writeStr);
		
	}
}