package com.company.Visit;

import java.util.List;

public class IssueList {
    private List<Issue> issueList;

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void addIssue(Issue issue) {
        issueList.add(issue);
    }

    public void removeIssue(Issue issue) {
        issueList.remove(issue);
    }
}
