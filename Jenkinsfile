pipeline {
    agent any

    parameters {
        // Thêm lại TEST_SUITE nếu bạn muốn chọn bộ test
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
                            // Thêm -Dallure.results.directory để tách thư mục
                            bat "mvn test -DBROWSER=chrome -Dallure.results.directory=target/allure-results/chrome -DsuiteXmlFile=testng.xml"
                        }
                    }
                    stage('Run on Firefox') {
                        steps {
                            // Thêm -Dallure.results.directory để tách thư mục
                            bat "mvn test -DBROWSER=firefox -Dallure.results.directory=target/allure-results/firefox -DsuiteXmlFile=testng.xml"
                        }
                    }
                }
            }
        }
    post {
        always {
            // Cách 1: Chỉ định rõ 2 folder kết quả (Chắc chắn nhất)
            allure includeProperties: false, jdk: '',
            results: [
                [path: 'target/allure-results/chrome'],
                [path: 'target/allure-results/firefox']
            ]
            script {
                        // 1. Cấu hình thông tin (Thay bằng thông tin của bạn)
                        def botToken = "8235786325:AAH13T9nKHIiG7F59jHLyrclTntSLkTa5Hk" // Token từ BotFather
                        def chatId = "8321594462"              // ID từ userinfobot

                        // 2. Chuẩn bị nội dung tin nhắn
                        def buildStatus = currentBuild.currentResult
                        def icon = (buildStatus == 'SUCCESS') ? "✅" : "❌"
                        def message = """
            ${icon} *THÔNG BÁO KẾT QUẢ TEST FIREWALL*
            ---------------------------------------
            🚀 *Dự án:* ${env.JOB_NAME}
            🔢 *Build số:* #${env.BUILD_NUMBER}
            📊 *Trạng thái:* ${buildStatus}
            🔗 *Xem báo cáo:* ${env.BUILD_URL}allure/
            ---------------------------------------
            """
                        // 3. Gửi tin nhắn qua Telegram API (Sử dụng lệnh bat của Windows)
                        bat "curl -s -X POST https://api.telegram.org/bot${botToken}/sendMessage -d chat_id=${chatId} -d text=\"${message}\" -d parse_mode=Markdown"
                    }
                }
        }
    }
