# Learn JUnit Testing in Spring Boot

Testing the system is an important phase in a Software Development Life Cycle (SDLC). Testing promotes code reliability, robustness, and ensures high-quality software delivered to clients if implemented correctly.

Testing has been given more importance ever since Test-Driven Development (TDD) has become a prominent process in developing software. Test-driven development entails converting requirements into test cases and using these test cases to gatekeep code quality. Code will be considered unacceptable if it fails any of the test cases declared in a system, and the more test cases that cover product requirements, the better. The codebase is lengthened considerably but reinforces the fact that the system meets the given requirements.

REST APIs are usually rigorously tested during integration testing. However, a good developer should test REST endpoints even before integration in their Unit Tests, since they are a vital part of the code since it's the sole access point of every entity wanting to make use of the services in the server.

## Requirements
- Spring Boot v2.0+
- JDK v1.8+
- [JUnit 5](https://junit.org/junit5/) - The most popular and widely used testing framework for Java.
- [Mockito](https://site.mockito.org/) - General-purpose framework for mocking and stubbing services and objects.
- [MockMVC](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html) - Spring's module for performing integration testing during unit testing.
- [Lombok](https://projectlombok.org/) - Convenience library for reducing boilerplate code.
- Any IDE that supports Java and Spring Boot (IntelliJ, VSC, NetBeans, etc.)
- Postman, curl or any HTTP client
