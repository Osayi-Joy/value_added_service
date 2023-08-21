pipeline {
    agent {
       label "Runner"
       }
       
    tools {
    jdk 'Java17'
    maven 'Maven'
    }
    
    environment {
        VERSION = "${env.BUILD_ID}"
        AWS_ACCOUNT_ID="549258854334"
        AWS_DEFAULT_REGION="eu-west-2"
        IMAGE_REPO_NAME="backoffice-service"
        IMAGE_TAG= "0.${env.BUILD_ID}"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
    }
    stages {
        stage('Git checkout') {
            steps {
                git branch: 'redtech', credentialsId: 'GitLab_Access', url: 'https://gitlab.com/teamdigicore/billent-backoffice-service.git'
            }
        }
        
        stage('Build The Artifact') {
            steps {
                withMaven(
                    maven: 'Maven',
                //mavenLocalRepo: '.repository',
                    mavenSettingsConfig: 'fcfa17f8-08b7-4f8d-ad46-4ca1d78027e1'//MyMVNSettings
                    ){
                    //sh 'mvn test -Dspring.profiles.active=test'
                    sh 'mvn clean package spring-boot:repackage -DskipTests'
                    }
            }
        }
      
        stage('Building A Docker image') {
            steps{
              script {
                dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                 }
            }
        }
        
        stage('Pushing to ECR') {
                 environment {
                        AWS_ACCESS_KEY_ID = credentials('aws_access_key_id')
                        AWS_SECRET_ACCESS_KEY = credentials('aws_secret_access_key')
                         }
          steps{  
            script {
                withAWS(role: "tf_rd_role", roleAccount: '549258854334') {
                sh """aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"""
                sh """docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:$IMAGE_TAG"""
                sh """docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"""
                 }
               }
             }
         }

         stage('pull image & Deploying application on k8s cluster') {
                    environment {
                       AWS_ACCESS_KEY_ID = credentials('aws_access_key_id')
                       AWS_SECRET_ACCESS_KEY = credentials('aws_secret_access_key')
                 }
                    steps {
                      script{
                          withAWS(role: "tf_rd_role", roleAccount: '549258854334') {
                          sh 'aws eks update-kubeconfig --name eks-redtech-rd-apps --region eu-west-2'
                          sh """helm upgrade --install --set image.tag="${IMAGE_TAG}" --namespace billeraggregation backoffice-service ./backoffice-service -f ./backoffice-service/values.yaml"""
                        }
                    }
                  }
            }
         
        stage('Remove Unused docker image') {
          steps{
            script {
              sh "docker rmi -f ${IMAGE_REPO_NAME}:$IMAGE_TAG"
              sh "docker system prune -f"
                    }
          }
        }    
    }

    post{
          always{
            script{
               RESULT=(currentBuild.result == null ? "SUCCESSFUL" : currentBuild.result)

            }
          }
          success{
            script{
                emailext to: "austin.okorowu@redtechlimited.com",
                subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}",
                attachmentsPattern: '*.jar'
            }
          }
          failure{
            script{
                emailext to: "austin.okorowu@redtechlimited.com",
                subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}"
            }
          }
        }
}
