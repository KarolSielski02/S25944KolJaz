package pl.pjatk.Model;

import java.util.List;

public class NbpResponse {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

    public static class Rate {
        private String no;
        private String effectiveDate;
        private double mid;

        public Rate(String no, String effectiveDate, long mid) {
            this.no = no;
            this.effectiveDate = effectiveDate;
            this.mid = mid;
        }

        public Rate() {
        }

        public String getNo() {
            return no;
        }

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public double getMid() {
            return mid;
        }

        @Override
        public String toString() {
            return "Rate{" +
                    "no='" + no + '\'' +
                    ", effectiveDate='" + effectiveDate + '\'' +
                    ", mid=" + mid +
                    '}';
        }
    }

    public NbpResponse(String table, String currency, String code, List<Rate> rates) {
        this.table = table;
        this.currency = currency;
        this.code = code;
        this.rates = rates;
    }

    public NbpResponse() {
    }

    public String getTable() {
        return table;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public List<Rate> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "NbpResponse{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}
