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
                // Chạy Chrome và ép Pipeline chạy tiếp dù test Fail (để còn gửi Telegram)
                bat "mvn test -DBROWSER=chrome -Dallure.results.directory=target/allure-results -DsuiteXmlFile=${params.TEST_SUITE} -DHEADLESS=${params.RUN_HEADLESS} || exit 0"
            }
        }
    }
    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]

            script {
                            def botToken = "8235786325:AAH13T9nKHIiG7F59jHLyrclTntSLkTa5Hk"
                            def chatId = "8321594462"
                            def allureLink = "${env.BUILD_URL}allure/"

                            // Tạo nội dung tin nhắn trực tiếp trong PowerShell để xuống dòng chuẩn xác
                            powershell """
                                [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

                                # Dùng dấu `n để xuống dòng trong PowerShell
                                \$message = "✅ *BÁO CÁO TEST FIREWALL*`n" +
                                           "----------------------------`n" +
                                           "*Build:* #${env.BUILD_NUMBER}`n" +
                                           "*Status:* SUCCESS`n" +
                                           "----------------------------`n" +
                                           "[👉 Xem Allure Report](${allureLink})"

                                \$url = "https://api.telegram.org/bot${botToken}/sendMessage"
                                \$payload = @{
                                    chat_id = "${chatId}"
                                    text = \$message
                                    parse_mode = "Markdown"
                                }
                                Invoke-RestMethod -Uri \$url -Method Post -Body (\$payload | ConvertTo-Json) -ContentType "application/json; charset=utf-8"
                            """
                        }
        }
    }
}