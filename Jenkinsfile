pipeline {
    agent any

    parameters {
        choice(name: 'TEST_SUITE', choices: ['testng.xml'], description: 'Chọn bộ test suite')
        booleanParam(name: 'RUN_HEADLESS', defaultValue: true, description: 'Chạy chế độ ẩn danh (Headless)')
    }

    stages {
        stage('Prepare') {
            steps { bat "mvn clean" }
        }
        stage('Testing') {
            steps {
                // Chỉ chạy Chrome và lưu vào folder kết quả cố định
                bat "mvn test -DBROWSER=chrome -Dallure.results.directory=target/allure-results -DsuiteXmlFile=${params.TEST_SUITE} -DHEADLESS=${params.RUN_HEADLESS}"
            }
        }
    }
    post {
        always {
            // Gom báo cáo Allure từ thư mục duy nhất
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]

            script {
                def botToken = "8235786325:AAH13T9nKHIiG7F59jHLyrclTntSLkTa5Hk"
                def chatId = "8321594462"
                def buildStatus = currentBuild.currentResult
                def icon = (buildStatus == 'SUCCESS') ? "✅" : "❌"

                // Chuỗi tin nhắn gọn gàng
                def message = "${icon} *FIREWALL TEST: ${buildStatus}*\\n🔢 Build: #${env.BUILD_NUMBER}\\n🚀 Browser: CHROME\\n🔗 [Xem Báo Cáo](${env.BUILD_URL}allure/)"

                powershell """
                    \$url = "https://api.telegram.org/bot${botToken}/sendMessage"
                    \$payload = @{
                        chat_id = "${chatId}"
                        text = "${message}"
                        parse_mode = "Markdown"
                    } | ConvertTo-Json -Compress

                    Invoke-RestMethod -Uri \$url -Method Post -Body \$payload -ContentType "application/json; charset=utf-8"
                """
            }
        }
    }
}