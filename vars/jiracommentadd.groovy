def call(String jiraissueid, String message) {    
    withEnv(['JIRA_SITE=jiralocal']) {
        def comment = [ body: message ]
        jiraAddComment idOrKey: jiraissueid, input: comment
    }
}
