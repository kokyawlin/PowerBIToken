# Power BI Embedded Token - Spring Boot Application

A Spring Boot application that connects to Power BI using Azure AD credentials to generate embedded tokens for displaying Power BI reports in web applications.

## Features

- 🔐 Secure authentication using Azure AD (Tenant ID, Client ID, Client Secret)
- 🎫 Generate Power BI embedded tokens for reports
- 🖥️ Interactive HTML interface to view Power BI reports
- 🌐 RESTful API endpoints for token generation
- ⚡ Built with Spring Boot 3.x and Java 17

## Prerequisites

Before running this application, you need:

1. **Java 17 or higher** installed
2. **Maven 3.6+** installed
3. **Power BI Pro or Premium account**
4. **Azure AD App Registration** with the following:
   - Tenant ID
   - Client ID (Application ID)
   - Client Secret
   - Power BI API permissions configured

## Azure AD Setup

### 1. Register an Application in Azure AD

1. Go to [Azure Portal](https://portal.azure.com)
2. Navigate to **Azure Active Directory** > **App registrations**
3. Click **New registration**
4. Enter a name (e.g., "PowerBI-Embed-App")
5. Select supported account types
6. Click **Register**

### 2. Configure API Permissions

1. In your app registration, go to **API permissions**
2. Click **Add a permission**
3. Select **Power BI Service**
4. Select **Delegated permissions** and add:
   - `Report.Read.All`
   - `Dataset.Read.All`
   - `Workspace.Read.All`
5. Click **Add permissions**
6. Click **Grant admin consent** (requires admin privileges)

### 3. Create Client Secret

1. Go to **Certificates & secrets**
2. Click **New client secret**
3. Add a description and select expiration
4. Click **Add**
5. **Copy the secret value immediately** (you won't be able to see it again)

### 4. Note Your Credentials

Copy the following values:
- **Tenant ID**: From the Overview page
- **Client ID (Application ID)**: From the Overview page
- **Client Secret**: The value you just copied

## Configuration

1. Open `src/main/resources/application.properties`
2. Replace the placeholder values with your actual credentials:

```properties
powerbi.tenant-id=YOUR_TENANT_ID
powerbi.client-id=YOUR_CLIENT_ID
powerbi.client-secret=YOUR_CLIENT_SECRET
```

## Build and Run

### Using Maven

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Using Java

```bash
# Build the JAR file
mvn clean package

# Run the JAR
java -jar target/powerbi-token-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Usage

### Web Interface

1. Open your browser and navigate to `http://localhost:8080`
2. Enter your **Workspace ID** (Group ID)
3. Enter your **Report ID**
4. Click **Load Report**

#### Finding Your IDs

**Workspace ID:**
- Go to Power BI Service
- Select your workspace
- The URL will be: `https://app.powerbi.com/groups/{WORKSPACE_ID}/...`

**Report ID:**
- Open a report in your workspace
- The URL will be: `https://app.powerbi.com/groups/{WORKSPACE_ID}/reports/{REPORT_ID}/...`

### API Endpoints

#### Get Embed Token for Report

```
GET /api/powerbi/embedtoken?workspaceId={workspaceId}&reportId={reportId}
```

**Response:**
```json
{
  "embedToken": "H4sIAAAAAAAEAB...",
  "embedUrl": "https://app.powerbi.com/reportEmbed?...",
  "reportId": "12345678-1234-1234-1234-123456789012",
  "tokenId": "abcdef...",
  "expiration": "2024-01-01T12:00:00Z"
}
```

#### Get Embed Token for Dataset

```
GET /api/powerbi/embedtoken/dataset?datasetId={datasetId}
```

#### Health Check

```
GET /api/powerbi/health
```

## Project Structure

```
PowerBIToken/
├── src/
│   ├── main/
│   │   ├── java/com/powerbi/token/
│   │   │   ├── PowerBITokenApplication.java    # Main application class
│   │   │   ├── config/
│   │   │   │   └── PowerBIProperties.java      # Configuration properties
│   │   │   ├── controller/
│   │   │   │   └── PowerBIController.java      # REST API controller
│   │   │   ├── model/
│   │   │   │   ├── EmbedConfig.java            # Embed configuration model
│   │   │   │   └── EmbedToken.java             # Embed token model
│   │   │   └── service/
│   │   │       └── PowerBIService.java         # Power BI service logic
│   │   └── resources/
│   │       ├── application.properties           # Application configuration
│   │       └── static/
│   │           └── index.html                   # Web interface
│   └── test/
│       └── java/com/powerbi/token/             # Test files
├── pom.xml                                      # Maven dependencies
└── README.md                                    # This file
```

## Dependencies

- **Spring Boot 3.1.5** - Application framework
- **Azure Identity 1.10.0** - Azure AD authentication
- **OkHttp 4.11.0** - HTTP client
- **Gson 2.10.1** - JSON processing

## Troubleshooting

### Authentication Errors

- Verify your Tenant ID, Client ID, and Client Secret are correct
- Ensure API permissions are granted with admin consent
- Check that your Azure AD app has Power BI Service permissions

### Report Not Loading

- Verify the Workspace ID and Report ID are correct
- Ensure the service principal has access to the workspace
- Check that the report exists and is published

### CORS Errors

- The controller includes `@CrossOrigin(origins = "*")` for development
- For production, configure specific allowed origins

## Security Notes

⚠️ **Important:**
- Never commit your `application.properties` file with real credentials to version control
- Use environment variables or Azure Key Vault for production deployments
- Implement proper authentication and authorization for production use
- The current CORS configuration allows all origins - restrict this in production

## License

This project is provided as-is for educational and development purposes.

## Support

For issues related to:
- **Power BI API**: [Power BI Developer Documentation](https://docs.microsoft.com/en-us/power-bi/developer/)
- **Azure AD**: [Azure Active Directory Documentation](https://docs.microsoft.com/en-us/azure/active-directory/)
- **Spring Boot**: [Spring Boot Documentation](https://spring.io/projects/spring-boot)