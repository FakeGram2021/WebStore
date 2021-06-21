package tim6.ordering.adapter.http.advice;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return this.violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}
