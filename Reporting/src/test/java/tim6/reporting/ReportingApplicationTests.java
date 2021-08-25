package tim6.reporting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tim6.reporting.reports.MostSoldReportTest;
import tim6.reporting.reports.MostProfitReportTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MostSoldReportTest.class,
        MostProfitReportTest.class
})
class ReportingApplicationTests {

}
