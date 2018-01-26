package com.test.trainticket.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class LeftTicketModel {


    /**
     * data : {"flag":"1","map":{"BJQ":"深圳东","GZG":"赣州","SZQ":"深圳"},
     * "result":["R03IdHeQ7WP9U80CrfywrWGYoTFoitDwzFxCoghhHpYJp4RYyqa/MCEDd/JE8OiPaODaFstwlrEk
     * \t\t\tVUpOuxXC7dDRowgDm2PtAoeN91SH2 / l8RwQIu8JM5P8MXp / V / JGZxgXx2PX6JI2XAgE8RmLc5AJY \t\t\tdoF6hUccGvxBRJ7 / 9
     * cjvsdpUnuKlqHVMVUAg / KFi220U + i2obZozZGIzQVIobIOnjD5ht1AJXcJN \t\t\txO5tfu9TJHUYPi926YALgvoB3x70gmOGqQ == | 预订 |
     * 240000 Z1070C | Z107 | BXP | SZQ | GZG | SZQ | 12: 34 | 18: 11 | 05: 37 | Y | CnlPzDLx8H8fo3zku2tAWPHJNAFAgs4DYADHS1P /
     * BjcnKqXOuHJpMCGxvEU = | 20180125 | 3 | P3 | 08 | 10 | 0 | 0 || || 无 || | 有 || 有 | 有 || || | 10401030 | 1413 | 0 ",
     * "gt+2ClyEre+kBe3xjAK4Iw36u2cfSoTS/8Sk4vVeuX/ImsJaA/cOUxVjmrhA9IJcv6Kixp/1Pfzz
     * \t\t\tW7iVIkRDzkyQkPXkvl8b15Cn1WHfeufvHjf9jRHGT996uOvZDLev4JOuUaoKfLnG2L6EIMVXjTzS
     * \t\t\tpynSFGJiufCXYjPtXxFR6DbUbJqygYOtOCnU8 + WkY5MLpTJeKqYISGTZs + RFGp3it6cRiTYH5hDW \t\t\twhzqOI +
     * FLJhEYQf5iOQYi3kI9E20ZTO475Qz6Gk = | 预订 | 76000 K10940A | K1091 | ICW | BJQ | GZG | BJQ | 14: 41 | 21: 34 | 06: 53 | Y |
     * gQ5 / CF9G9iDciSE1uJYOqp5U + SklTt6nYO50Sr0 / OGHZ7xCa0BWj29ZlCVc = | 20180125 | 3 | W2 | 21 | 26 | 0 | 0 || || 有 || | 有
     * || 有 | 有 || || | 10401030 | 1413 | 1 "]}
     * httpstatus : 200
     * messages :
     * status : true
     */

    public LeftTicketModel() {
        super();
    }


    private DataBean data;
    private int httpstatus;
    private String messages;
    private boolean status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getHttpstatus() {
        return httpstatus;
    }

    public void setHttpstatus(int httpstatus) {
        this.httpstatus = httpstatus;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * flag : 1
         * map : {"BJQ":"深圳东","GZG":"赣州","SZQ":"深圳"}
         * result : ["R03IdHeQ7WP9U80CrfywrWGYoTFoitDwzFxCoghhHpYJp4RYyqa/MCEDd/JE8OiPaODaFstwlrEk
         * \t\t\tVUpOuxXC7dDRowgDm2PtAoeN91SH2 / l8RwQIu8JM5P8MXp / V / JGZxgXx2PX6JI2XAgE8RmLc5AJY \t\t\tdoF6hUccGvxBRJ7 / 9
         * cjvsdpUnuKlqHVMVUAg / KFi220U + i2obZozZGIzQVIobIOnjD5ht1AJXcJN \t\t\txO5tfu9TJHUYPi926YALgvoB3x70gmOGqQ == | 预订 |
         * 240000 Z1070C | Z107 | BXP | SZQ | GZG | SZQ | 12: 34 | 18: 11 | 05: 37 | Y |
         * CnlPzDLx8H8fo3zku2tAWPHJNAFAgs4DYADHS1P / BjcnKqXOuHJpMCGxvEU = | 20180125 | 3 | P3 | 08 | 10 | 0 | 0 || || 无 || | 有
         * || 有 | 有 || || | 10401030 | 1413 | 0 ","gt+2ClyEre+kBe3xjAK4Iw36u2cfSoTS/8Sk4vVeuX/ImsJaA/cOUxVjmrhA9IJcv6Kixp/1Pfzz
         * \t\t\tW7iVIkRDzkyQkPXkvl8b15Cn1WHfeufvHjf9jRHGT996uOvZDLev4JOuUaoKfLnG2L6EIMVXjTzS
         * \t\t\tpynSFGJiufCXYjPtXxFR6DbUbJqygYOtOCnU8 + WkY5MLpTJeKqYISGTZs + RFGp3it6cRiTYH5hDW \t\t\twhzqOI +
         * FLJhEYQf5iOQYi3kI9E20ZTO475Qz6Gk = | 预订 | 76000 K10940A | K1091 | ICW | BJQ | GZG | BJQ | 14: 41 | 21: 34 | 06: 53 |
         * Y | gQ5 / CF9G9iDciSE1uJYOqp5U + SklTt6nYO50Sr0 / OGHZ7xCa0BWj29ZlCVc = | 20180125 | 3 | W2 | 21 | 26 | 0 | 0 || ||
         * 有 || | 有 || 有 | 有 || || | 10401030 | 1413 | 1 "]
         */

        public DataBean() {
            super();
        }

        private String flag;
        private HashMap<String,String> map;
        private List<String> result;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public HashMap<String,String> getMap() {
            return map;
        }

        public void setMap(HashMap<String,String> map) {
            this.map = map;
        }

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }


    }
}
