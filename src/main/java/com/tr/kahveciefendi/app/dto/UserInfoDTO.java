package com.tr.kahveciefendi.app.dto;

/**
 *
 * JSON-serializable DTO containing user data
 *
 */
public class UserInfoDTO {
	
	private Long id;
    private String userName;
    
    public UserInfoDTO(){
    	
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserInfoDTO(String userName, Long id) {
        this.userName = userName;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
