import model.Record;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestExample {

    @Test
    @DisplayName("Comprueba que no guarda cadenas vacias")
    void myFirstTest() {
        Record record = new Record();
        record.setNotes("");
        //assertEquals(null, record.getNotes());
        assertNull(record.getNotes());
    }

}
