# PowerBIToken

A Spring Boot application for generating Power BI embedded tokens to display Power BI reports in web applications.

## Overview

This project provides a backend service that generates embedded tokens for Power BI reports using Azure AD authentication. The embedded tokens allow users to view Power BI reports securely within a web application.

## Features

- Generate embedded tokens for Power BI reports
- REST API for token generation
- Support for workspace and report-based embedding
- Configurable Azure AD authentication

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Power BI Pro or Premium account
- Azure AD application registration

## Configuration

Configure the following properties in `application.properties` or `application.yml`:

- Azure AD Tenant ID
- Client ID
- Client Secret
- Power BI Workspace ID
- Power BI Report ID

## Getting Started

More documentation coming soon.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
