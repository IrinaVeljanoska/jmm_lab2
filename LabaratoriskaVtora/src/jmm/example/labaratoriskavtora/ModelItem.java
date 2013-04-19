package jmm.example.labaratoriskavtora;

import java.sql.Date;

public class ModelItem {

	private Long offer_id;
	private String title;
	private String price;
	private Date dueDate;
	
	public ModelItem() 	{	}
	
	public ModelItem(String title, String price, Date dueDate) 
	{
		super();
		this.title = title;
		this.price = price;
		this.dueDate = dueDate;
	}
	
	public Long getId()
	{
		return offer_id;
	}
	
	public void setId(Long offer_id) 
	{
		this.offer_id = offer_id;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getPrice() 
	{
		return price;
	}
		
	public void setPrice(String price) 
	{
		this.price = price;
	}
	
	public Date getDueDate()
	{
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) 
	{
		this.dueDate = dueDate;
	}
}
