pipeline {
    agent any

    parameters {
        choice(name: 'CHOOSE_BROWSER', choices: ['chrome', 'firefox'], description: 'Chọn trình duyệt để test')
        booleanParam(name: 'RUN_HEADLESS', defaultValue: true, description: 'Chạy chế độ ẩn danh (Headless)')
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Chạy lệnh Maven với các tham số đã chọn
                bat "mvn clean test -DBROWSER=${params.CHOOSE_BROWSER} -DHEADLESS=${params.RUN_HEADLESS}"
            }
        }
    }

    post {
        always {
            // Luôn tạo báo cáo Allure sau khi test xong
            allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
        }
    }
}