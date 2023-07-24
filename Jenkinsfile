pipeline {
  agent any 

  environment {

  }

  stages {
    stage('Build Image') {
      steps {
        git branch: 'main',
            credentialsId: 'uabass'
            url: 'https://gitlab.com/teamdigicore/billent-backoffice-service.git'
      }
    }
  }
}