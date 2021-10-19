package com.utils;

public class Query {

    public static String getAccountsToProcess (){
        return "SELECT D.WI_NAME as winame , E.ACC_NO as accountNo, to_char(E.DEF_EXPIRY_DAYS, 'YYYY-MM-DD') as expiryDate, ACC_UNFREEZE_STATUS as unfreezeStatus  FROM FBN_PB_DOC_DETAIL_DATA D, EXT_PBAO_FBN E " +
                "WHERE D.IS_DEFERRAL = 'true' and (D.DEF_ISATTACHED is null or D.DEF_ISATTACHED = 'false') " +
                "AND E.CURR_WS = 'Activation' " +
                "AND (E.ACC_UNFREEZE_STATUS is null or ACC_UNFREEZE_STATUS <> 'FREEZED') " +
                "AND D.WI_NAME = E.WI_NAME";
    }
}
