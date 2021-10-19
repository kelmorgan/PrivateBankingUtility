package com.api.generateXml;

public class RequestXml {

    public static String freezeAccountXml(String accountNo){
        return "<executeFinacleScriptRequest><ExecuteFinacleScriptInputVO><requestId>FIacctFrezCard.scr</requestId></ExecuteFinacleScriptInputVO><executeFinacleScript_CustomData><AcctNo>"+accountNo+"</AcctNo><freezReason>CPC</freezReason><freezeRemarks>Awaiting Deferred Documents</freezeRemarks></executeFinacleScript_CustomData></executeFinacleScriptRequest>";
    }
}
