pipeline {
    agent any

    parameters {
        choice(name: 'TEST_SUITE', choices: ['testng.xml'], description: 'Chọn bộ test suite')
        booleanParam(name: 'RUN_HEADLESS', defaultValue: true, description: 'Chạy chế độ ẩn danh (Headless)')
    }

    stages {
        stage('Prepare') {
            steps {
                bat "mvn clean"
            }
        }
        stage('Testing Parallel') {
            parallel {
                stage('Run on Chrome') {
                    steps {
                        // Tách thư mục chrome để không bị Firefox ghi đè
                        bat "mvn test -DBROWSER=chrome -Dallure.results.directory=target/allure-results/chrome -DsuiteXmlFile=${params.TEST_SUITE} -DHEADLESS=${params.RUN_HEADLESS}"
                    }
                }
                stage('Run on Firefox') {
                    steps {
                        // Tách thư mục firefox để không bị Chrome ghi đè
                        bat "mvn test -DBROWSER=firefox -Dallure.results.directory=target/allure-results/firefox -DsuiteXmlFile=${params.TEST_SUITE} -DHEADLESS=${params.RUN_HEADLESS}"
                    }
                }
            }
        }
    }
    post {
        always {
            // Gom dữ liệu từ cả 2 thư mục để Allure cộng dồn thành 4 TCs
            allure includeProperties: false, jdk: '', results: [
                [path: 'target/allure-results/chrome'],
                [path: 'target/allure-results/firefox']
            ]
        }
    }
}