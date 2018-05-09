package Service;

import Model.Applicant;
import Model.Assembly;
import Model.Service;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

public class DAO {

    public static Dao<Assembly, Integer> assemblyDao;
    public static Dao<Applicant, Integer> applicantDao;
    public static Dao<Service, Integer> servicesDao;

    static {
        try {
            assemblyDao = DaoManager.createDao(DataBase.get(), Assembly.class);
            applicantDao = DaoManager.createDao(DataBase.get(), Applicant.class);
            servicesDao = DaoManager.createDao(DataBase.get(), Service.class);
            // TODO para cada modelo
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
