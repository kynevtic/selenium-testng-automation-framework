# Selenium Automation Framework

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.41.0-43B02A?style=flat&logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7.12.0-FF6C37?style=flat)
![Maven](https://img.shields.io/badge/Maven-3.9.14-C71A36?style=flat&logo=apachemaven&logoColor=white)

A UI test automation framework built as a portfolio project to demonstrate Selenium WebDriver skills with industry-standard design patterns and tooling. Tests target the [Automation Practice](https://automationpractice.techwithjatin.com/) web application.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21.0.7 (LTS) | Core programming language |
| Selenium WebDriver | 4.41.0 | Browser automation |
| TestNG | 7.12.0 | Test execution, grouping, data providers |
| Maven | 3.9.14 | Build and dependency management |
| Extent Reports | 5.1.2 | HTML test reporting with screenshots |
| Log4j2 | 2.25.3 | Structured logging to console and file |
| Gson | 2.13.2 | JSON configuration and test data parsing |
| OpenCSV | 5.12.0 | CSV test data reading |
| Apache POI | 5.5.1 | Excel (.xlsx) test data reading |
| LambdaTest | Cloud | Remote cross-browser execution |
| Maven Surefire | 3.5.5 | Test runner plugin |

---

## Framework Architecture

### Design Patterns

- **Page Object Model (POM)** — Each page has its own class with locators and actions.
- **ThreadLocal Pattern** — Thread-safe WebDriver and ExtentTest instances for parallel execution.
- **Listener Pattern** — TestNG `ITestListener` for lifecycle hooks, screenshots, and reporting.
- **Singleton-style Logger** — `LoggerUtility` with private constructor and static factory method.
- **Data Provider Pattern** — TestNG `@DataProvider` supplying data from JSON, CSV, and Excel.

### Layered Architecture

```
┌─────────────────────────────────────────────────────┐
│                    TEST LAYER                        │
│         LoginTest (extends TestBase)                 │
│         Assertions · Data Providers · Retry Logic    │
├─────────────────────────────────────────────────────┤
│                  PAGE OBJECT LAYER                   │
│      HomePage → LoginPage → MyAccountPage            │
│      Locators · Page Actions · Navigation            │
├─────────────────────────────────────────────────────┤
│                  UTILITY LAYER                       │
│   BrowserUtility · LoggerUtility · ExtentReporter    │
│   CSVReader · ExcelReader · JSONUtility · Properties │
├─────────────────────────────────────────────────────┤
│               CONFIGURATION LAYER                    │
│   config.json · *.properties · log4j2.xml · Enums   │
├─────────────────────────────────────────────────────┤
│              DATA / INFRASTRUCTURE                   │
│        test-data/ (JSON, CSV, Excel)                 │
│        LambdaTest Cloud · Selenium WebDriver         │
└─────────────────────────────────────────────────────┘
```

---

## Key Features

- **Multi-source data-driven testing** — JSON, CSV, and Excel via dedicated `@DataProvider` methods.
- **Multi-browser support** — Chrome, Firefox, Edge (local and headless).
- **Cloud execution** — LambdaTest integration using `RemoteWebDriver`.
- **Parallel test execution** — TestNG `parallel="methods"` with `ThreadLocal` safety.
- **Automatic screenshot capture** — On test failure, embedded in Extent Report.
- **Retry mechanism** — Configurable retry count for flaky tests via `config.json`.
- **Multi-environment configuration** — `.properties` files (DEV, QA, UAT) and `config.json`.
- **Structured logging** — Console and file output via Log4j2.

---

## Project Structure

```
selenium-automation-framework/
├── pom.xml
├── testng.xml
├── config/
│   ├── config.json
│   ├── DEV.properties
│   ├── QA.properties
│   └── UAT.properties
├── test-data/
│   ├── logindata.json
│   ├── logindata.csv
│   └── loginData.xlsx
├── src/test/java/com/
│   ├── constants/
│   │   ├── Browser.java
│   │   └── Env.java
│   ├── ui/
│   │   ├── pages/
│   │   │   ├── HomePage.java
│   │   │   ├── LoginPage.java
│   │   │   └── MyAccountPage.java
│   │   ├── tests/
│   │   │   ├── TestBase.java
│   │   │   └── LoginTest.java
│   │   ├── listeners/
│   │   │   ├── TestListener.java
│   │   │   └── RetryAnalyzer.java
│   │   └── dataproviders/
│   │       └── LoginDataProvider.java
│   └── utility/
│       ├── BrowserUtility.java
│       ├── LoggerUtility.java
│       ├── ExtentReporterUtility.java
│       ├── CSVReaderUtility.java
│       ├── ExcelReaderUtility.java
│       ├── JSONUtility.java
│       ├── PropertiesUtility.java
│       └── LambdaTestUtility.java
├── logs/
│   └── automation.log
└── reports/
    └── report.html
```

---

## Prerequisites

| Requirement | Version |
|---|---|
| Java JDK | 21 or higher |
| Maven | 3.9+ |
| Browser | Chrome, Firefox, or Edge (latest stable) |

---

## Setup & Execution

```bash
# Clone and install
git clone https://github.com/<your-username>/selenium-automation-framework.git
cd selenium-automation-framework
mvn clean install -DskipTests

# Run with default browser (Chrome, local)
mvn test

# Specify browser
mvn test -Dbrowser=firefox

# Headless execution
mvn test -Dbrowser=chrome -DisHeadless=true

# Cloud execution via LambdaTest
mvn test -Dbrowser=chrome -DisLambdaTest=true
```

---

## Reporting

| Report | Location | Description |
|---|---|---|
| Extent Report | `reports/report.html` | HTML report with pass/fail/skip status, logs, and failure screenshots |
| TestNG Reports | `test-output/` and `target/surefire-reports/` | Default TestNG HTML and XML reports |
| Log File | `logs/automation.log` | Structured execution log with timestamps and thread info |

---

## CI/CD

GitHub Actions workflow for automated test execution:

```yaml
name: Selenium Tests

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up Java 21
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'

      - name: Run tests
        run: mvn clean test -Dbrowser=chrome -DisHeadless=true

      - name: Upload Extent Report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: extent-report
          path: reports/report.html
```

Save this as `.github/workflows/tests.yml` in your repository.

---

## Author

Built as a portfolio project by Venkatesh S.
