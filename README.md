# CSCI 4731: Software Design & Patterns (Spring 2026)

**ADA University | School of IT and Engineering**

**Instructor:** _Elvin Taghizade_
**Semester:** _Spring 2026_
**Language:** _Java 17+_

## Project Overview

**OmniHome Smart Home System** - A reactive smart home automation system demonstrating comprehensive implementation of **Creational** and **Behavioral** design patterns in Java.

---

## Repository Structure

```
CSCI-4731-asg02/
├── src/
│   ├── main/java/asg02/
│   │   ├── Main.java                          # Demo
│   │   ├── builders/
│   │   │   └── RoutineBuilder.java            # Builder pattern
│   │   ├── connections/
│   │   │   └── CloudConnection.java           # Singleton pattern
│   │   ├── factories/
│   │   │   ├── DeviceFactory.java             # Abstract Factory interface
│   │   │   ├── BudgetFactory.java
│   │   │   └── LuxuryFactory.java
│   │   └── products/
│   │       ├── abstracts/                     # Product interfaces
│   │       │   ├── SmartLight.java
│   │       │   ├── SmartLock.java
│   │       │   └── SmartThermostat.java
│   │       ├── concretes/
│   │       │   └── SmartDevice.java           # Concrete product implementations
│   │       ├── legacy/
│   │       │   └── GlorbThermostat.java       # Legacy device (Adapter pattern)
│   │       ├── configurations/
│   │       │   └── Configuration.java         # Prototype pattern
│   │       ├── automations/
│   │       │   └── AutomationRoutine.java
│   │       ├── observers/                     # Observer pattern
│   │       │   ├── Observer.java
│   │       │   ├── Subject.java
│   │       │   ├── MotionSensor.java
│   │       │   ├── SmartLights.java
│   │       │   └── SmartAlarm.java
│   │       ├── strategies/                    # Strategy pattern
│   │       │   ├── AlertStrategy.java
│   │       │   ├── SilentPushStrategy.java
│   │       │   └── LoudSirenStrategy.java
│   │       └── commands/                      # Command pattern
│   │           ├── Command.java
│   │           ├── TurnOnLightCommand.java
│   │           ├── TurnOffLightCommand.java
│   │           ├── ArmAlarmCommand.java
│   │           ├── DisarmAlarmCommand.java
│   │           └── SmartRemote.java
│   └── test/java/asg02/                       # Comprehensive test suite
│       ├── AdapterTest.java
│       ├── BuilderTest.java
│       ├── CloudConnectionTest.java
│       ├── FactoryTest.java
│       ├── PrototypeTest.java
│       ├── SmartDeviceTest.java
│       ├── MotionSensorTest.java              
│       ├── SmartLightsTest.java              
│       ├── SmartAlarmTest.java              
│       ├── SilentPushStrategyTest.java        
│       ├── LoudSirenStrategyTest.java        
│       └── SmartRemoteTest.java             
├── build.gradle                               
├── settings.gradle
├── gradlew / gradlew.bat                     
└── README.md
```

---

## How to Use

### Prerequisites
- **Java 11+** (Java 17+ recommended)
- **Gradle 8.x** (wrapper included, no installation needed)
- **Git** for version control

### Clone the Repository

```bash
git clone https://github.com/Emillock/CSCI-4731-asg02.git
cd CSCI-4731-asg02
```

### Build the Project

```bash
# Unix/Mac
./gradlew build

# Windows
gradlew.bat build
```

### Run the Application

```bash
# Compile and run Main.java
./gradlew build
java -cp "build/classes/java/main" asg02.Main
```

### Run Tests

```bash
# Run all tests
./gradlew test

# Run specific pattern tests
./gradlew test --tests "*Observer*"
./gradlew test --tests "*Strategy*"
./gradlew test --tests "*Command*"
./gradlew test --tests "*SmartAlarm*"

# Run all tests with coverage report
./gradlew test jacocoTestReport
```

### View Test Coverage Report

After running tests with coverage:
```bash
# Open the HTML report
# Location: build/reports/jacoco/test/html/index.html
```

**Current Coverage**: 91% instruction coverage, 79% branch coverage

---

## Pattern Demonstrations

### Running the Main Application

Execute `Main.java` to see all patterns in action:

```
=== CREATIONAL PATTERNS ===
- Singleton CloudConnection
- Abstract Factory (Budget & Luxury device lines)
- Adapter (Legacy GlorbThermostat)
- Builder (Vacation Mode automation routine)
- Prototype (Configuration cloning)

=== BEHAVIORAL PATTERNS ===
- Observer: MotionSensor → SmartLights + SmartAlarm
- Strategy: Alert behavior switching (SILENT ↔ SIREN)
- Command: SmartRemote with undo functionality
```

### Example Output

```
Observer Pattern - Motion Detection System:

Hallway MotionSensor detected movement!
Hallway SmartLights turned ON
Sending silent push notification to homeowner's phone.

Strategy Pattern - Alert System:

Home Security alert strategy changed to SIREN
SOUNDING 120dB SIREN!

Command Pattern - Smart Remote:

Button 0 pressed
Hallway SmartLights turned ON
Button 1 pressed
Home Security is now ARMED

Undo executed
Home Security is now DISARMED
```