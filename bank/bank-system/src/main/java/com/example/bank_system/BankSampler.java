package com.example.bank_system;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class BankSampler extends AbstractJavaSamplerClient {

    private BankService service;

    @Override
    public void setupTest(JavaSamplerContext context) {
        BankRepository repo = new BankRepository();
        service = new BankService(repo);
        service.createAccount("A1", 100000.0);
        service.createAccount("A2", 100000.0);
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        String operation = context.getParameter("operation", "deposit");
        SampleResult result = new SampleResult();
        result.sampleStart();
        try {
            switch (operation) {
                case "deposit":
                    service.deposit("A1", 100.0);
                    break;
                case "withdraw":
                    service.withdraw("A1", 100.0);
                    break;
                case "transfer":
                    service.transfer("A1", "A2", 50.0);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operation: " + operation);
            }
            result.setSuccessful(true);
            result.setResponseCodeOK();
            result.setResponseMessage("OK");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setResponseMessage(e.getMessage());
        } finally {
            result.sampleEnd();
        }
        return result;
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = new Arguments();
        args.addArgument("operation", "deposit");
        return args;
    }
}