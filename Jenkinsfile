
pipeline {
    agent any

    options {
        timestamps()
        skipDefaultCheckout(true)
    }

    environment {
        IMAGE_NAME = 'namet-daniyar-student-api'
        IMAGE_TAG = 'ci-37056'

        STUDENT_NAME = 'Daniyar Namet'
        STUDENT_GROUP = 'IT2-2312 SE'
        STUDENT_ID = '37056'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '===== CHECKOUT ====='

                checkout scm

                sh 'git log -1 --oneline'

                echo "Student: ${STUDENT_NAME}"
                echo "Group: ${STUDENT_GROUP}"
                echo "Student ID: ${STUDENT_ID}"
            }
        }

        stage('Build') {
            steps {
                echo '===== SPRING BOOT BUILD ====='

                sh '''
                    chmod +x ./gradlew
                    ./gradlew --no-daemon clean bootJar -x test --console=plain
                '''

                sh 'ls -lh build/libs/'
            }
        }

        stage('Test') {
            steps {
                echo '===== AUTOMATED TESTS ====='

                sh './gradlew --no-daemon test --console=plain'

                sh '''
                    TEST_REPORT="build/test-results/test/TEST-kz.namet.studentapi.controller.StudentControllerTest.xml"

                    test -f "$TEST_REPORT"

                    grep -o '<testsuite[^>]*' "$TEST_REPORT"

                    grep -q 'tests="6"' "$TEST_REPORT"
                    grep -q 'failures="0"' "$TEST_REPORT"
                    grep -q 'errors="0"' "$TEST_REPORT"
                '''

                echo 'All 6 StudentController tests passed.'
            }
        }

        stage('Docker Build') {
            steps {
                echo '===== DOCKER IMAGE BUILD ====='

                sh '''
                    docker build --progress=plain \
                        -t "$IMAGE_NAME:$IMAGE_TAG" .
                '''

                echo "Image built: ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }

        stage('Verify') {
            steps {
                echo '===== VERIFY DOCKER IMAGE ====='

                sh '''
                    docker image inspect "$IMAGE_NAME:$IMAGE_TAG" > /dev/null

                    docker image ls "$IMAGE_NAME:$IMAGE_TAG"

                    docker image inspect "$IMAGE_NAME:$IMAGE_TAG" \
                        --format 'Image ID: {{.Id}}'

                    docker image inspect "$IMAGE_NAME:$IMAGE_TAG" \
                        --format 'Created: {{.Created}}'

                    docker image inspect "$IMAGE_NAME:$IMAGE_TAG" \
                        --format 'Size: {{.Size}} bytes'

                    docker run --rm \
                        --entrypoint java \
                        "$IMAGE_NAME:$IMAGE_TAG" -version
                '''

                echo "Verified: ${IMAGE_NAME}:${IMAGE_TAG}"
                echo "Student: ${STUDENT_NAME}"
                echo "Student ID: ${STUDENT_ID}"
            }
        }
    }

    post {
        success {
            echo '======================================'
            echo 'JENKINS PIPELINE SUCCESS'
            echo 'Student: Daniyar Namet'
            echo 'Group: IT2-2312 SE'
            echo 'Student ID: 37056'
            echo '======================================'
        }

        failure {
            echo 'JENKINS PIPELINE FAILED'
            echo 'Check the failed stage in Console Output.'
        }
    }
}
