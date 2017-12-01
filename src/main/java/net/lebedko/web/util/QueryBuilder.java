package net.lebedko.web.util;

public class QueryBuilder {
    private String base;
    private StringBuilder params;

    private QueryBuilder(String base) {
        this.base = base;
        this.params = new StringBuilder();
    }

    public QueryBuilder addParam(String name, String value) {
        if (params.length() == 0) {
            params.append("?");
        } else {
            params.append("&");
        }
        params.append(name)
                .append("=")
                .append(value);

        return this;
    }

    @Override
    public String toString() {
        return base.concat(params.toString());
    }

    public static QueryBuilder base(String base) {
        return new QueryBuilder(base);
    }
}

