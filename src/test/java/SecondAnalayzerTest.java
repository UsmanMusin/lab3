import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecondAnalayzerTest {

    OktmoReader reader_one = new OktmoReader();
    OktmoData oktmoData_one = new OktmoData();
    OktmoAnalyzer analyzer = new OktmoAnalyzer();

    @Test
    public void test_analayzerSecond(){
        reader_one.readPlaces("data-201710.csv", oktmoData_one);
        analyzer.test_second(oktmoData_one);
    }

}