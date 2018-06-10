
import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.DAO;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tests {

    @Test
    @DisplayName("Add Hospital")
    void addHospital() throws SQLException {
        Hospital hos = new Hospital("H.Tests", "Hospital Tests", null);
        int size_before = DAO.hospital.queryBuilder().query().size();
        DAO.hospital.create(hos);
        int size_after = DAO.hospital.queryBuilder().query().size();
        Hospital hos_aux = DAO.hospital.queryBuilder().orderBy("ID_hospital", false).queryForFirst();
        DAO.hospital.delete(hos);
        assertEquals(hos, hos_aux);
        assertEquals(size_before + 1, size_after);
    }

    @Test
    @DisplayName("Add Recursos")
    void addResource() throws SQLException {
        Resource recurso = new Resource("Ambulancia", "A.0.1", null);
        int size_before = DAO.resource.queryBuilder().query().size();
        DAO.resource.create(recurso);
        int size_after = DAO.resource.queryBuilder().query().size();
        Resource res_aux = DAO.resource.queryBuilder().orderBy("ID_resource", false).queryForFirst();
        DAO.resource.delete(recurso);
        assertEquals(recurso, res_aux);
        assertEquals(size_before + 1, size_after);
    }

    @Test
    @DisplayName("Add Area")
    void addArea() throws SQLException {
        Area area = new Area("Nombre_area", "nom_area");
        int size_before = DAO.area.queryBuilder().query().size();
        DAO.area.create(area);
        int size_after = DAO.area.queryBuilder().query().size();
        Area area_aux = DAO.area.queryBuilder().orderBy("ID_area", false).queryForFirst();
        DAO.area.delete(area);
        assertEquals(area, area_aux);
        assertEquals(size_before + 1, size_after);
    }

    @Test
    @DisplayName("Add Asamblea")
    void addAsamblea() throws SQLException {
        Assembly asam = new Assembly("nombre_asamblea", "N.A");
        int size_before = DAO.assembly.queryBuilder().query().size();
        DAO.assembly.create(asam);
        int size_after = DAO.assembly.queryBuilder().query().size();
        Assembly ass_aux = DAO.assembly.queryBuilder().orderBy("ID_assembly", false).queryForFirst();
        DAO.assembly.delete(asam);
        assertEquals(asam, ass_aux);
        assertEquals(size_before + 1, size_after);
    }

    @Test
    @DisplayName("Add Operative")
    void addOperative() throws SQLException {
        Operative op = new Operative("op", "today");
        int size_before = DAO.operatives.queryBuilder().query().size();
        DAO.operatives.create(op);
        int size_after = DAO.operatives.queryBuilder().query().size();
        Operative op_aux = DAO.operatives.queryBuilder().orderBy("ID_operative", false).queryForFirst();
        DAO.operatives.delete(op);
        assertEquals(op, op_aux);
        assertEquals(size_before + 1, size_after);
    }

    @Test
    @DisplayName("Add Service")
    void addService() throws SQLException {
        Service servicio = new Service("servicio", null);
        int size_before = DAO.services.queryBuilder().query().size();
        DAO.services.create(servicio);
        int size_after = DAO.services.queryBuilder().query().size();
        Service serv_aux = DAO.services.queryBuilder().orderBy("ID_service", false).queryForFirst();
        DAO.services.delete(servicio);
        assertEquals(servicio, serv_aux);
        assertEquals(size_before + 1, size_after);
    }

    @Test
    @DisplayName("Add User")
    void addUser() throws SQLException {
        User usuario = new User("user", "usr", "elio");
        int size_before = DAO.users.queryBuilder().query().size();
        DAO.users.create(usuario);
        int size_after = DAO.users.queryBuilder().query().size();
        User usr_aux = DAO.users.queryBuilder().orderBy("ID_user", false).queryForFirst();
        DAO.users.delete(usuario);
        assertEquals(usuario, usr_aux);
        assertEquals(size_before + 1, size_after);
    }

}
