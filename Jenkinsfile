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
        stage('Testing Parallel') {
            parallel {
                stage('Run on Chrome') {
                    steps {
                        bat "mvn test -DBROWSER=chrome -Dallure.results.directory=target/allure-results/chrome -DsuiteXmlFile=${params.TEST_SUITE} -DHEADLESS=${params.RUN_HEADLESS}"
                    }
                }
                stage('Run on Firefox') {
                    steps {
                        bat "mvn test -DBROWSER=firefox -Dallure.results.directory=target/allure-results/firefox -DsuiteXmlFile=${params.TEST_SUITE} -DHEADLESS=${params.RUN_HEADLESS}"
                    }
                }
            }
        }
    }
    post {
        always {
            // Gom báo cáo Allure từ 2 thư mục
            allure includeProperties: false, jdk: '',
            results: [
                [path: 'target/allure-results/chrome'],
                [path: 'target/allure-results/firefox']
            ]

            script {
                def botToken = "8235786325:AAH13T9nKHIiG7F59jHLyrclTntSLkTa5Hk"
                def chatId = "8321594462"
                def buildStatus = currentBuild.currentResult
                def icon = (buildStatus == 'SUCCESS') ? "✅" : "❌"

                // Nội dung tin nhắn
                def message = """
${icon} *THÔNG BÁO KẾT QUẢ TEST FIREWALL*
---------------------------------------
🚀 *Dự án:* ${env.JOB_NAME}
🔢 *Build số:* #${env.BUILD_NUMBER}
📊 *Trạng thái:* ${buildStatus}
🔗 *Xem báo cáo:* ${env.BUILD_URL}allure/
---------------------------------------
""".trim()

                // Gửi qua PowerShell để không bị lỗi ký tự và xuống dòng
                powershell """
                    \$url = "https://api.telegram.org/bot${botToken}/sendMessage"
                    \$body = @{
                        chat_id = "${chatId}"
                        text = "${message}"
                        parse_mode = "Markdown"
                    }
                    Invoke-RestMethod -Uri \$url -Method Post -Body (\$body | ConvertTo-Json -Compress) -ContentType "application/json; charset=utf-8"
                """
            }
        }
    }
}