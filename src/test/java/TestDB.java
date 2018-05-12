import model.Applicant;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) throws SQLException {

        ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:db");

        Dao<Applicant, String> accountDao = DaoManager.createDao(connectionSource, Applicant.class);

        Applicant applicant = new Applicant();
        applicant.setName_applicant("rtjer");
        accountDao.create(applicant);

        Applicant applicant1 = accountDao.queryForId(String.valueOf(applicant.getID_applicant()));
        System.out.println(applicant1.getID_applicant());
    }
}
