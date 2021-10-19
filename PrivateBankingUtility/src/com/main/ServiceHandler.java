package com.main;


import com.utils.ConstantsI;
import com.utils.LoadProp;

import java.time.LocalDateTime;

public class ServiceHandler extends Thread implements ConstantsI {


    public void run() {
        System.out.println("Utility started Date: " + LocalDateTime.now());
        try {
        while (true) {
            if (LoadProp.runUtility.equalsIgnoreCase(start))
                new Process();
            else
                System.out.println("Utility not processing right now - Date: " + LocalDateTime.now());

                Thread.sleep(Long.parseLong(LoadProp.sleepDuration));
             }
         }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
