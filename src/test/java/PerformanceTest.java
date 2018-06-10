import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.DAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerformanceTest {

    private static String loremIpsum = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer " +
            "took a galley of type and scrambled it to make a type specimen book. It has survived not only five" +
            " centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was " +
            "popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more " +
            "recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @Test
    @DisplayName("Comprueba la inserción de registros con eventos")
    void addRecords() throws SQLException {
        int total = 0;
        List<Assembly> assemblies = DAO.assembly.queryBuilder().query();
        List<Resource> resources = DAO.resource.queryBuilder().query();
        List<Area> areas = DAO.area.queryBuilder().query();
        List<Hospital> hospitals = DAO.hospital.queryBuilder().query();
        List<String> keys = new ArrayList<>(Arrays.asList("CLAVE 0", "CLAVE 1.1", "CLAVE 1.2", "CLAVE 1.3", "CLAVE 2",
                "CLAVE 3", "CLAVE 5", "CLAVE 6"));

        for (int i = 0; i < 30000; i++) {
            Record record = new Record();
            record.setDate(LocalDate.of(2018, random(1, 13), random(1, 29)));
            record.setAssembly(assemblies.get(random(0, assemblies.size())));
            record.setStartTime(LocalTime.of(random(0, 13), random(0, 60)));
            record.setEndTime(LocalTime.of(random(13, 24), random(0, 60)));
            record.setAddress(randomString());
            record.setRegistry(randomString());
            record.setNotes(randomString());

            Area area = areas.get(random(0, areas.size()));
            record.setArea(area);
            List<Service> services = DAO.services.queryBuilder()
                    .where().eq("area_id", area.getID()).query();
            record.setService(services.get(random(0, services.size())));

            record.setAssistanceH(Integer.toString(random(0, 13)));
            record.setAssistanceM(Integer.toString(random(0, 13)));
            record.setEvacuatedH(Integer.toString(random(0, 13)));
            record.setEvacuatedM(Integer.toString(random(0, 13)));

            DAO.record.create(record);
            record.refresh();
            total++;

            if (area.getShortName().equals("S.P.T") || area.getShortName().equals("S.P.A")) {
                record.setResource(resources.get(random(0, resources.size())));
                DAO.record.update(record);
                for (int j = 0; j < 6; j++) {
                    Event event = new Event();
                    event.setRecord(record);
                    event.setSubcode(j);
                    event.setKey(keys.get(random(0, keys.size())));
                    event.setStartTime(LocalTime.of(random(0, 9), random(0, 60)));
                    event.setEndTime(LocalTime.of(random(17, 24), random(0, 60)));
                    event.setTransferTime(LocalTime.of(random(9, 17), random(0, 60)));
                    event.setHospital(hospitals.get(random(0, hospitals.size())));
                    event.setRegistry(Integer.toString(random(4000000, 99999999)));
                    event.setPathology(randomString());
                    event.setGender(random(1, 3));
                    DAO.event.create(event);
                }
            }
        }

        assertEquals(30000, total);
    }

    private static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private static String randomString() {
        return loremIpsum.substring(random(0, loremIpsum.length()));
    }

}
