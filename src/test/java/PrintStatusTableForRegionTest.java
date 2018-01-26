import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStatusTableForRegionTest {
    OktmoReader reader_one = new OktmoReader();
    OktmoData oktmoData_one = new OktmoData();
    OktmoAnalyzer analyzer = new OktmoAnalyzer();

    @Test
    public void printStatusTableForRegion_test(){
        reader_one.readPlacesRegex("data-201710.csv", oktmoData_one);
        analyzer.printStatusTableForRegion("Муниципальные образования Республики Башкортостан", oktmoData_one);
    }
}