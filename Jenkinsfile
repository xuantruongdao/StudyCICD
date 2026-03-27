pipeline {
    agent any

    parameters {
        // Cho phép chọn bộ test (testng.xml, smoke.xml, vầy...)
        booleanParam(name: 'RUN_HEADLESS', defaultValue: true, description: 'Chạy chế độ ẩn danh (Headless)')
    }

    stages {
        stage('Testing Parallel') {
            parallel {
                stage('Run on Chrome') {
                    steps {
                        bat "mvn clean test -DBROWSER=chrome -DHEADLESS=${params.RUN_HEADLESS} -DsuiteXmlFile=${params.TEST_SUITE}"
                    }
                }
                stage('Run on Firefox') {
                    steps {
                        // Đảm bảo máy Jenkins đã cài Firefox
                        bat "mvn clean test -DBROWSER=firefox -DHEADLESS=${params.RUN_HEADLESS} -DsuiteXmlFile=${params.TEST_SUITE}"
                    }
                }
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