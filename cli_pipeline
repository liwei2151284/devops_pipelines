node {
    
    // env.NODE_HOME=tool name: 'nodejs', type: 'nodejs'
    // env.PATH="${env.NODE_HOME}/bin:${env.PATH}"
    
    //stage('Prepare') {
    //    sh 'jfrog rt c art --url=http://192.168.230.155:8081/artifactory --user=admin --password=password'
    //    sh 'jfrog rt use art'
    //}
    
    stage('SCM'){
        git 'https://github.com/liwei2151284/Guestbook-microservices-k8s.git'
    }
    
    stage('Build') {
        dir('.') {
            sh "jfrog rt mvn-config --repo-resolve-releases=releasemgt-maven-release-virtual --repo-resolve-snapshots=releasemgt-maven-snapshot-virtual --repo-deploy-releases=releasemgt-maven-stage-local --repo-deploy-snapshots=releasemgt-maven-snapshot-local --server-id-deploy=artifactory --server-id-resolve=artifactory "
            sh "jfrog rt mvn clean install -f ./gateway-service/pom.xml --build-name=${env.JOB_NAME} --build-number=${env.BUILD_NUMBER} "
            sh "jfrog rt bce ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
    }
    
    stage('JIRA') {
        sh "jfrog rt bag ${env.JOB_NAME} ${env.BUILD_NUMBER} --config ./config.cfg"
    }
    
    stage('Publish') {
        dir('.') {
          sh "jfrog rt bp ${env.JOB_NAME} ${env.BUILD_NUMBER}"
          sleep 10
        }
    }
    
    stage('Scan') {
        sh "jfrog rt bs ${env.JOB_NAME} ${env.BUILD_NUMBER} --fail=false"
    }
    
    stage('Promote') {
        sh "jfrog rt bpr ${env.JOB_NAME} ${env.BUILD_NUMBER} releasemgt-maven-release-local --status=Released --comment=approved --include-dependencies=false --copy=true  --props=\"Released=approve;test=ok\""
    }

}
