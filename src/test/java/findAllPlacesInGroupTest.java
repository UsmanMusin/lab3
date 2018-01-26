import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class findAllPlacesInGroupTest {
    OktmoReader reader_one = new OktmoReader();
    OktmoData oktmoData_one = new OktmoData();
    OktmoAnalyzer analyzer = new OktmoAnalyzer();

    @Test
    public void findAllPlacesInGroup_test(){
        reader_one.readPlacesRegex("data-201710.csv", oktmoData_one);
        analyzer.findAllPlaceInRegion("Муниципальные образования Республики Башкортостан", oktmoData_one);
    }
}