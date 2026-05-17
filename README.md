# SOLID Principles in Java Microservices

This repository serves as a comprehensive guide and architectural reference for implementing the **SOLID Principles** within Java-based microservices.

---

## 📋 Table of Contents
1. [About SOLID Principles](#about-solid-principles)
2. [Single Responsibility Principle (SRP)](#1-single-responsibility-principle-srp)
3. [Open/Closed Principle (OCP)](#2-openclosed-principle-ocp)
4. [Liskov Substitution Principle (LSP)](#3-liskov-substitution-principle-lsp)
5. [Interface Segregation Principle (ISP)](#4-interface-segregation-principle-isp)
6. [Dependency Inversion Principle (DIP)](#5-dependency-inversion-principle-dip)
7. [Getting Started & Verification](#getting-started--verification)

---

## 💡 About SOLID Principles
SOLID is an acronym for five object-oriented design principles intended to make software designs more understandable, flexible, and maintainable. In microservice environments, these rules prevent architectural degradation over time.

---

## 1. Single Responsibility Principle (SRP)
> **Definition:** A class should have one, and only one responsibility to perform task.

### ❌ Anti-Pattern (Violating SRP)
```java
package com.example.service;

import java.sql.*;

public class UserService {
    public void registerUser(String username, String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Violation: Mixing business flow with low-level data layer connectivity
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db")) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### ✅ Clean Pattern (Adhering to SRP)
```java
package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        userRepository.save(user);
    }
}
```

---

## 2. Open/Closed Principle (OCP)
> **Definition:** Software entities should be open for extension, but closed for modification. One should not be able to change core implementation but can inherit and create a wrapper around it or override with custom implementation. 

### ❌ Anti-Pattern (Violating OCP)
```java
package com.example.service;

public class PaymentProcessor {
    public void process(String method, double amount) {
        if (method.equalsIgnoreCase("CREDIT_CARD")) {
            // Credit card execution logic
        } else if (method.equalsIgnoreCase("PAYPAL")) {
            // PayPal execution logic
        } // Adding Apple Pay requires modifying this production method directly
    }
}
```

### ✅ Clean Pattern (Adhering to OCP)
```java
package com.example.service;

import java.util.Map;

public interface PaymentStrategy {
    void execute(double amount);
}

public class CreditCardPayment implements PaymentStrategy {
    public void execute(double amount) { /* logic */ }
}

public class PaymentProcessor {
    private final Map<String, PaymentStrategy> strategies;

    public PaymentProcessor(Map<String, PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public void process(String method, double amount) {
        PaymentStrategy strategy = strategies.get(method);
        if (strategy == null) throw new IllegalArgumentException("Unsupported method");
        strategy.execute(amount);
    }
}
```

---

## 3. Liskov Substitution Principle (LSP)
> **Definition:** Subtypes must be substitutable for their base types without altering correctness.  parent class reference can hold an instance of its child class.

### ❌ Anti-Pattern (Violating LSP)
```java
package com.example.service;

public interface NotificationChannel {
    void send(String message);
}

public class SmsNotification implements NotificationChannel {
    public void send(String message) {
        if (message.length() > 160) {
            // Violation: Subclass throws unexpected runtime error breaking parent contract expectations
            throw new UnsupportedOperationException("SMS cannot exceed 160 characters");
        }
    }
}
```

### ✅ Clean Pattern (Adhering to LSP)
```java
package com.example.service;

public interface NotificationChannel {
    void send(String message);
    boolean supportsLength(int length);
}

public class SmsNotification implements NotificationChannel {
    public void send(String message) { /* logic */ }
    public boolean supportsLength(int length) { return length <= 160; }
}
```

---

## 4. Interface Segregation Principle (ISP)
> **Definition:** Provide multiple specific interfaces rather than a few general-purpose ones. Clients should not be forced to depend on interfaces they do not use.

### ❌ Anti-Pattern (Violating ISP)
```java
package com.example.service;

public interface ElectronicDevices {
    int displaySize(); // Specific to device 
}

public class Trimmer implements ElectronicDevices {
    public int displaySize() {
        // Violation: Forced to implement useless stub methods
        throw new UnsupportedOperationException("Don't have display");
    }
}
```

### ✅ Clean Pattern (Adhering to ISP)
```java
package com.example.service;

public interface FileUploader {
    void uploadFile(byte[] data);
}

public interface ContentDeliveryNetwork {
    void setupCdnCaching();
}

public class LocalDiskStorage implements FileUploader {
    public void uploadFile(byte[] data) { /* write logic */ }
}
```

---

## 5. Dependency Inversion Principle (DIP)
> **Definition:** Depend on abstractions, not on concretions. High-level modules should not depend on low-level modules. Basically, abstraction layer should be called rather than the class.

### ❌ Anti-Pattern (Violating DIP)
```java
package com.example.service;

import com.example.client.SendGridEmailClient;

public class AlertManager {
    // Violation: Directly instantiating low-level library clients creates rigid structural locking
    private final SendGridEmailClient emailClient = new SendGridEmailClient();

    public void triggerAlert(String text) {
        emailClient.sendEmail(text);
    }
}
```

### ✅ Clean Pattern (Adhering to DIP)
```java
package com.example.service;

public interface MessageSender {
    void send(String text);
}

public class AlertManager {
    private final MessageSender messageSender;

    // High-level service depends safely on abstraction injected at runtime
    public AlertManager(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void triggerAlert(String text) {
        messageSender.send(text);
    }
}
```
