package service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import model.*;

import java.sql.SQLException;

public class DAO {

    public static Dao<Assembly, Integer> assembly;
    public static Dao<Service, Integer> services;
    public static Dao<Area, Integer> area;
    public static Dao<Resource, Integer> resource;
    public static Dao<Record, Integer> record;
    public static Dao<Event, Integer> event;
    public static Dao<Hospital, Integer> hospital;

    static {
        try {
            assembly = DaoManager.createDao(DB.get(), Assembly.class);
            services = DaoManager.createDao(DB.get(), Service.class);
            area = DaoManager.createDao(DB.get(), Area.class);
            resource = DaoManager.createDao(DB.get(), Resource.class);
            record = DaoManager.createDao(DB.get(), Record.class);
            event = DaoManager.createDao(DB.get(), Event.class);
            hospital = DaoManager.createDao(DB.get(), Hospital.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
