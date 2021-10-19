package com.api.controller;

import com.api.excute.Api;
import com.api.generateXml.RequestXml;
import com.utils.ConstantsI;
import com.utils.LogGen;
import com.utils.XmlParser;
import org.apache.log4j.Logger;

public class Controller implements ConstantsI {
    private Logger logger = LogGen.getLoggerInstance(logName);

    public String freezeAccount (String accountNo){

        String output = Api.executeCall(apiFreezeAccountServiceName, RequestXml.freezeAccountXml(accountNo));
        logger.info("Webservice output: "+ output);

        if (!isEmpty(output)){
            XmlParser xmlParser = new XmlParser(output);

            String status = xmlParser.getValueOf(apiStatus);
            logger.info("Webservice status: "+ status);

            if (isSuccess(status)){
                String freezeStatus = xmlParser.getValueOf(apiStatusFlag);
                logger.info("Webservice freezeStatus: "+ freezeStatus);
                if (isEmpty(freezeStatus))
                    logger.info("account number does not exist to freeze");
                else if (freezeStatus.equalsIgnoreCase("N")) {
                    logger.info("account number already frozen");
                    return apiSuccess;
                }
                else if (freezeStatus.equalsIgnoreCase("Y"))
                    return apiSuccess;
            }
            else if (isFailed(status)){
                String errorCode = xmlParser.getValueOf("ErrorCode");
                String errorDesc = xmlParser.getValueOf("ErrorDesc");
                String errorType = xmlParser.getValueOf("ErrorType");
                logger.info("Webservice failed status:  errorCode: "+ errorCode+ " errorDesc: "+errorDesc+" errorType: "+errorType+"");
                return apiFailed;
            }
            return null;
        }
        else {
            logger.info("No response from api");
            return "No response from api";
        }
    }

    private boolean isSuccess(String data){
        return data.equalsIgnoreCase(apiSuccess);
    }
    private boolean isFailed(String data){
        return data.equalsIgnoreCase(apiFailed) || data.equalsIgnoreCase(apiFailure);
    }
    private boolean isEmpty(String data){
        return data == null || data.isEmpty();
    }
}
