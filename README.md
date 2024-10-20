### About This Project-

### This is an complete project where an [Daily finance App](https://dailyfinance.roadtocareer.net/) by writting test suites using selenium-webdrive And TestNG  As Testing Framework.

The following key modules/pages are automated:

- Registration
- Admin Login
- Admin Can Check Registered User in the Dasboard
- Log in with the last registered user and can update their profile image.
- Last Registered User can Add Cost Items
- Search Item From the list
### Technology:
- Tool: Selenium Webdriver
- IDE: Intellij IDEA
- Build tool: Gradle
- Language: Java
- Testing Framework : TestNG

### Prerequisite:

- Need to install jdk 11, gradle and allure
- Configure Environment variable for jdk 11, gradle and allure
- Clone this project and unzip it
- Open the project folder
- Double click on "build.gradle" and open it through IntellIJ IDEA
- Let the project build successfully
- Click on "Terminal" and run the automation scripts

### Run The Automation Script by the following Command
gradle clean test 
- Selenium will open the browser and start automating.
- After automation to view allure report , give the following commands:

allure generate allure-results --clean -o allure-report
allure serve allure-results

- Below is my allure overview report:
 ![final-allure-1](https://github.com/user-attachments/assets/52db33d2-938b-4be5-b6c7-033d6b2920d6)

Here are the suites of this project:
![final-allure-2](https://github.com/user-attachments/assets/8722c708-4eb5-48c0-8be3-b1b2f721dd5b)

Here is the overall walkthrough of the project - [Video](https://screenrec.com/share/MfwIolKnQg)
