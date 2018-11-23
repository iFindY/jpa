package de.arkadi.persistence.json.producing_json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.arkadi.persistence.json.common.LoanApplication;

import java.io.IOException;

public class ProducingWithBindingApi
{
    public static void main(String[] args) throws IOException
    {
        LoanApplication loanApplication = ExampleLoan.LOAN_APPLICATION;
        System.out.println(loanApplication);
        System.out.println();

        toJsonString(loanApplication);
    }

    private static void toJsonString(final LoanApplication loanApplication) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String jsonString = objectWriter.writeValueAsString(loanApplication);
        System.out.println(jsonString);
    }
}
