# Quick Start Guide

Get your Power BI Embedded Token application running in 5 minutes!

## Prerequisites

- Java 17+ installed (`java -version`)
- Maven 3.6+ installed (`mvn -version`)
- Power BI Pro or Premium account
- Azure AD App Registration (see [Azure AD Setup](#azure-ad-setup))

## Quick Setup

### 1. Clone and Navigate

```bash
git clone https://github.com/kokyawlin/PowerBIToken.git
cd PowerBIToken
```

### 2. Configure Credentials

Copy the example configuration:

```bash
cp application.properties.example src/main/resources/application.properties
```

Edit `src/main/resources/application.properties` and update:

```properties
powerbi.tenant-id=YOUR_TENANT_ID_HERE
powerbi.client-id=YOUR_CLIENT_ID_HERE
powerbi.client-secret=YOUR_CLIENT_SECRET_HERE
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Use the Web Interface

1. Open your browser to `http://localhost:8080`
2. Enter your Power BI **Workspace ID** (found in the URL when viewing your workspace)
3. Enter your **Report ID** (found in the URL when viewing your report)
4. Click **Load Report**

## Azure AD Setup

### Quick Steps:

1. **Go to Azure Portal** → Azure Active Directory → App registrations → New registration
2. **Note down**:
   - Tenant ID (from Overview)
   - Client ID / Application ID (from Overview)
3. **Create Client Secret**:
   - Go to Certificates & secrets
   - New client secret
   - Copy the value immediately
4. **Add API Permissions**:
   - API permissions → Add a permission → Power BI Service
   - Add: `Report.Read.All`, `Dataset.Read.All`, `Workspace.Read.All`
   - Grant admin consent
5. **Configure in Power BI**:
   - Go to Power BI Admin Portal → Tenant settings
   - Enable "Allow service principals to use Power BI APIs"
   - Add your app to your workspace as a member

## Finding Your IDs

### Workspace ID (Group ID):
```
Power BI URL: https://app.powerbi.com/groups/[WORKSPACE_ID]/...
                                              ^^^^^^^^^^^^^^
                                              This is your Workspace ID
```

### Report ID:
```
Report URL: https://app.powerbi.com/groups/[workspace-id]/reports/[REPORT_ID]/...
                                                                    ^^^^^^^^^^^
                                                                    This is your Report ID
```

## API Endpoints

Once running, you can also use the REST API:

### Get Embed Token
```bash
curl "http://localhost:8080/api/powerbi/embedtoken?workspaceId=YOUR_WORKSPACE_ID&reportId=YOUR_REPORT_ID"
```

### Health Check
```bash
curl http://localhost:8080/api/powerbi/health
```

## Troubleshooting

### Application won't start
- Check Java version: `java -version` (should be 17+)
- Check Maven version: `mvn -version` (should be 3.6+)
- Check if port 8080 is available

### Authentication errors
- Verify your Tenant ID, Client ID, and Client Secret
- Ensure API permissions are granted with admin consent
- Check that service principals are enabled in Power BI tenant settings

### Report won't load
- Verify the Workspace ID and Report ID are correct
- Ensure your app has access to the workspace in Power BI
- Check browser console for errors

## Build & Test

```bash
# Run tests
mvn test

# Build JAR
mvn clean package

# Run JAR
java -jar target/powerbi-token-1.0.0.jar
```

## Next Steps

- Read the full [README.md](README.md) for detailed information
- Secure your credentials using environment variables or Azure Key Vault
- Configure proper CORS settings for production
- Add authentication to your web interface

## Support

For issues, refer to:
- [Power BI Developer Documentation](https://docs.microsoft.com/en-us/power-bi/developer/)
- [Azure AD Documentation](https://docs.microsoft.com/en-us/azure/active-directory/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

**Security Note**: Never commit your `application.properties` file with real credentials to version control!
