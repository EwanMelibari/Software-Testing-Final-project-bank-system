echo "🚀 Starting Local CI/CD Pipeline..."

mvn clean compile test verify spotbugs:check package

if [ $? -eq 0 ]; then
    echo "Pipeline Passed Successfully! Artifacts are ready in target/"
else
    echo "Pipeline Failed! Please check the logs above."
fi