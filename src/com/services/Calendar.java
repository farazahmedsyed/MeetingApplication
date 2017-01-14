package com.services;

import java.io.*;
import javax.servlet.http.*;

import com.calender.event.CalendarSample;

// Extend HttpServlet class
@SuppressWarnings("serial")
public class Calendar extends HttpServlet {
//private static final long serialVersionUID = 1L;
public void init() 
{
// Do required initialization
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
	try
	{
	CalendarSample calendar= new CalendarSample();
	String	writeString = "";
	String op = request.getParameter("op");
	switch (op)
	{
	case ("reg"):
		writeString = calendar.register();
	    break;
	case ("addEvent"):
		writeString = calendar.addEvent();
	    break;
	default:
		writeString="Operation Not Recognized";
	}
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