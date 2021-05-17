pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'Java JDK'
    }
    stages {
        stage('Clean and Test target') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Test and Package') {
            steps {
                sh 'mvn package'
            }
        }
        // stage('Code Analysis: Sonarqube') {
        //     steps {
        //         withSonarQubeEnv('SonarQube') {
        //             sh 'mvn sonar:sonar'
        //         }
        //     }
        // }
        // stage('Await Quality Gateway') {
        //     steps {
        //         waitForQualityGate abortPipeline: true
        //     }
        // }
    }
    post {
        always {
            sh 'mvn clean'
        }
    }
}