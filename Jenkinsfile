import java.net.URLEncoder

pipeline {
    parameters{
        string(name: 'TEST', defaultValue: '', description: 'This is a test parameter')
    }

    agent any
    tools{
        maven 'Maven3'
        jdk 'Java8'
    }


    stages {
        stage('Checkout') {
            steps{
                    checkout scm
                }
        }

        stage('Build and test') {
            steps {
                build job: 'multi-branch-pipeline-1/master'
            }
        }

        stage('Deploy') {
            steps {
                build job: 'multi-branch-pipeline-2/master'
            }
        }
    }
}
