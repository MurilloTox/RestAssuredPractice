# REST Assured API Testing Practice

## Project Overview
This project demonstrates API testing using REST Assured for a mock API, implementing comprehensive test scenarios for client and resource management.

## Technologies Used
- Java
- Maven
- REST Assured
- POJO (Plain Old Java Object)
- Cucumber

## Base URL
`https://673bdac096b8dcd5f3f7afdb.mockapi.io/api/v1/`

## Test Cases

### Test Case 1: Update Client Phone Number
- **Objective**: Change the phone number of the first client named Laura
- **Pre-Conditions**: At least 10 registered clients
- **Steps**:
  1. Find the first client named Laura
  2. Save current phone number
  3. Update phone number
  4. Validate new phone number is different
- **Post-Conditions**: Delete all registered clients

### Test Case 2: Get Active Resources
- **Objective**: Retrieve and update active resources
- **Pre-Conditions**: At least 5 active resources
- **Steps**:
  1. Find all active resources
  2. Update resources to inactive
- **Verifications**:
  - Validate HTTP status code 200
  - Verify response body schema

### Test Case 3: Client CRUD Operations
- **Objective**: Demonstrate full client lifecycle
- **Steps**:
  1. Create a new client
  2. Find the new client
  3. Update client parameters
  4. Delete the client
- **Verifications**:
  - Validate HTTP status code 200
  - Verify response body schema
  - Confirm update details

### Test Case 4: Update Last Created Resource
- **Objective**: Update the most recently created resource
- **Pre-Conditions**: At least 15 resources
- **Steps**:
  1. Find the latest resource
  2. Update all resource parameters
- **Verifications**:
  - Validate HTTP status code 200
  - Verify response body schema
  - Confirm update details

## Running Tests
- Execute tests using Maven: `mvn test`

## Best Practices
- Modular and maintainable test design
- Independent test execution
- Comprehensive error handling
- Clear documentation
