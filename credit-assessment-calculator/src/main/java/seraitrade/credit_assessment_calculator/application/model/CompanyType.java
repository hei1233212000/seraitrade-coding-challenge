package seraitrade.credit_assessment_calculator.application.model;

import java.util.Arrays;

public enum CompanyType {
    SOLE_PROPRIETORSHIP(CompanyType.READABLE_VALUE_SOLE_PROPRIETORSHIP),
    LIMITED_LIABILITY_COMPANY(CompanyType.READABLE_VALUE_LIMITED_LIABILITY_COMPANY),
    PARTNERSHIP(CompanyType.READABLE_VALUE_PARTNERSHIP),
    OTHERS(CompanyType.READABLE_VALUE_OTHERS);

    public static final String READABLE_VALUE_SOLE_PROPRIETORSHIP = "Sole Proprietorship";
    public static final String READABLE_VALUE_LIMITED_LIABILITY_COMPANY = "Limited Liability Company";
    public static final String READABLE_VALUE_PARTNERSHIP = "Partnership";
    public static final String READABLE_VALUE_OTHERS = "Others";

    private final String readableName;

    CompanyType(String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName() {
        return readableName;
    }

    public static CompanyType fromReadableName(String readableName) {
        return Arrays.stream(CompanyType.values())
                .filter(companyType -> companyType.readableName.equals(readableName))
                .findAny()
                .orElseThrow();
    }
}
