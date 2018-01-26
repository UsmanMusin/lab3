import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EqualsTest {

    OktmoReader reader_one = new OktmoReader();
    OktmoData oktmoData_one = new OktmoData();
    OktmoData oktmoData_second = new OktmoData();

    @Test
    public void test_equal(){
        reader_one.readPlaces("data-201710.csv", oktmoData_one);
        reader_one.readPlacesRegex("data-201710.csv", oktmoData_second);
        assertTrue(OktmoData.equalLists(oktmoData_one, oktmoData_second));
    }



}