package com.main;

import com.api.controller.Controller;
import com.fbn.service.Service;
import com.utils.ConstantsI;
import com.utils.LogGen;
import com.utils.Query;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class Process implements ConstantsI {
    private Service service;
    private Logger logger = LogGen.getLoggerInstance(logName);

    public Process() {
        setDependencies();
        processData();
    }

    private void processData() {
        try {

            Set<Map<String, String>> resultSet = service.getRecords(Query.getAccountsToProcess());
            logger.info("Result Set: " + resultSet);

            for (Map<String, String> result : resultSet) {

                String wiName = result.get("winame".toUpperCase());
                String accountNo = result.get("accountNo".toUpperCase());
                String expiryDate = result.get("expiryDate".toUpperCase());
                String unfreezeStatus = result.get("unfreezeStatus".toUpperCase());

                if (isNotEmpty(expiryDate)) {
                    if (checkAccountToFreeze(expiryDate)) {
                        String freezeAccountResponse = new Controller().freezeAccount(accountNo);
                        if (isSuccess(freezeAccountResponse)) {
                            updateFreezeStatus(wiName);
                            setDecisionHistory(wiName);
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Exception occurred Date: "+ LocalDateTime.now() +"  "+ e.getMessage());
        }
        service.disconnectCabinet();
    }

    private boolean checkAccountToFreeze(String date) {
        logger.info("date to check "+ date);
        LocalDate todayDate = LocalDate.now();
        logger.info("todayDate "+ todayDate);
        LocalDate processDate = LocalDate.parse(date);
        logger.info("processDate "+ processDate);

        return (todayDate.isAfter(processDate) || todayDate.isEqual(processDate));
    }
    private boolean isSuccess(String data){
        return data != null &&  data.equalsIgnoreCase(apiSuccess);
    }

    private void updateFreezeStatus(String wiName){
        String tableName = "EXT_PBAO_FBN";
        String column = "ACC_UNFREEZE_STATUS";
        String value = "'FREEZED'";
        String condition = "WI_NAME = '"+wiName+"'";
        String status = service.updateRecord(tableName,column,value,condition);
        if(status.equalsIgnoreCase(serviceSuccess))
            logger.info("Freeze status updated successfully");

    }

    private void setDecisionHistory(String wiName){
        String tableName = "FBN_PRIVAO_DECISION_HISTORY";
        String columns = "WI_NAME, CURR_WS, ROLE, USER_ID, REMARKS, DATE_TIME";
        String values = "'"+wiName+"', 'Activation', 'System', 'System', 'Account Frozen for Deferred Docs', SYSDATE";
        String status = service.insertRecords(tableName,columns,values);
        if(status.equalsIgnoreCase(serviceSuccess))
            logger.info("Decision History set successfully");
    }

    private boolean isNotEmpty(String data){
        return data != null;
    }
    private void setDependencies(){
        Service.setConfigPath(configPath);
        this.service = new Service(Service.getSessionId());
    }


}
