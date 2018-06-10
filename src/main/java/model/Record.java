package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import utils.LocalDateType;
import utils.LocalTimeType;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

@DatabaseTable(tableName = "Record")
public class Record extends BaseDaoEnabled<Record, Integer> {

    @DatabaseField(generatedId = true)
    private int ID_record;
    @DatabaseField(persisterClass = LocalDateType.class)
    private LocalDate date;
    @DatabaseField
    private int code;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Resource resource;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Assembly assembly;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime startTime;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime endTime;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Area area;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Service service;
    @DatabaseField
    private String address;
    @DatabaseField
    private int assistance_h;
    @DatabaseField
    private int assistance_m;
    @DatabaseField
    private int evacuated_h;
    @DatabaseField
    private int evacuated_m;
    @DatabaseField
    private String registry;
    @DatabaseField
    private String notes;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Operative operative;

    public Record() {
    }

    public Record(LocalDate date, int code, Resource resource, Assembly assembly, LocalTime startTime, LocalTime endTime,
                  Area area, Service service, String address, String assistance_h, String assistance_m,
                  String evacuated_h, String evacuated_m, String registry, String notes, Operative operative) {
        this.date = date;
        this.code = code;
        this.resource = resource;
        this.assembly = assembly;
        this.startTime = startTime;
        this.endTime = endTime;
        this.area = area;
        this.service = service;
        this.address = address;
        this.assistance_h = Integer.parseInt(assistance_h);
        this.assistance_m = Integer.parseInt(assistance_m);
        this.evacuated_h = Integer.parseInt(evacuated_h);
        this.evacuated_m = Integer.parseInt(evacuated_m);
        this.registry = registry;
        this.notes = notes;
        this.operative = operative;
    }

    public int getID_record() {
        return ID_record;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = Utils.emptyStringToNull(address);
    }

    public int getAssistance_h() {
        return assistance_h;
    }

    public int getAssistance_m() {
        return assistance_m;
    }

    public int getEvacuated_h() {
        return evacuated_h;
    }

    public int getEvacuated_m() {
        return evacuated_m;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = Utils.emptyStringToNull(registry);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = Utils.emptyStringToNull(notes);
    }

    public Operative getOperative() {
        return operative;
    }

    public void setOperative(Operative operative) {
        this.operative = operative;
    }

    public void setID(int ID_record) {
        this.ID_record = ID_record;
    }

    public void setAssistanceH(String assistance_h) {
        this.assistance_h = Integer.parseInt(assistance_h);
    }

    public void setAssistanceM(String assistance_m) {
        this.assistance_m = Integer.parseInt(assistance_m);
    }

    public void setEvacuatedH(String evacuated_h) {
        this.evacuated_h = Integer.parseInt(evacuated_h);
    }

    public void setEvacuatedM(String evacuated_m) {
        this.evacuated_m = Integer.parseInt(evacuated_m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return ID_record == record.ID_record;
    }

    @Override
    public int hashCode() {
        return ID_record;
    }

    @Override
    public String toString() {
        return "Record{" +
                "ID_record=" + ID_record +
                ", date='" + date + '\'' +
                ", code=" + code +
                ", resource=" + resource +
                ", assembly=" + assembly +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", area=" + area +
                ", service=" + service +
                ", address='" + address + '\'' +
                ", assistance_h=" + assistance_h +
                ", assistance_m=" + assistance_m +
                ", evacuated_h=" + evacuated_h +
                ", evacuated_m=" + evacuated_m +
                ", registry='" + registry + '\'' +
                ", notes='" + notes + '\'' +
                ", operative=" + operative +
                '}';
    }

    public Object[] getInfo() {
        Object[] info = new Object[17];
        try {
            info[0] = this.getID_record() + "";
        } catch (Exception e) {
            info[0] = "-";
        }
        try {
            info[1] = this.getDate().toString() + "";
        } catch (Exception e) {
            info[1] = "-";
        }
        try {
            info[2] = this.getCode() + "";
        } catch (Exception e) {
            info[2] = "-";
        }
        try {
            info[3] = this.getResource().getCode() + "";
        } catch (Exception e) {
            info[3] = "-";
        }
        try {
            info[4] = this.getAssembly().getName() + "";
        } catch (Exception e) {
            info[4] = "-";
        }
        try {
            info[5] = this.getStartTime().toString() + "";
        } catch (Exception e) {
            info[5] = "-";
        }
        try {
            info[6] = this.getEndTime().toString() + "";
        } catch (Exception e) {
            info[6] = "-";
        }
        try {
            info[7] = this.getArea().getName() + "";
        } catch (Exception e) {
            info[7] = "-";
        }
        try {
            info[8] = this.getService().getName() + "";
        } catch (Exception e) {
            info[8] = "-";
        }
        try {
            info[9] = this.getAddress() + "";
        } catch (Exception e) {
            info[9] = "-";
        }
        try {
            info[10] = this.getAssistance_h() + "";
        } catch (Exception e) {
            info[10] = "-";
        }
        try {
            info[11] = this.getAssistance_m() + "";
        } catch (Exception e) {
            info[11] = "-";
        }
        try {
            info[12] = this.getEvacuated_h() + "";
        } catch (Exception e) {
            info[12] = "-";
        }
        try {
            info[13] = this.getEvacuated_m() + "";
        } catch (Exception e) {
            info[13] = "-";
        }
        try {
            info[14] = this.getRegistry() + "";
        } catch (Exception e) {
            info[14] = "-";
        }
        try {
            info[15] = this.getNotes() + "";
        } catch (Exception e) {
            info[15] = "-";
        }
        try {
            info[16] = this.getOperative().getName() + "";
        } catch (Exception e) {
            info[16] = "-";
        }
        return info;
    }
}
