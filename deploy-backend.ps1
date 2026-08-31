# deploy-backend.ps1
# Automates compiling and deploying the correct backend JAR to the EC2 server

$localJar = "C:\Users\Sharon\IdeaProjects\ce_pune_website\target\ce-pune-backend-1.0-SNAPSHOT.jar"
$keyPath = "C:\Users\Sharon\Downloads\aws-springboot\ceindia-key.pem"
$serverHost = "deploy@148.66.154.48"

Write-Host "1. Building backend JAR..." -ForegroundColor Green
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Error "Maven build failed!"
    exit 1
}

Write-Host "2. Stopping remote backend service..." -ForegroundColor Green
& "C:\Windows\System32\OpenSSH\ssh.exe" -o StrictHostKeyChecking=no -i $keyPath $serverHost "sudo systemctl stop ce-pune-backend; sudo pkill -9 -f java"

Write-Host "3. Uploading new JAR to server..." -ForegroundColor Green
& "C:\Windows\System32\OpenSSH\scp.exe" -o StrictHostKeyChecking=no -i $keyPath $localJar "${serverHost}:~/"
if ($LASTEXITCODE -ne 0) {
    Write-Error "SCP upload failed!"
    exit 1
}

Write-Host "4. Restarting remote backend service..." -ForegroundColor Green
& "C:\Windows\System32\OpenSSH\ssh.exe" -o StrictHostKeyChecking=no -i $keyPath $serverHost "sudo systemctl start ce-pune-backend"

Write-Host "Backend successfully deployed!" -ForegroundColor Green
