package service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import model.*;

import java.sql.SQLException;

public class DAO {

    public static Dao<Assembly, Integer> assembly;
    public static Dao<Applicant, Integer> applicant;
    public static Dao<Service, Integer> services;
    public static Dao<Area, Integer> area;
    public static Dao<Resource, Integer> resource;
    public static Dao<Record, Integer> record;
    public static Dao<Event, Integer> event;

    static {
        try {
            assembly = DaoManager.createDao(DataBase.get(), Assembly.class);
            applicant = DaoManager.createDao(DataBase.get(), Applicant.class);
            services = DaoManager.createDao(DataBase.get(), Service.class);
            area = DaoManager.createDao(DataBase.get(), Area.class);
            resource = DaoManager.createDao(DataBase.get(), Resource.class);
            record = DaoManager.createDao(DataBase.get(), Record.class);
            event = DaoManager.createDao(DataBase.get(), Event.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
