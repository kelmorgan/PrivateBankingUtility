package com.api.excute;

import com.socketService.SocketService;
import com.utils.LoadProp;

public class Api {

    public static String executeCall (String serviceName, String request){
        SocketService.setSocketIP(LoadProp.socketServiceIp);
        SocketService.setSocketPort(LoadProp.socketServicePort);
        return  SocketService.executeIntegrationCall(serviceName,request);
    }
}
