package com.example.changshuai.microuniversity.model;



public class Huodong implements java.io.Serializable
{
	public String id;

	public String biaoti ;
	public String neirong ;
	public String shijian ;
 
	public Huodong()
	{
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBiaoti()
	{
		return this.biaoti;
	} 
	public void setBiaoti(String biaoti)
	{
		this.biaoti = biaoti;
	} 
	public String getNeirong()
	{
		return this.neirong;
	} 
	public void setNeirong(String neirong)
	{
		this.neirong = neirong;
	} 
	public String getShijian()
	{
		return this.shijian;
	} 
	public void setShijian(String shijian)
	{
		this.shijian = shijian;
	} 


}