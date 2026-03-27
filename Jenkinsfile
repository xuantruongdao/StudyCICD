pipeline {
    agent any

    parameters {
        // Thêm lại TEST_SUITE nếu bạn muốn chọn bộ test
        choice(name: 'TEST_SUITE', choices: ['testng.xml'], description: 'Chọn bộ test suite')
        booleanParam(name: 'RUN_HEADLESS', defaultValue: true, description: 'Chạy chế độ ẩn danh (Headless)')
    }

    stages {
        stage('Prepare') {
            steps {
                // Clean một lần duy nhất tại đây để dọn dẹp rác từ build cũ
                bat "mvn clean"
            }
        }
        stage('Testing Parallel') {
            parallel {
                stage('Run on Chrome') {
                    steps {
                        // Bỏ 'clean', chỉ để 'test'
                        bat "mvn test -DBROWSER=chrome -DHEADLESS=${params.RUN_HEADLESS} -DsuiteXmlFile=${params.TEST_SUITE}"
                    }
                }
                stage('Run on Firefox') {
                    steps {
                        // Bỏ 'clean', chỉ để 'test'
                        bat "mvn test -DBROWSER=firefox -DHEADLESS=${params.RUN_HEADLESS} -DsuiteXmlFile=${params.TEST_SUITE}"
                    }
                }
            }
        }
    }
    post {
        always {
            // Jenkins sẽ gộp tất cả file .json trong target/allure-results thành 1 báo cáo duy nhất
            allure includeProperties: false, jdk: '', results: [[path: '**/target/allure-results']]
        }
    }
}