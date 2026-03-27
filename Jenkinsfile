pipeline {
    agent any
    parameters {
        choice(name: 'CHOOSE_BROWSER', choices: ['chrome', 'firefox'], description: 'Chọn trình duyệt')
        booleanParam(name: 'RUN_HEADLESS', defaultValue: true, description: 'Chạy chế độ ẩn danh')
    }
    stages {
        // Đã xóa stage Checkout thừa vì Jenkins tự làm lúc đầu rồi
        stage('Build & Test') {
            steps {
                // Chạy lệnh Maven
                bat "mvn clean test -DBROWSER=${params.CHOOSE_BROWSER} -DHEADLESS=${params.RUN_HEADLESS}"
            }
        }
    }
    post {
        always {
            // Sửa đường dẫn results thành 'target/allure-results'
            allure includeProperties: false, jdk: '', results: [[path: '**/target/allure-results']]
        }
    }
}