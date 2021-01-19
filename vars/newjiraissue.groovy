def call(String buildname, String buildnum,String message) {    
    withEnv(['JIRA_SITE=jiralocal']) {
        def testIssue = [fields: [ project: [id: "10100"],
                                    summary: buildname+buildnum+'_'+message,
                                    description: 'New JIRA Created from Jenkins.',
                                    issuetype: [id: '10005']]]

        response = jiraNewIssue issue: testIssue

        echo response.successful.toString()
        echo response.data.toString()
    }
    
}
