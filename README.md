# Billent-BackOffice-Service

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing(SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

## Name
Billent-BackOffice-Service

## Description
This service is used by the back-office user-interface. This is console is used to administer the entire platform and is used by the company staff. Actions supported here include
- Manage Biller Aggregators : The biller aggregators can be administered here
- Manage Billers : The billers and their offerings can be administered here
- Manage Resellers : The resellers can administered here
- Manage Products : The biller products can be administered
- View Transactions : The transactions done on the system can be viewed here
- Manage Wallet History : The wallet history can be managed here
- Manage Users : The users of the back-office can be managed here
- Manage Roles : The roles of the back-office can be managed here

## Versioning Rules
The versioning rules adapted is as follows :

Starting version - 0.0.1-SNAPSHOT

Each numbers in a version is used to represent the current state of the project.

- 0 : This represents a stable version of the project
- 0 : This represents a feature addition to the project
- 1 : This represents a bug fix on the project
- SNAPSHOT : This represents the project is in development mode
- ALPHA : This represents the project is in production mode

The versioning rules is based on the sequential increment of each number, based on the state of the project development.

## Installation
Within a particular ecosystem, there may be a common way of installing things, such as using Yarn, NuGet, or Homebrew. However, consider the possibility that whoever is reading your README is a novice and would like more guidance. Listing specific steps helps remove ambiguity and gets people to using your project as quickly as possible. If it only runs in a specific context like a particular programming language version or operating system or has dependencies that have to be installed manually, also add a Requirements subsection.

## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.


## Contributing
In order to have a standard uniform code base, the following are required of you before you can contribute to this project.

**Laptop Requirements Recommended**

- OS : MacBook Pro ( Ventura 13.2 )
- Chip : Apple M2
- Memory : 8 GB


**IDE** 

We make use of Intellij IDEA ( Community Edition/Ultimate Edition )


**Code Formatting Style**

We make use of Google Java Format Style.

Kindly install it as a plugin in your IDE, see [Google Java Format Plugin Installation and Usage](https://github.com/google/google-java-format/blob/master/README.md#intellij-jre-config)

**Additional plugins to be installed in IDEA**

The below plugins can be found in Intellij Plugin Marketplace

- _**SonarLint by SonarSource**_ : SonarLint is a free IDE extension to find and fix coding issues in real-time, flagging issues as you code, just like a spell-checker. More than a linter, it also delivers rich contextual guidance to help developers understand why there is an issue, assess the risk and educate them on how to fix it. This helps improve their skills, enhance their productivity, and take ownership of their code, taking linting to a different level.

- _**GitToolBox by LukasZielinski**_ : Extends Git Integration 

- _**JavaDoc by Sergey Timofiychuk**_ : Plugin that generates javadocs on java class elements, like field, method, etc. Home page: http://setial.github.com/intellij-javadocs

- _**JPA Buddy by haulmont**_ : JPA Buddy is an IntelliJ IDEA plugin that helps developers work efficiently with Hibernate, EclipseLink, Spring Data JPA, Flyway, Liquibase, Lombok, MapStruct, and other related technologies in both Java and Kotlin.
Starting with version 2022.2.*-221, JPA Buddy provides free and paid functionality. Most of the features stay free, including all visual designers for entities, Spring Data repositories, SQL and Liquibase changelogs. However, some features are available only under commercial license, e.g. differential migration scripts generation. Find a comparison of free and commercial versions on our website.

<details><summary>Merge Request Rules</summary>

-  Ensure to use the development template for your merge request description. see path to the template on the project root .gitlab/merge_request_templates/development.md

- For every issue/ticket, kindly check out a new branch from the main branch, this new branch name should follow this convention 
{milestone}{issue-no} e.g **authentication-module-#1**

</details>

<details><summary>Test Rules</summary>

-  Ensure to write both successful and failed integration test for the controllers, Your integration test should assert the followings
    - HTTP status code
    - Expected Data
-  Ensure to write both successful and failed unit test for the services.
</details>


## Authors and acknowledgment
- Oluwatobi Ogunwuyi ( Team Lead )
- Olaoluwa Adaghe ( Project Manager )
- Ibrahim Lawal ( Software Engineer )
- Joy Osayi ( Software Engineer )

## License
For open source projects, say how it is licensed.

## Project status
DEVELOPMENT ACTIVE
