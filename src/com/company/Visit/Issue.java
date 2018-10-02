package com.company.Visit;

public class Issue {
    private IssueEnum issueEnum;
    private String description;
    private int price;

    public Issue(IssueEnum issueEnum, String description, int price) {
        this.issueEnum = issueEnum;
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public IssueEnum getIssueEnum() {
        return issueEnum;
    }

    public int getPrice() {
        return price;
    }
}
