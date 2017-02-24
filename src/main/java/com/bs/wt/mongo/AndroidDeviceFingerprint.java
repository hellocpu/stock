package com.bs.wt.mongo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @version 3.3.5
 * @author karl
 * @modifier karl
 * @describe add two new elemenets phoneNum and ipinfo
 */

@Document(collection = "T_DEVICE_FP_ANDROID")
public class AndroidDeviceFingerprint implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4262326396357561684L;

    @Id
    private String id;

    private String partnerCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

  
}
