package service;

import model.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

public class DAO {

    public static Dao<Assembly, Integer> assemblyDao;
    public static Dao<Applicant, Integer> applicantDao;
    public static Dao<Service, Integer> servicesDao;
    public static Dao<Area, Integer> areaDao;
    public static Dao<Resource, Integer> resourceDao;
    public static Dao<Record, Integer> recordDao;
    public static Dao<Event, Integer> eventDao;

    static {
        try {
            assemblyDao = DaoManager.createDao(DataBase.get(), Assembly.class);
            applicantDao = DaoManager.createDao(DataBase.get(), Applicant.class);
            servicesDao = DaoManager.createDao(DataBase.get(), Service.class);
            areaDao = DaoManager.createDao(DataBase.get(), Area.class);
            resourceDao = DaoManager.createDao(DataBase.get(), Resource.class);
            recordDao = DaoManager.createDao(DataBase.get(), Record.class);
            eventDao = DaoManager.createDao(DataBase.get(), Event.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
