pipeline {
    agent any
    tools {
        maven 'local_maven'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    echo "Archiving the artifact"
                    archiveArtifacts artifacts: 'target/*.war'
                }
            }
        }
        stage('Deploy to tomcat server') {
            steps {
                script {
                    // List contents of the target directory
                    sh 'ls -l target'
                    deploy adapters: [tomcat9(credentialsId: 'c36d1479-5eb6-41d2-bc3a-f94f51620a24', path: '', url: 'http://192.168.1.115:8081/')], contextPath: null, war: 'target/*.war'
                }
            }
        }
    }
}
