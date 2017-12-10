package ru.javawebinar.topjava;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static String REPOSITORY_IMPLEMENTATION = JDBC;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    public static final String DATABASE_IMPLEMENTATION=POSTGRES_DB;
    //  Get DB profile depending of DB driver in classpath
    public static String[] getActiveProfiles() {
        String res;
        try {
            Class.forName("org.postgresql.Driver");
            res=POSTGRES_DB;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                res=Profiles.HSQL_DB;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not find DB driver");
            }
        }
        return new String[]{res,REPOSITORY_IMPLEMENTATION};
    }
}
