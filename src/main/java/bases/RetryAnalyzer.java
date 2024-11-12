package bases;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 1;
    private static final int maxRetryCount = 3; // Số lần thử lại tối đa

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true; // Thử lại
        }
        return false; // Không thử lại nữa
    }
}

