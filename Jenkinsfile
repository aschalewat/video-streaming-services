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
        stage('Checkouttt') {
                    steps{
                            checkout scm
                        }
                    }
            }
        stage('Build and test') {
            steps {
                build job: 'multi-branch-pipeline-1'
            }
        }

        stage('Deploy') {
            steps {
                build job: 'multi-branch-pipeline-2'
            }
        }
    }
}
