package com.is.mpg.vo;

public class MyService extends CommonVo {
	
	private Integer serviceId	= null;
	private String title		= null;
	private String description	= null;
	private String sliderImage	= null;
	private String iconImage	= null;
	private String pageName		= null;

	public MyService() {
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSliderImage() {
		return sliderImage;
	}

	public void setSliderImage(String sliderImage) {
		this.sliderImage = sliderImage;
	}
	
	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}
	
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId
				+ ", title=" + title
				+ ", description=" + description
				+ ", sliderImage=" + sliderImage
				+ ", iconImage=" + iconImage
				+ ", pageName=" + pageName
				+ ", registeredDate=" + getRegisteredDate()
				+ ", modifiedDate=" + getModifiedDate()
				+ "]";
	}
}
