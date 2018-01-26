package com.test.trainticket.model;

/**
 * Created by Administrator on 2018/1/26.
 */

public class TicketCsModel {

    public TicketCsModel() {
        super();
        queryLeftNewDTO = new QueryLeftNewDTOModel();
    }

    public String secretHBStr;
    public String secretStr;
    public String buttonTextInfo;
    public QueryLeftNewDTOModel queryLeftNewDTO;

}
