package hudson.plugins.violations.types.resharper;

import static hudson.plugins.violations.ViolationsReportBuilder.violationsReport;
import static hudson.plugins.violations.types.resharper.ReSharperDescriptor.RESHARPER;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class ReSharperParserTest extends JenkinsRule {
    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Test
    public void testThatResharperFileCanBeParsed() throws Exception {
        violationsReport(RESHARPER)
                .reportedIn("**/resharper-report.xml")
                .perform()
                .assertThat("MyLibrary/Class1.cs")
                .wasReported()
                .reportedViolation(0, "Redundancies in Code",
                        "Using directive is not required by the code and can be safely removed")
                .reportedViolation(9, "Common Practices and Code Improvements", "Join declaration and assignment")
                .and()
                .assertThat("MyLibrary/Properties/AssemblyInfo.cs")
                .wasReported()
                .reportedViolation(2, "Redundancies in Code",
                        "Using directive is not required by the code and can be safely removed");
    }
}
