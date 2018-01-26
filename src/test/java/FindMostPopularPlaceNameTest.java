import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindMostPopularPlaceNameTest {
    OktmoReader reader_one = new OktmoReader();
    OktmoData oktmoData_one = new OktmoData();
    OktmoAnalyzer analyzer = new OktmoAnalyzer();

    @Test
    public void findMostPopularPlaceName_test(){
        reader_one.readPlacesRegex("data-201710.csv", oktmoData_one);
        //     analyzer.findMostPopularPlaceName("Муниципальные образования Республики Башкортостан", oktmoData_one);
             analyzer.findMostPopularPlaceName("Муниципальные образования Чувашской Республики - Чувашии", oktmoData_one);
    }
}