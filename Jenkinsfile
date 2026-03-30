pipeline {
    agent any

    parameters {
        // Thêm lại TEST_SUITE nếu bạn muốn chọn bộ test
        choice(name: 'TEST_SUITE', choices: ['testng.xml'], description: 'Chọn bộ test suite')
        booleanParam(name: 'RUN_HEADLESS', defaultValue: true, description: 'Chạy chế độ ẩn danh (Headless)')
    }

    stage('Testing Parallel') {
        parallel {
            stage('Run on Chrome') {
                steps {
                    // Thêm -Dallure.results.directory để tách folder
                    bat "mvn test -DBROWSER=chrome -Dallure.results.directory=target/allure-results/chrome -DsuiteXmlFile=testng.xml"
                }
            }
            stage('Run on Firefox') {
                steps {
                    // Thêm -Dallure.results.directory để tách folder
                    bat "mvn test -DBROWSER=firefox -Dallure.results.directory=target/allure-results/firefox -DsuiteXmlFile=testng.xml"
                }
            }
        }
    }
    post {
        always {
            // QUAN TRỌNG: Chỉ định RÕ CẢ 2 ĐƯỜNG DẪN folder kết quả
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results/chrome'], [path: 'target/allure-results/firefox']]
        }
    }