package com.register;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.*;

import com.calender.event.CalendarSample;

// Extend HttpServlet class
@SuppressWarnings("serial")
public class Register extends HttpServlet {
//private static final long serialVersionUID = 1L;
public void init() 
{
// Do required initialization
}
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
	try
	{
	CalendarSample calendar= new CalendarSample();
	String	writeString = calendar.getDetails();
	PrintWriter writer = response.getWriter();
	writer.write(writeString);
	writer.close();
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
}
public void destroy()
{
// do nothing.
}
}