package resources;

public enum APIResources {
	

	booking("/booking/");
	
	private String resource;
	
	APIResources(String resource)
	{
		this.resource=resource;
	}

	
	public String getResource()
	{
		return resource;
	}
	

}
