import model.*;
import service.DAO;
import utils.Utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PerformanceTest {

    private static String loremIpsum = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer " +
            "took a galley of type and scrambled it to make a type specimen book. It has survived not only five" +
            " centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was " +
            "popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more " +
            "recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    public static void main(String[] args) throws SQLException {
        int total = 0;
        List<Assembly> assemblies = DAO.assembly.queryBuilder().query();
        List<Resource> resources = DAO.resource.queryBuilder().query();
        List<Area> areas = DAO.area.queryBuilder().query();
        List<Operative> operatives = DAO.operatives.queryBuilder().query();
        List<Hospital> hospitals = DAO.hospital.queryBuilder().query();
        List<String> keys = new ArrayList<>(Arrays.asList("CLAVE 0", "CLAVE 1.1", "CLAVE 1.2", "CLAVE 1.3", "CLAVE 2",
                "CLAVE 3", "CLAVE 5", "CLAVE 6"));


        for (int i = 0; i < 2000; i++) {
            Record record = new Record();
            record.setDate(LocalDate.of(2018, random(1, 13), random(1, 32)));
            record.setResource(resources.get(random(0, resources.size())));
            record.setAssembly(assemblies.get(random(0, assemblies.size())));
            record.setStartTime(LocalTime.of(random(0, 24), random(0, 60)));
            record.setEndTime(LocalTime.of(random(0, 24), random(0, 60)));
            record.setAddress(randomString());
            record.setRegistry(randomString());
            record.setNotes(randomString());

            Area area = areas.get(random(0, areas.size()));
            record.setArea(area);
            List<Service> services = DAO.services.queryBuilder()
                    .where().eq("area_id", area.getID()).query();
            record.setService(services.get(random(0, services.size())));

            if (area.getShortName().equals("S.P.T")) {
                for (int j = 0; j < 6; j++) {
                    Event event = new Event();
                    event.setSubcode(j);
                    event.setKey(keys.get(random(0, keys.size())));
                    // TODO horas mayores que las anteriores
                    event.setStartTimeAssistance(LocalTime.of(random(0, 24), random(0, 60)));
                    event.setEndTimeAssistance(LocalTime.of(random(0, 24), random(0, 60)));
                    event.setTransferTimeAssistance(LocalTime.of(random(0, 24), random(0, 60)));
                }
            }

            record.setAssistanceH(Integer.toString(random(0, 13)));
            record.setAssistanceM(Integer.toString(random(0, 13)));
            record.setEvacuatedH(Integer.toString(random(0, 13)));
            record.setEvacuatedM(Integer.toString(random(0, 13)));
            try {
                DAO.record.create(record);
                System.out.println(record.toString());
                total++;
            } catch (SQLException e) {
                System.err.println("Error al añadir.");
            }
        }
    }

    private static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private static String randomString() {
        return loremIpsum.substring(random(0, loremIpsum.length()));
    }
}
