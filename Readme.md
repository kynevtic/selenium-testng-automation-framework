# 🚀 Selenium Automation Framework

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.41.0-43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7.12.0-FF6C37?style=for-the-badge&logo=testng&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Log4j2](https://img.shields.io/badge/Log4j2-Logging-FF5722?style=for-the-badge)
![Extent Reports](https://img.shields.io/badge/Extent_Reports-5.1.2-1DBF73?style=for-the-badge)
![LambdaTest](https://img.shields.io/badge/LambdaTest-Cloud-6C47FF?style=for-the-badge)

A **scalable, maintainable, enterprise-grade** UI Test Automation Framework built with **Selenium WebDriver 4**, **Java**, and **TestNG**. Designed with industry best practices including the **Page Object Model**, **data-driven testing** from multiple sources, **parallel execution**, **cloud-based cross-browser testing**, and **comprehensive reporting**.

---

## 📑 Table of Contents

- [Project Overview](#-project-overview)
- [Tech Stack](#-tech-stack)
- [Framework Architecture](#-framework-architecture)
- [Key Features](#-key-features)
- [Project Structure](#-project-structure)
- [Setup Instructions](#-setup-instructions)
- [Test Execution](#-test-execution)
- [Reporting](#-reporting)
- [CI/CD Integration](#-cicd-integration)
- [Best Practices](#-best-practices)
- [Future Enhancements](#-future-enhancements)

---

## 🔍 Project Overview

This framework provides a robust foundation for **UI automation testing** of web applications. It is designed to be:

- **Scalable** — Easily add new pages, tests, and data sources without modifying existing code.
- **Maintainable** — Clean separation of concerns ensures changes in the UI require updates only in page classes.
- **Reliable** — Built-in retry logic, thread-safe parallel execution, and automatic screenshot capture on failure.
- **Flexible** — Run tests locally across Chrome, Firefox, and Edge, headless or headed, or remotely via LambdaTest cloud.

The framework currently automates an e-commerce practice application ([Automation Practice](https://automationpractice.techwithjatin.com/)) and serves as a template for enterprise test automation projects.

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| **Java** | 17+ | Core programming language |
| **Selenium WebDriver** | 4.41.0 | Browser automation engine |
| **TestNG** | 7.12.0 | Test execution, grouping, parameterization, data providers |
| **Maven** | 3.x | Build automation & dependency management |
| **Extent Reports** | 5.1.2 | Rich HTML test reporting with screenshots |
| **Log4j2** | 2.25.3 | Structured logging (console + file) |
| **Gson** | 2.13.2 | JSON configuration & test data parsing |
| **OpenCSV** | 5.12.0 | CSV test data reader |
| **Apache POI** | 5.5.1 | Excel (.xlsx) test data reader |
| **LambdaTest** | Cloud | Remote cross-browser/cross-platform execution |

---

## 🏗 Framework Architecture

### Design Patterns

#### 1. Page Object Model (POM)
Each web page is represented by a dedicated Java class encapsulating its **locators** and **actions**. This decouples test logic from UI structure, making maintenance straightforward when the application UI changes.

```
HomePage  →  LoginPage  →  MyAccountPage
   │              │              │
   └── goToLoginPage()   doLoginWith()   getUserName()
```

All page classes extend `BrowserUtility`, inheriting common interactions (`clickOn`, `enterText`, `getVisibleText`, `takeScreenshot`).

#### 2. ThreadLocal Pattern (Thread-Safe Parallel Execution)
`BrowserUtility`, `ExtentReporterUtility`, and `LambdaTestUtility` all use `ThreadLocal<WebDriver>` / `ThreadLocal<ExtentTest>` to isolate browser instances per thread — enabling safe parallel test execution with up to **8 concurrent threads**.

#### 3. Template Method Pattern
`BrowserUtility` serves as an abstract base class providing a skeleton of browser operations. Page classes inherit and extend it with page-specific behavior, following the template method design approach.

#### 4. Listener Pattern
`TestListener` implements TestNG's `ITestListener` to hook into test lifecycle events — automatically logging results, capturing screenshots on failure, and updating Extent Reports without polluting test code.

#### 5. Data Provider Pattern
TestNG `@DataProvider` methods in `LoginDataProvider` supply test data from **JSON**, **CSV**, and **Excel** sources, cleanly separating test data from test logic.

#### 6. Singleton-Inspired Logger
`LoggerUtility` uses a **private constructor** to prevent instantiation, exposing only a static factory method `getLogger()` — ensuring consistent logger creation across the framework.

### Layered Architecture

```
┌─────────────────────────────────────────────────────┐
│                    TEST LAYER                        │
│         LoginTest (extends TestBase)                 │
│         Assertions · Test Groups · Retry Logic       │
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
│   config.json · *.properties · log4j2.xml · enums   │
├─────────────────────────────────────────────────────┤
│              DATA / INFRASTRUCTURE                   │
│        test-data/ (JSON, CSV, Excel)                 │
│        LambdaTest Cloud · Selenium WebDriver         │
└─────────────────────────────────────────────────────┘
```

---

## ✨ Key Features

### 📊 Multi-Source Data-Driven Testing
Test data is externalized and read from **three formats**:
- **JSON** — `test-data/logindata.json` via Gson deserialization
- **CSV** — `test-data/logindata.csv` via OpenCSV
- **Excel** — `test-data/loginData.xlsx` via Apache POI

Each source has a dedicated `@DataProvider` in `LoginDataProvider`, making it trivial to switch data sources per test.

### 🔁 Retry Mechanism for Flaky Tests
`RetryAnalyzer` implements `IRetryAnalyzer` with a **configurable retry count** read from `config.json`:
```json
"MAXIMUM_NUMBER_OF_ATTEMPTS": 3
```
Failed tests are automatically retried up to the configured limit before being marked as failed.

### 🖥 Multi-Browser & Headless Support
Supports **Chrome**, **Firefox**, and **Edge** with optional headless execution:
```bash
mvn test -Dbrowser=chrome -DisHeadless=true
```

### ☁️ Cloud Execution via LambdaTest
Seamlessly switch between local and cloud execution:
```bash
mvn test -Dbrowser=chrome -DisLambdaTest=true
```
`LambdaTestUtility` handles `RemoteWebDriver` initialization with LambdaTest's Selenium Grid.

### ⚡ Parallel Execution
TestNG is configured with `parallel="methods"` and `thread-count="8"`. Thread safety is guaranteed via `ThreadLocal` across all shared resources (WebDriver, ExtentTest, DesiredCapabilities).

### 📸 Automatic Screenshot on Failure
`TestListener.onTestFailure()` automatically captures a timestamped screenshot and attaches it to the Extent Report for visual debugging.

### 🌍 Multi-Environment Configuration
Environment-specific settings are managed via:
- **Properties files** — `config/DEV.properties`, `config/QA.properties`, `config/UAT.properties`
- **JSON config** — `config/config.json` with structured environment map

Switch environments by updating the `Env` enum reference (e.g., `Env.QA`, `Env.DEV`, `Env.UAT`).

### 📝 Structured Logging
**Log4j2** outputs to both **console** and **file** (`logs/automation.log`) with timestamps, thread names, log levels, and source locations:
```
2026-03-17 10:30:45 [main] INFO com.ui.pages.HomePage Trying to perform click to go to sign in page
```

---

## 📁 Project Structure

```
Selenium-Automation-Framework/
│
├── pom.xml                          # Maven build & dependency configuration
├── testng.xml                       # TestNG suite config (parallel, params)
│
├── config/
│   ├── config.json                  # Multi-environment JSON configuration
│   ├── DEV.properties               # Dev environment properties
│   ├── QA.properties                # QA environment properties
│   └── UAT.properties               # UAT environment properties
│
├── test-data/
│   ├── logindata.json               # Test data (JSON format)
│   └── logindata.csv                # Test data (CSV format)
│
├── src/test/
│   ├── java/com/
│   │   ├── constants/
│   │   │   ├── Browser.java         # Browser enum (CHROME, FIREFOX, EDGE)
│   │   │   └── Env.java             # Environment enum (QA, DEV, UAT)
│   │   │
│   │   ├── ui/
│   │   │   ├── pages/
│   │   │   │   ├── HomePage.java        # Home page actions & locators
│   │   │   │   ├── LoginPage.java       # Login page actions & locators
│   │   │   │   └── MyAccountPage.java   # Account page actions & locators
│   │   │   │
│   │   │   ├── tests/
│   │   │   │   ├── TestBase.java        # Base test class (setup/teardown)
│   │   │   │   └── LoginTest.java       # Login test scenarios
│   │   │   │
│   │   │   ├── listeners/
│   │   │   │   ├── TestListener.java    # TestNG listener (reporting/screenshots)
│   │   │   │   └── RetryAnalyzer.java   # Retry logic for failed tests
│   │   │   │
│   │   │   ├── dataproviders/
│   │   │   │   ├── LoginDataProvider.java  # Data providers (JSON/CSV/Excel)
│   │   │   │   ├── TestData.java           # Test data wrapper POJO
│   │   │   │   └── User.java              # User model POJO
│   │   │   │
│   │   │   └── pojo/
│   │   │       ├── Config.java          # JSON config deserialization model
│   │   │       └── Environment.java     # Environment settings POJO
│   │   │
│   │   └── utility/
│   │       ├── BrowserUtility.java        # Abstract base: browser ops & WebDriver
│   │       ├── CSVReaderUtility.java      # CSV file reader
│   │       ├── ExcelReaderUtility.java    # Excel file reader
│   │       ├── ExtentReporterUtility.java # Extent Reports setup & management
│   │       ├── JSONUtility.java           # JSON config reader
│   │       ├── LambdaTestUtility.java     # LambdaTest cloud driver setup
│   │       ├── LoggerUtility.java         # Log4j2 logger factory
│   │       └── PropertiesUtility.java     # Properties file reader
│   │
│   └── resources/
│       └── log4j2.xml               # Log4j2 configuration
│
├── logs/                            # Generated log files
├── reports/                         # Generated Extent HTML reports
├── screenshots/                     # Failure screenshots (timestamped)
└── target/                          # Maven build output & TestNG reports
```

---

## ⚙️ Setup Instructions

### Prerequisites

| Requirement | Minimum Version |
|---|---|
| Java JDK | 17 or higher |
| Maven | 3.6+ |
| Browser | Chrome, Firefox, or Edge (latest) |
| IDE (optional) | IntelliJ IDEA / Eclipse / VS Code |

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/Selenium-Automation-Framework.git
   cd Selenium-Automation-Framework
   ```

2. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Verify setup**
   ```bash
   mvn test -Dbrowser=chrome
   ```

---

## ▶️ Test Execution

### Run via Maven CLI

```bash
# Default: Chrome browser, local execution
mvn test

# Specify browser
mvn test -Dbrowser=firefox

# Headless execution
mvn test -Dbrowser=chrome -DisHeadless=true

# Cloud execution via LambdaTest
mvn test -Dbrowser=chrome -DisLambdaTest=true

# Combine options
mvn test -Dbrowser=edge -DisHeadless=true
```

### Run via TestNG XML

```bash
# Execute the default suite
mvn test -DsuiteXmlFile=testng.xml
```

### Run from IDE

- **Eclipse/IntelliJ**: Right-click `testng.xml` → Run As → TestNG Suite
- **Individual Test**: Right-click `LoginTest.java` → Run As → TestNG Test

### Test Groups

Tests are organized into groups for selective execution:
- `e2e` — End-to-end workflow tests
- `sanity` — Quick sanity validation tests

```bash
# Run specific group (requires TestNG XML group configuration)
mvn test -Dgroups=sanity
```

---

## 📈 Reporting

### Extent Reports

The framework generates **rich HTML reports** using Extent Spark Reporter.

- **Report Location**: `reports/report.html`
- **Generated**: Automatically after each test suite execution
- **Contents**:
  - Test pass/fail/skip status with detailed logs
  - Screenshots embedded for failed tests
  - Execution timestamps and test metadata

### TestNG Default Reports

Standard TestNG reports are generated in:
- `target/surefire-reports/` — Maven Surefire reports
- `test-output/` — TestNG HTML reports (`index.html`, `emailable-report.html`)

### Log Files

Detailed execution logs are available at:
- **Console** — Real-time during execution
- **File** — `logs/automation.log` (overwritten per run)

---

## 🔄 CI/CD Integration

This framework is **CI/CD-ready** by design. The Maven Surefire plugin accepts parameterized inputs, making it seamless to integrate into any pipeline.

### Jenkins Pipeline Example

```groovy
pipeline {
    agent any
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/<your-username>/Selenium-Automation-Framework.git'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean test -Dbrowser=chrome -DisHeadless=true'
            }
        }
        stage('Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'reports',
                    reportFiles: 'report.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }
}
```

### GitHub Actions Example

```yaml
name: Selenium Tests
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
      - name: Run Tests
        run: mvn clean test -Dbrowser=chrome -DisHeadless=true
      - name: Upload Report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: extent-report
          path: reports/report.html
```

### Key CI/CD Properties

| Parameter | Values | Default |
|---|---|---|
| `browser` | `chrome`, `firefox`, `edge` | `chrome` |
| `isHeadless` | `true`, `false` | `false` |
| `isLambdaTest` | `true`, `false` | `false` |

---

## ✅ Best Practices

| Practice | Implementation |
|---|---|
| **Page Object Model** | Each page has its own class with encapsulated locators and actions |
| **Separation of Concerns** | Tests, pages, utilities, data, and config are cleanly separated |
| **Thread Safety** | `ThreadLocal` for WebDriver, ExtentTest, and cloud driver instances |
| **Externalized Test Data** | Data stored in JSON, CSV, and Excel — not hardcoded in tests |
| **Externalized Configuration** | Environment URLs and settings in properties/JSON config files |
| **No Conditional Logic in Tests** | Tests are concise; business logic lives in page objects |
| **Fluent Method Chaining** | `homePage.goToLoginPage().doLoginWith(...).getUserName()` |
| **Automatic Failure Evidence** | Screenshots captured and attached to reports on test failure |
| **Reusable Utilities** | Common browser operations abstracted in `BrowserUtility` |
| **Configurable Retry Logic** | Retry count externalized in config, not hardcoded |

---

## 🔮 Future Enhancements

- [ ] **Factory Pattern** — Introduce a `BrowserFactory` to consolidate browser creation logic and reduce constructor overloading in `BrowserUtility`
- [ ] **Docker Execution** — Containerized test execution using Selenium Grid with Docker Compose
- [ ] **Selenium Grid** — Distributed execution across multiple nodes for large-scale parallel testing
- [ ] **Allure Reports** — Add Allure as an alternative reporting framework with richer analytics
- [ ] **API Testing Layer** — Extend the framework to support REST API testing (RestAssured)
- [ ] **Database Validation** — Add JDBC utilities for backend data verification
- [ ] **Visual Regression Testing** — Integrate screenshot comparison tools (e.g., Ashot)
- [ ] **Environment-Driven Execution** — Parameterize the environment (`-Denv=QA`) from the CLI
- [ ] **CI/CD Pipeline Files** — Add Jenkinsfile and GitHub Actions workflow to the repository
- [ ] **SonarQube Integration** — Add `sonar-maven-plugin` for static code analysis and quality gate enforcement

---

## 👤 Author

Built as a **portfolio-grade** automation framework demonstrating enterprise-level test architecture, design patterns, and best practices in **Selenium WebDriver** with **Java**.

---

## 📄 License

This project is open-source and available for educational and professional use.
