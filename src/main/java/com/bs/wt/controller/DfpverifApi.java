package com.bs.wt.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.bsfit.dfp.utils.DeviceFingerPrint;
import cn.com.bsfit.dfp.validation.ValidateManager;

@Controller
@RequestMapping("/api")
public class DfpverifApi {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	// 公钥
	static final String PK = "MIGWMA0GCSqGSIb3DQEBAQUAA4GEADCBgAJ5AL2KNNzht9zQVQPiXcpQGC0kd0aMUJscviBkaaGRL36U26r9UvG7yTVSYxLWRN7FU7eYy2J4otWLASk6atN8LEYkd5KQKuCn12Asp4T8JbJHi805sUvjrN5BfjHW+uN104CRwb/bK/2hHCcLmvRl5wu5MCRmbvZ8uwIDAQAB";
	
	@ResponseBody
	@RequestMapping(value="/transform",method = RequestMethod.GET)
    public String transformDfp(@RequestParam(value = "originStr") String originStr) throws IOException {
        // 输出
        log.debug("originStr : {}",originStr);
        if (originStr == null || originStr.isEmpty()) {
            return null;
        }
        String external = originStr; //外码

        DeviceFingerPrint dfp = null;
        if (dfp == null) {
            try {
            	if(originStr.length() < 160){
            		return ValidateManager.decryptDFPInfo(originStr);
            	}else
                dfp=ValidateManager.decryptDFPInfo(external, PK);
            } catch (Exception e) {
               return "";
            }
        }
        return dfp.getInnerCode();
    }

}
