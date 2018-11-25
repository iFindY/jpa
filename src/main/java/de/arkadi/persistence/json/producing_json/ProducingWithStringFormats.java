package de.arkadi.persistence.json.producing_json;


import de.arkadi.persistence.json.common.Job;
import de.arkadi.persistence.json.common.LoanApplication;
import de.arkadi.persistence.json.common.LoanDetails;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * See also {@link #toJsonString(LoanApplication)}.
 * See also {@link MyOtherClass#myMethod(String)}.
 * See also {@link com.mypackage.YetAnotherClass#myMethod(String)}.
 * See also this {@linkplain #myMethod(String) implementation}.
 * See also {@link <a href="http://google.com">link lable </a>}
 * {@link #toJsonString(List)} (int, int) getComponentAt}
 */
public class ProducingWithStringFormats {
    public static void main(String[] args) {
        LoanApplication loanApplication = ExampleLoan.LOAN_APPLICATION;
        System.out.println(loanApplication);
        System.out.println();
        System.out.println(toJsonString(loanApplication));
    }

    private static CharSequence toJsonString(final LoanApplication loanApplication) {
        return String.format(
                "{\n" +
                        "  \"name\": \"%s\",\n" +
                        "  \"purposeOfLoan\": \"%s\",\n" +
                        "  \"loanDetails\": %s,\n" +
                        "  \"jobs\": %s\n" +
                        "}\n",
                loanApplication.getName(),
                loanApplication.getPurposeOfLoan(),
                toJsonString(loanApplication.getLoanDetails()),
                toJsonString(loanApplication.getJobs()));
    }

    private static CharSequence toJsonString(final List<Job> jobs) {
        return jobs
                .stream()
                .map(job -> String.format(
                        "    {\n" +
                                "      \"title\": \"%s\",\n" +
                                "      \"annualIncome\": \"%g\",\n" +
                                "      \"yearsActive\": \"%d\"\n" +
                                "    }",
                        job.getTitle(),
                        job.getAnnualIncome(),
                        job.getYearsActive()))
                .collect(joining(",\n", "[\n", "\n  ]"));
    }

    private static CharSequence toJsonString(final LoanDetails loanDetails) {
        return String.format(
                "{\n" +
                        "    \"amount\": %g,\n" +
                        "    \"startDate\": \"%s\",\n" +
                        "    \"endDate\": \"%s\"\n" +
                        "  }",
                loanDetails.getAmount(),
                loanDetails.getStartDate(),
                loanDetails.getEndDate());
    }
}
